package adam.gaia.gbincat;

//TODO Où placer les config du logger ? (http://maven.apache.org/plugins/maven-resources-plugin/)
// http://www.javacodegeeks.com/2012/04/using-slf4j-with-logback-tutorial.html
// http://www.slf4j.org/ / http://logback.qos.ch/documentation.html
// http://commons.apache.org/proper/commons-cli/usage.html / http://commons.apache.org/proper/commons-cli/properties.html
// https://github.com/alexholmes/hdfs-file-slurper/blob/master/src/main/java/com/alexholmes/hdfsslurper/Slurper.java

import adam.gaia.gbin.GbinFileDescriptor;
import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.cli.ParseException;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

import static adam.gaia.gbincat.GbinCat.ExitCode.*;

/**
 * Charge un ensemble de fichiers gbin dans un fichier stocké dans HDFS.
 */
public final class GbinCat extends Configured implements Tool {
    private static final Logger logger = LoggerFactory.getLogger(GbinCat.class);

    public enum ExitCode {
        NO_ERROR(0),
        /** Erreur d'analyse de la ligne de commande. */
        PARSE_ERROR(1),
        /** Erreur de recherche des fichiers gbin. */
        GBIN_ACCESS(2),
        /** Erreur d'accès à HDFS. */
        HDFS_ACCESS(3);

        private int value;

        ExitCode(int value) {
            this.value = value;
        }
    }

    public static enum GbinType { IGSL, GOG; }

    private Configuration config;
    private GbinFileDescriptor metadata;
    private OutputTuple outputTuple;

    @Override
    public int run(final String... args) throws Exception {
        logger.info("Début de l'exécution");
        parseCommandLineAndConfigure(args);
        extractGbinMetadata();
        ElementAccessScript script = new ElementAccessScript(config, metadata);
        GbinFileProcessor gbinFileProcessor = new GbinNashornFileProcessor(config, metadata, outputTuple, script);
        CSVWriter writer = openOutputFile();
        GbinFinderAndProcessor gbinFinderAndProcessor = new GbinFinderAndProcessor(config, gbinFileProcessor, writer);
        executeProcessing(gbinFinderAndProcessor, writer);
        logger.info("Fin de l'exécution");
        return NO_ERROR.value;
    }

    private void parseCommandLineAndConfigure(String[] args) {
        logger.info("Analyse de la ligne de commande");
        CommandLineParser commandLineParser = new CommandLineParser();
        try {
            config = commandLineParser.parse(args);
        } catch (ParseException e) {
            commandLineParser.printUsage();
            logDisplayAndExit(e, "Echec de l'analyse de la ligne de commande", PARSE_ERROR);
        }
        logger.trace(config.toString());
    }

    private void extractGbinMetadata() {
        logger.info("Extraction des métadonnées des fichiers");
        MetadataExtractor metadataExtractor = new MetadataExtractor();
        try {
            Files.walkFileTree(config.getInputPath(), metadataExtractor);
        } catch (IOException e) {
            logDisplayAndExit(e, "Erreur d'E/S lors de l'accès aux fichiers gbin", GBIN_ACCESS);
        }
        if (metadataExtractor.getExceptionDuringProcessing() != null) {
            logDisplayAndExit(metadataExtractor.getExceptionDuringProcessing(),
                    "Erreur d'E/S lors de l'extraction des métadonnées des fichiers gbin", GBIN_ACCESS);
        }
        metadata = metadataExtractor.getMetadata();
        if (metadata == null) {
            logDisplayAndExit(null,
                    "Erreur lors de l'extraction des métadonnées des fichiers gbin", GBIN_ACCESS);
        }
        logger.trace(metadata.toString());
        outputTuple = new OutputTuple(
                config.isAllAttributes() ? metadata.getSupportedAttributes().size()
                        : config.getAttributesToProject().size());
    }

    private CSVWriter openOutputFile() {
        logger.info("Ouverture du fichier de sortie dans HDFS");
        String outputFilename = config.getOutputFile().toString();
        FileSystem hdfs = getHDFSFileSystem();
        FSDataOutputStream hdfsOutputStream = getFsDataOutputStream(hdfs, outputFilename);
        return new CSVWriter(new OutputStreamWriter(hdfsOutputStream),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER);
    }

    private FileSystem getHDFSFileSystem() {
        FileSystem hdfs = null;
        org.apache.hadoop.conf.Configuration conf = getConf();
        try {
            hdfs = FileSystem.get(conf);
        } catch (IOException e) {
            logDisplayAndExit(e, "Erreur d'E/S lors de l'accès au système de fichiers HDFS", HDFS_ACCESS);
        }
        return hdfs;
    }

    private FSDataOutputStream getFsDataOutputStream(FileSystem hdfs, String fileName) {
        FSDataOutputStream hdfsOutputStream = null;
        try {
            org.apache.hadoop.fs.Path outputPath = new org.apache.hadoop.fs.Path(fileName);
            hdfsOutputStream = hdfs.create(outputPath, false);
        } catch (IOException e) {
            logDisplayAndExit(e, "Erreur d'E/S lors d'ouverture du fichier de sortie", HDFS_ACCESS);
        }
        return hdfsOutputStream;
    }

    private void executeProcessing(GbinFinderAndProcessor gbinFinderAndProcessor, CSVWriter writer) throws IOException {
        logger.info("Démarrage du traitement des fichiers");
        try {
            Files.walkFileTree(config.getInputPath(), gbinFinderAndProcessor);
        } catch (IOException e) {
            logDisplayAndExit(e, "Erreur d'E/S lors de l'accès aux fichiers gbin", GBIN_ACCESS);
        } finally {
            logger.info("Fermeture du fichier de sortie");
            writer.close();
        }
        if (gbinFinderAndProcessor.getExceptionDuringProcessing() != null) {
            logDisplayAndExit(gbinFinderAndProcessor.getExceptionDuringProcessing(),
                    "Erreur d'E/S lors du traitement des fichiers gbin", GBIN_ACCESS);
        }
    }

    /**
     * log et affiche un message d'erreur puis termine  le programme.
     * @param e l'exception
     * @param msg le message à afficher
     * @param exitCode le code de sortie du programme
     */
    private void logDisplayAndExit(Exception e, String msg, ExitCode exitCode) {
        logger.error("{} : {}", msg, e);
        System.err.println(msg + " : " + e);
        System.exit(exitCode.value);
    }

    public static void main(final String[] args) throws Exception {
        int exitCode = ToolRunner.run(new org.apache.hadoop.conf.Configuration(), new GbinCat(), args);
        System.exit(exitCode);
    }
}
