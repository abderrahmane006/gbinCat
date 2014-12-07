package adam.gaia;


// Où placer les config du logger ? (http://maven.apache.org/plugins/maven-resources-plugin/)
// http://www.javacodegeeks.com/2012/04/using-slf4j-with-logback-tutorial.html
// http://www.slf4j.org/ / http://logback.qos.ch/documentation.html
// http://commons.apache.org/proper/commons-cli/usage.html / http://commons.apache.org/proper/commons-cli/properties.html
// https://github.com/alexholmes/hdfs-file-slurper/blob/master/src/main/java/com/alexholmes/hdfsslurper/Slurper.java

import gaia.cu1.tools.exception.GaiaException;
import gaia.cu1.mdb.cu3.auxdata.igsl.dm.IgslSource;
// WARNING : l'import ci-dessous de IgslSource ne permet pas de lire les fichiers
// import gaia.cu9.operations.auxiliarydata.igsl.dm.IgslSource;
import gaia.cu9.archivearchitecture.core.dm.CatalogueSource;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    GbinCatConf config = new GbinCatConf(); // TODO : private

    /*
     * Main method of the program.
     * @param args command line arguments
     */
    public void run(final String... args) throws ParseException {
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
            if (sourceClass == IgslSource.class) {
                System.out.println("alpha = " + ((IgslSource)data[0]).getAlpha() + ", delta = " + ((IgslSource)data[0]).getDelta());
            } else {
                System.out.println("alpha = " + ((CatalogueSource)data[0]).getAlpha() + ", delta = " + ((CatalogueSource)data[0]).getDelta());
            }
        }

        logger.info("Fin de l'exécution");
    }

    /**
     * Class method call by Java VM when starting the application.
     * @param args command line arguments
     */
    public static void main(final String[] args) throws ParseException {
        ENVIRONMENT.run(args);
    }
}
