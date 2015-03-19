package adam.gaia;

import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Traite les enregistrements d'un fichier gbin.
 *
 * @author hal
 * @version 03/2015
 */
public abstract class GbinFileProcessor {
    /** Journal. */
    private static final Logger logger = LoggerFactory.getLogger(GbinFileProcessor.class);

    /** Configuration du programme. */
    private final GbinCatConf config;

    public GbinFileProcessor(GbinCatConf config) {
        this.config = config;
    }

    /**
     * Traite les données des fichiers gbin.
     *
     * @param files fichiers à traiter
     * @param writer flux de sortie
     * @throws Exception
     */
    public void process(GbinFinder files, CSVWriter writer) throws Exception {
        logger.info("Traitement des fichiers gbin");
        long nbProcessedObjects = 0L;
        String[] outData = new String[config.getProjection().size()]; // buffer de sortie
        loopInGbin:
        for (Path file : files.getGbinFiles()) {
            logger.info("Traitement du fichier {} de type {}", file, getSourceClass());
            GbinLoader gbinLoader = new GbinLoader(getSourceClass());
            Object[] data = gbinLoader.loadData(file);
            for (Object o : data) {
                processGbinObject(o, outData);
                logger.trace("Ecriture de {} dans le fichier CSV", Arrays.toString(outData));
                writer.writeNext(outData);
                ++nbProcessedObjects;
                if (nbProcessedObjects >= config.getNbObjects()) {
                    break loopInGbin;
                }
            }
        }
    }

    /**
     * Retourne la liste des attributs pour la projection.
     * @return attributs pour la projection
     */
    public List<String> getProjection() {
        return config.getProjection();
    }

    /**
     * Retourne la classe des éléments des fichiers.
     * @return la classe des éléments
     */
    abstract protected Class getSourceClass();

    /**
     * Traite un enregistrement d'un fichier gbin.
     *
     * @param o l'enregistrement
     * @param outData les données à écrire dans le flux de sortie
     * @throws Exception
     */
    abstract protected void processGbinObject(final Object o, final String[] outData) throws Exception;
}
