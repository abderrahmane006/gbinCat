package adam.gaia;

import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//TODO à découper en 2 classes
/**
 * Analyse la ligne de commande et configure gbinCat.
 */
public class GbinCatConf {
    public static final String INPUT_PATH_OPT = "d";
    public static final String NB_OBJECTS_OPT = "n";
    public static final String PROJECTION_OPT = "p";
    public static final String OUTFILE_OPT = "o";
    public static final String GOG_OPT = "gog";
    public static final String IGSL_OPT = "igsl";

    public static final String NB_OBJECTS_DEFAULT = "-1";
    public static final String PROJECTION_DEFAULT = "*";
    public static final String PROJECTION_SEP = ",";

    /**
     * Les options supportées par le programme.
     */
    private final Options options = new Options();

    /**
     * Le répertoire d'entrée à analyser.
     */
    private Path inputPath;

    /**
     * Le fichier de sortie.
     */
    private Path outFile;

    /**
     * Le nombre d'objects à traiter
     */
    private long nbObjects;

    /**
     * La liste des attributs pour la projection.
     */
    private List<String> projectionList;

    /**
     * Le type des fichiers gbin.
     */
    private GbinCat.GbinType gbinType;

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
        options.addOption(IGSL_OPT, false, "contenu au format IGSL");
        options.addOption(GOG_OPT, false, "contenu au format GOG");
    }

    /**
     * Analyse la ligne de commande et stocke la configuration.
     * @param args la ligne de commande
     */
    public void parse(String... args) throws ParseException {
        CommandLine commandLine = new PosixParser().parse(options, args, false);
        parseInPath(commandLine);
        parseOutFile(commandLine);
        parseNbObjects(commandLine);
        parseProjection(commandLine);
        parseType(commandLine);
    }

    /**
     * Analyse le chemin d'entrée.
     *
     * @param commandLine la ligne de commande
     */
    private void parseInPath(CommandLine commandLine) {
        inputPath = Paths.get(commandLine.getOptionValue(INPUT_PATH_OPT));
    }

    /**
     * Analyse le fichier de sortie.
     *
     * @param commandLine la ligne de commande
     */
    private void parseOutFile(CommandLine commandLine) {
        outFile = Paths.get(commandLine.getOptionValue(OUTFILE_OPT));
    }

    /**
     * Analyse le nombre d'objets.
     * La chaîne représentant le nombre peut se terminer par K (ou k),
     * M (ou m), G (ou g) ou T (ou t) pour indiquer l'unité (K = x1000,
     * M = 1 000 000, G = 1 000 000 000, T = 1 000 000 000 000).
     *
     * @param commandLine la ligne de commande
     */
    private void parseNbObjects(CommandLine commandLine) {
        String toParse = commandLine.getOptionValue(NB_OBJECTS_OPT, NB_OBJECTS_DEFAULT);
        char last = toParse.toLowerCase().charAt(toParse.length() - 1);
        long multFactor = 1; // par défaut, à l'unité
        switch (last) {
            case 'k':
                multFactor = 1000;
                break;
            case 'm':
                multFactor = 1000000;
                break;
            case 'g':
                multFactor = 1000000000;
                break;
            case 't':
                multFactor = 1000000000000L;
                break;
        }
        if (multFactor > 1) {
            toParse = toParse.substring(0, toParse.length() - 1);
        }
        nbObjects = Integer.parseInt(toParse) * multFactor;
    }

    /**
     * Analyse les attributs pour la projection.
     *
     * @param commandLine la ligne de commande
     */
    private void parseProjection(CommandLine commandLine) {
        projectionList = Arrays.asList(commandLine.getOptionValue(PROJECTION_OPT, PROJECTION_DEFAULT).split(PROJECTION_SEP));
    }

    /**
     * Analyse le type de fichier.
     *
     * @param commandLine la ligne de commande
     */
    private void parseType(CommandLine commandLine) throws MissingOptionException {
        if (commandLine.hasOption(IGSL_OPT)) {
            gbinType = GbinCat.GbinType.IGSL;
        } else if (commandLine.hasOption(GOG_OPT)) {
            gbinType = GbinCat.GbinType.GOG;
        } else {
            throw new MissingOptionException("le type de fichier doit être précisé (igsl ou gog)");
        }
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
        return inputPath;
    }

    /**
     * Retourne le fichier de sortie.
     * @return le fichier de sortie
     */
    public Path getOutFile() {
        return outFile;
    }

    /**
     * Retourne le nombre d'objets à traiter.
     * @return le nombre d'objets à traiter
     */
    public long getNbObjects() {
        return nbObjects;
    }

    /**
     * Retourne la liste des attributs pour la projection.
     * @return attributs pour la projection
     */
    public List<String> getProjection() {
        if (projectionList.equals(Collections.singletonList(PROJECTION_DEFAULT))) {
            //TODO à reprendre
            if (getGbinType() == GbinCat.GbinType.GOG) {
                projectionList = GOGDescriptor.getAttributesAsList();
            } else if (getGbinType() == GbinCat.GbinType.IGSL) {
                projectionList = IGSLDescriptor.getAttributesAsList();
            } else {
                assert false;
            }
        }
        return projectionList;
    }

    /**
     * Retourne le type des donnéesL.
     */
    public GbinCat.GbinType getGbinType() { return gbinType; }

    @Override
    public String toString() {
        return "GbinCatConf{" +
                "inputPath=" + inputPath +
                ", outFile=" + outFile +
                ", nbObjects=" + nbObjects +
                ", projectionList=" + projectionList +
                ", gbinType=" + gbinType +
                '}';
    }
}
