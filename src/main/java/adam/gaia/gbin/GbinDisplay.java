package adam.gaia.gbin;

import gaia.cu1.tools.dal.file.FileStore;
import gaia.cu1.tools.dal.gbin.GbinFactory;
import gaia.cu1.tools.dal.gbin.GbinMetaData;
import gaia.cu1.tools.dal.gbin.GbinReader;
import gaia.cu1.tools.dal.gbin.GbinReaderV4;
import gaia.cu1.tools.dal.table.GaiaTable;
import gaia.cu1.tools.dal.table.GaiaTableHeader;
import gaia.cu1.tools.exception.GaiaException;
import gaia.cu1.tools.util.props.PropertyLoader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Affiche les informations extraites du fichier gbin.
 */
public class GbinDisplay {
    private static final Logger logger = LoggerFactory.getLogger(GbinDisplay.class);

    private static final String[] PATH_NAMES = {
            "/home/hal/Devel/gaia/data/igsl_source/000/000/IgslSource_000-000-000.gbin",
            "/home/hal/Devel/gaia/data/igsl_source/000/000/IgslSource_000-000-246.gbin",
            "/home/hal/Devel/gaia/data/obsparis/CatalogueSource_rds14a_S200211.gbin",
            "/home/hal/Devel/gaia/data/RDS-14-A-Converted-0.01/CatalogueSource_442.gbin",
            "/home/hal/Devel/gaia/data/gums11/UMStellar_N010103321.gbin"
    };

    public static void main(String[] args) throws GaiaException {
        for (String pathName : PATH_NAMES) {
            Path inputPath = Paths.get(pathName);
            logger.info("Traitement du fichier {}", inputPath.toString());

            try {
                readWithFileStore(inputPath);
            } catch (GaiaException ge) {
                logger.error(ge.toString());
            }

            try {
                readWithGbinFactory(inputPath);
            } catch (GaiaException ge) {
                logger.error(ge.toString());
            }
            try {
                readWithGbinReader(inputPath);
            } catch (GaiaException ge) {
                logger.error(ge.toString());
            }
        }
    }

    private static void readWithFileStore(Path inputPath) throws GaiaException {
        PropertyLoader.load();
        FileStore store = new FileStore();
        logger.info("Création de la table (GaiaTable)");
        GaiaTable gt = store.getFile(inputPath.getParent().toString(),
                FilenameUtils.getBaseName(inputPath.getFileName().toString()));
        logger.info("Extraction de l'entête");
        GaiaTableHeader tableHeader = gt.getHeader();
        logger.info(tableHeader.toString());
    }

    private static void readWithGbinFactory(Path inputPath) throws GaiaException {
        logger.info("Création d'un GbinReader à partir d'un GbinFactory");
        GbinReader reader = GbinFactory.getGbinReader(inputPath.toFile()); // échoue...
        logger.info("Extraction des métadonnées");
        GbinMetaData metaData = reader.getGbinMetaData();
        logger.info(metaData.toString());
    }

    private static void readWithGbinReader(Path inputPath) throws GaiaException {
        logger.info("Création d'un GbinReader");
        GbinReader reader = new GbinReaderV4(inputPath.toFile());
        logger.info("Extraction des métadonnées");
        GbinMetaData metaData = reader.getGbinMetaData();
        logger.info(metaData.toString());
    }
}
