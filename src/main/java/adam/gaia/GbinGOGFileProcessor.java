package adam.gaia;

import au.com.bytecode.opencsv.CSVWriter;
import gaia.cu9.archivearchitecture.core.dm.CatalogueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * Traite les enregistrements d'un fichier gbin au format GOG.
 *
 * @author hal
 * @version 03/2015
 */
public class GbinGOGFileProcessor {
    /**
     * Journal.
     */
    private static final Logger logger = LoggerFactory.getLogger(GbinGOGFileProcessor.class);

    /**
     * Configuration du programme
     */
    private GbinCatConf config;

    public GbinGOGFileProcessor(GbinCatConf config) {
        this.config = config;
    }

    public void process(GbinFinder files, CSVWriter writer) throws Exception {
        logger.info("Traitement des fichiers gbin au format GOG");
        long nbProcessedObjects = 0L;
        String[] outData = new String[config.getProjection().size()]; // buffer de sortie
        loopInGbin:
        for (Path file : files.getGbinFiles()) {
            logger.info("Traitement du fichier IGSL {}", file);
            GbinLoader gbinLoader = new GbinLoader(CatalogueSource.class);
            Object[] data = gbinLoader.loadData(file);
            for (Object o : data) {
                CatalogueSource igslData = (CatalogueSource)o;
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
                logger.trace("Ecriture (GOG) de {} dans le fichier CSV", Arrays.toString(outData));
                writer.writeNext(outData);
                ++nbProcessedObjects;
                if (nbProcessedObjects >= config.getNbObjects()) {
                    break loopInGbin;
                }
            }
        }
    }
}
