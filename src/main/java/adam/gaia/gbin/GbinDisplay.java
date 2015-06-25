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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static adam.gaia.gbincat.GbinCat.ExitCode.GBIN_ACCESS;

//TODO modifier cette classe pour en faire un outil de documentation des gbin (utiliser GbinFileDescriptor)

/**
 * Affiche les informations extraites du fichier gbin.
 */
public class GbinDisplay {
    private static final Logger logger = LoggerFactory.getLogger(GbinDisplay.class);

    public static void main(String[] args) throws GaiaException, IOException {
        Path inputPath = Paths.get(args[0]);
        logger.info("Analyse du répertoire {}", inputPath.toString());

        Files.walkFileTree(inputPath, new GbinDisplayVisitor());
    }
}
