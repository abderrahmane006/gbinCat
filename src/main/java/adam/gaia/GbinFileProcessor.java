package adam.gaia;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

/**
 * Traite les enregistrements d'un fichier gbin.
 */
public abstract class GbinFileProcessor {
    private static final Logger logger = LoggerFactory.getLogger(GbinFileProcessor.class);
    private final Configuration config;
    private OutputTuple outputTuple;

    public GbinFileProcessor(Configuration config, OutputTuple outputTuple) {
        this.config = config;
        this.outputTuple = outputTuple;
    }

    /**
     * Traite les données des fichiers gbin.
     *
     * @param file fichier à traiter
     * @param nbProcessedObjects nombre d'objets déjà traités
     * @param writer flux de sortie
     * @return nombre total d'objets traités
     */
    public long process(Path file, long nbProcessedObjects, CSVWriter writer) throws Exception {
        logger.info("Traitement du fichier {} de type {}", file, getSourceClass());
        outputTuple.setFilename(FilenameUtils.getBaseName(file.toString()));
        GbinLoader gbinLoader = new GbinLoader(getSourceClass());
        Object[] data = gbinLoader.loadData(file);
        for (Object o : data) {
            extractGbinObject(o, outputTuple);
            logger.trace("Ecriture de {}", outputTuple);
            writer.writeNext(outputTuple.asArray());
            ++nbProcessedObjects;
            if (nbProcessedObjects >= config.getNumberOfObjectsToProcess()) {
                break;
            }
        }
        return nbProcessedObjects;
    }

    protected List<String> getProjection() {
        return config.getAttributesToProject();
    }

    abstract protected Class getSourceClass();
    abstract protected void extractGbinObject(final Object o, OutputTuple outputTuple) throws Exception;
}
