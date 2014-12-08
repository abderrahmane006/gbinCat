package adam.gaia;


// Où placer les config du logger ? (http://maven.apache.org/plugins/maven-resources-plugin/)
// http://www.javacodegeeks.com/2012/04/using-slf4j-with-logback-tutorial.html
// http://www.slf4j.org/ / http://logback.qos.ch/documentation.html
// http://commons.apache.org/proper/commons-cli/usage.html / http://commons.apache.org/proper/commons-cli/properties.html
// https://github.com/alexholmes/hdfs-file-slurper/blob/master/src/main/java/com/alexholmes/hdfsslurper/Slurper.java

import au.com.bytecode.opencsv.CSVWriter;
import gaia.cu1.mdb.cu3.auxdata.igsl.dm.IgslSource;
import gaia.cu1.tools.exception.GaiaException;
import gaia.cu9.archivearchitecture.core.dm.CatalogueSource;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

// WARNING : l'import ci-dessous de IgslSource ne permet pas de lire les fichiers
// import gaia.cu9.operations.auxiliarydata.igsl.dm.IgslSource;

/**
 * Charge un ensemble de fichiers gbin dans un fichier texte stocké dans HDFS.
 *
 * @author hal
 * @version 12/2014
 */
public enum GbinCat {
    ENVIRONMENT;

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
    public void run(final String... args) throws Exception {
        // assume SLF4J is bound to logback in the current environment
        //LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        //StatusPrinter.print(lc);

        logger.info("Début de l'exécution");
        try {
            logger.info("Analyse de la ligne de commande");
            config.parse(args);
        } catch (ParseException e) {
            logger.error("Echec de l'analyse de la ligne de commande : {}", e.getMessage());
            System.err.println("Echec de l'analyse de la ligne de commande : " + e.getMessage());
            config.printUsage();
            return;
        }
        logger.trace("Configuration [{}, {}, {}, {}, {}]", config.getInPath(), config.getGbinType(), config.getOutFile(), config.getNbObjects(), config.getProjection());

        GbinFinder gbinFinder = new GbinFinder();
        try {
            logger.info("Recherche des fichiers gbin");
            Files.walkFileTree(config.getInPath(), gbinFinder);
        } catch (IOException e) {
            logger.error("Erreur d'E/S lors de la recherche des fichiers gbin : {}", e.getMessage());
            System.err.println("Erreur d'E/S lors de la recherche des fichiers gbin : " + e.getMessage());
            return;
        }
        logger.trace(gbinFinder.getGbinFiles().toString());

        logger.info("Traitement des fichiers gbin");
        int nbProcessedObjects = 0;
        String[] outData = new String[config.getProjection().size()];
        CSVWriter writer = new CSVWriter(new FileWriter(config.getOutFile().toString()));
        Class sourceClass = config.getGbinType() == GbinType.IGSL ? IgslSource.class : CatalogueSource.class;
        for (Path file : gbinFinder.getGbinFiles()) {
            logger.info("Traitement du fichier {}", file);
            Object[] data = null;
            try {
                GbinLoader gbinLoader = new GbinLoader(sourceClass);
                data = gbinLoader.loadData(file);
            } catch (GaiaException e) {
                logger.error("Erreur lors du chargement du fichier gbin {} : {}", file, e.getMessage());
                System.err.println("Erreur lors du chargement du fichier gbin " + file + " : " + e.getMessage());
                return;
            }
            for (Object o : data) {
                if (sourceClass == IgslSource.class) {
                    IgslSource igslData = (IgslSource)o;
                    int attributeIdx = 0;
                    for (String attribute : config.getProjection()) {
                        if (attribute.equals("alpha")) {
                            outData[attributeIdx] = String.valueOf(igslData.getAlpha());
                        } else if (attribute.equals("delta")) {
                            outData[attributeIdx] = String.valueOf(igslData.getDelta());
                        } else {
                            throw new Exception("Attribut " + attribute + " pas encore supporté.");
                        }
                        ++attributeIdx;
                    }
                    logger.trace("Ecriture (IGSL) de {} dans le fichier CSV", Arrays.toString(outData));
                    writer.writeNext(outData);
                } else {
                    CatalogueSource gogData = (CatalogueSource)o;
                    int attributeIdx = 0;
                    for (String attribute : config.getProjection()) {
                        if (attribute.equals("alpha")) {
                            outData[attributeIdx] = String.valueOf(gogData.getAlpha());
                        } else if (attribute.equals("delta")) {
                            outData[attributeIdx] = String.valueOf(gogData.getDelta());
                        } else {
                            throw new Exception("Attribut " + attribute + " pas encore supporté.");
                        }
                        ++attributeIdx;
                    }
                    logger.trace("Ecriture (GOG) de {} dans le fichier CSV", Arrays.toString(outData));
                    writer.writeNext(outData);
                }
            }
        }
        writer.close();

        logger.info("Fin de l'exécution");
    }

    /**
     * Class method call by Java VM when starting the application.
     * @param args command line arguments
     */
    public static void main(final String[] args) throws Exception {
        ENVIRONMENT.run(args);
    }
}
