package adam.gaia;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

// Où placer les config du logger ? (http://maven.apache.org/plugins/maven-resources-plugin/)
// http://www.javacodegeeks.com/2012/04/using-slf4j-with-logback-tutorial.html
// http://www.slf4j.org/ / http://logback.qos.ch/documentation.html
// http://commons.apache.org/proper/commons-cli/usage.html / http://commons.apache.org/proper/commons-cli/properties.html
// https://github.com/alexholmes/hdfs-file-slurper/blob/master/src/main/java/com/alexholmes/hdfsslurper/Slurper.java

/**
 * Charge un ensemble de fichiers gbin dans un fichier texte stocké dans HDFS.
 *
 * @author hal
 * @version 12/2014
 */
public class gbinCat {
    /**
     * Point d'entrée du programme.
     *
     * @param args paramètres de la ligne de commande
     */
    public static void main(final String args[]) {
        Options options = new Options();
        options.addOption("d", "directory", true, "répertoire source des fichiers gbin");
        options.addOption("n", "nbobject", false, "nombre d'objets à traiter");
        options.addOption("p", "projection", false, "attributs à projeter");
        options.addOption("o", "outfile", true, "fichier de sortie");

        CommandLine commandLine;
        try {
            commandLine = new PosixParser().parse(options, args, false);
        } catch (ParseException e) {
            //log.error("Could not parse command line args: " + e.getMessage());
            System.err.println("Could not parse command line args: " + e.getMessage());
            //printUsageAndExit(options, 1);
            return;
        }

    }
}
