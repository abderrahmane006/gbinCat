package adam.gaia;


// Où placer les config du logger ? (http://maven.apache.org/plugins/maven-resources-plugin/)
// http://www.javacodegeeks.com/2012/04/using-slf4j-with-logback-tutorial.html
// http://www.slf4j.org/ / http://logback.qos.ch/documentation.html
// http://commons.apache.org/proper/commons-cli/usage.html / http://commons.apache.org/proper/commons-cli/properties.html
// https://github.com/alexholmes/hdfs-file-slurper/blob/master/src/main/java/com/alexholmes/hdfsslurper/Slurper.java

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.cli.ParseException;
import org.apache.hadoop.conf.Configuration;
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

import static adam.gaia.GbinCat.ExitCode.*;

// WARNING : l'import ci-dessous de IgslSource ne permet pas de lire les fichiers
// import gaia.cu9.operations.auxiliarydata.igsl.dm.IgslSource;

/**
 * Charge un ensemble de fichiers gbin dans un fichier texte stocké dans HDFS.
 *
 * @author hal
 * @version 03/2015
 */
public final class GbinCat extends Configured implements Tool {
    public static enum ExitCode {
        /** Erreur d'analyse de la ligne de commande. */
        PARSE_ERROR(1),
        /** Erreur de recherche des fichiers gbin. */
        GBIN_FIND(2),
        /** Erreur d'accès à HDFS. */
        HDFS_ACCESS(3);

        private int value;

        private ExitCode(int value) {
            this.value = value;
        }
    }

    /**
     * Types de fichiers gbin.
     */
    public static enum GbinType { IGSL, GOG; }

    /**
     * Journal.
     */
    private static final Logger logger = LoggerFactory.getLogger(GbinCat.class);

    /**
     * Configuration du programme
     */
    private GbinCatConf config = new GbinCatConf();

    /*
     * Main method of the program.
     * @param args command line arguments
     */
    @Override
    public int run(final String... args) throws Exception {
        logger.info("Début de l'exécution");

        parseCommandLine(args);
        GbinFinder gbinFinder = findGbinFiles();

        CSVWriter writer = null;
        try {
            writer = openOutputFile(config.getOutFile().toString());
            if (config.getGbinType() == GbinType.IGSL) {
                GbinIGSLFileProcessor fileProcessor = new GbinIGSLFileProcessor(config);
                fileProcessor.process(gbinFinder, writer);
            } else if (config.getGbinType() == GbinType.GOG) {
                GbinGOGFileProcessor fileProcessor = new GbinGOGFileProcessor(config);
                fileProcessor.process(gbinFinder, writer);
            } else {
                assert false;
            }
        } finally {
            logger.info("Fermeture du fichier de sortie");
            writer.close();
        }

        logger.info("Fin de l'exécution");
        return 0;
    }

    /**
     * Analyse la ligne de commande.
     * @param args les paramètres de ligne de commande
     */
    private void parseCommandLine(String[] args) {
        logger.info("Analyse de la ligne de commande");
        try {
            config.parse(args);
        } catch (ParseException e) {
            config.printUsage();
            logDisplayAndExit(e, "Echec de l'analyse de la ligne de commande", PARSE_ERROR);
        }
        logger.trace(config.toString());
    }

    /**
     * Recherche les fichiers gbin.
     * @return
     */
    private GbinFinder findGbinFiles() {
        logger.info("Recherche des fichiers gbin");
        GbinFinder gbinFinder = new GbinFinder();
        try {
            Files.walkFileTree(config.getInPath(), gbinFinder);
        } catch (IOException e) {
            logDisplayAndExit(e, "Erreur d'E/S lors de la recherche des fichiers gbin", GBIN_FIND);
        }
        logger.trace("Liste des fichiers gbin : {}", gbinFinder.getGbinFiles().toString());
        return gbinFinder;
    }

    /**
     * Crée le fichier de sortie dans HDFS.
     *
     * @param fileName le nom du fichier de sortie
     * @return une instance de CSVWriter
     */
    private CSVWriter openOutputFile(final String fileName) {
        logger.info("Ouverture du fichier de sortie dans HDFS");
        Configuration conf = getConf();

        FileSystem hdfs = null;
        try {
            hdfs = FileSystem.get(conf);
        } catch (IOException e) {
            logDisplayAndExit(e, "Erreur d'E/S lors de l'accès au système de fichiers HDFS", HDFS_ACCESS);
        }

        FSDataOutputStream hdfsOutputStream = null;
        try {
            org.apache.hadoop.fs.Path outputPath = new org.apache.hadoop.fs.Path(fileName);
            hdfsOutputStream = hdfs.create(outputPath, false);
        } catch (IOException e) {
            logDisplayAndExit(e, "Erreur d'E/S lors d'ouverture du fichier de sortie", HDFS_ACCESS);
        }

        return new CSVWriter(new OutputStreamWriter(hdfsOutputStream), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
    }

    /**
     * log et affiche un message d'erreur puis termine  le programme.
     * @param e l'exception
     * @param msg le message à afficher
     * @param exitCode le code de sortie du programme
     */
    private void logDisplayAndExit(Exception e, String msg, ExitCode exitCode) {
        logger.error("{} : {}", msg, e.getMessage());
        System.err.println(msg + " : " + e.getMessage());
        System.exit(exitCode.value);
    }

    /**
     * Class method call by Java VM when starting the application.
     * @param args command line arguments
     */
    public static void main(final String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(), new GbinCat(), args);
        System.exit(exitCode);
    }
}
