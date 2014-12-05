package adam.gaia;

import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Analyse la ligne de commande et configure gbinCat.
 *
 * @author hal
 * @version 12/2014
 */
public class GbinCatConf {
    public static final String INPUT_PATH_OPT = "d";
    public static final String NB_OBJECTS_OPT = "n";
    public static final String PROJECTION_OPT = "p";
    public static final String OUTFILE_OPT = "o";

    /**
     * Les options supportées par le programme.
     */
    private final Options options = new Options();

    /**
     * Les arguments de ligne de commande analysés.
     */
    private CommandLine commandLine;

    /**
     * Initialise les options.
     */
    public GbinCatConf() {
        createOptions();
    }

    /**
     * Définie les options supportées par le programme.
     */
    private void createOptions() {
        options.addOption(
                OptionBuilder.withArgName("inPath")
                        .hasArg()
                        .isRequired()
                        .withDescription("répertoire source des fichiers gbin")
                        .create(INPUT_PATH_OPT));
        options.addOption(
                OptionBuilder.withArgName("nbObjects")
                        .hasArg()
                        .withDescription("nombre d'objets à traiter")
                        .create(NB_OBJECTS_OPT));
        options.addOption(
                OptionBuilder.withArgName("projection")
                        .hasArg()
                        .withDescription("attributs à projeter")
                        .create(PROJECTION_OPT));
        options.addOption(
                OptionBuilder.withArgName("outFile")
                        .hasArg()
                        .isRequired()
                        .withDescription("fichier de sortie")
                        .create(OUTFILE_OPT));
    }

    /**
     * Analyse la ligne de commande et stocke la configuration.
     * @param args la ligne de commande
     */
    public void parse(String... args) throws ParseException {
        commandLine = new PosixParser().parse(options, args, false);
    }

    /**
     * Affiche l'aide du programme.
     */
    public void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("gbinCat", options, true);
    }

    /**
     * Retourne le répertoire contenant les fichiers gbin.
     * @return le répertoire source
     */
    public Path getInPath() {
        return Paths.get(commandLine.getOptionValue(INPUT_PATH_OPT));
    }

    /**
     * Retourne le fichier de sortie.
     * @return le fichier de sortie
     */
    public Path getOutFile() {
        return Paths.get(commandLine.getOptionValue(OUTFILE_OPT));
    }

    /**
     * Retourne le nombre d'objets à traiter.
     * @return le nombre d'objets à traiter
     */
    public int getNbObjects() {
        return Integer.parseInt(commandLine.getOptionValue(NB_OBJECTS_OPT));
    }

    /**
     * Retourne la liste des attributs pour la projection.
     * @return attributs pour la projection
     */
    public List<String> getProjection() {
        return Arrays.asList(commandLine.getOptionValue(PROJECTION_OPT).split(","));
    }
}
