package adam.gaia;


// Où placer les config du logger ? (http://maven.apache.org/plugins/maven-resources-plugin/)
// http://www.javacodegeeks.com/2012/04/using-slf4j-with-logback-tutorial.html
// http://www.slf4j.org/ / http://logback.qos.ch/documentation.html
// http://commons.apache.org/proper/commons-cli/usage.html / http://commons.apache.org/proper/commons-cli/properties.html
// https://github.com/alexholmes/hdfs-file-slurper/blob/master/src/main/java/com/alexholmes/hdfsslurper/Slurper.java

import org.apache.commons.cli.ParseException;

/**
 * Charge un ensemble de fichiers gbin dans un fichier texte stocké dans HDFS.
 *
 * @author hal
 * @version 12/2014
 */
public enum gbinCat {
    ENVIRONMENT;

    /**
     * Configuration du programme
     */
    private GbinCatConf config = new GbinCatConf();

    /*
     * Main method of the program.
     * @param args command line arguments
     */
    public void run(final String... args) throws ParseException {
        try {
            config.parse(args);
        } catch (ParseException e) {
            System.err.println("Could not parse command line args: " + e.getMessage());
            config.printUsage();
        }
    }

    /**
     * Class method call by Java VM when starting the application.
     * @param args command line arguments
     */
    public static void main(final String[] args) throws ParseException {
        ENVIRONMENT.run(args);
    }
}
