package adam.gaia.gbin;

import gaia.cu1.tools.dal.file.FileStore;
import gaia.cu1.tools.dal.gbin.GbinFactory;
import gaia.cu1.tools.dal.gbin.GbinMetaData;
import gaia.cu1.tools.dal.gbin.GbinReader;
import gaia.cu1.tools.dal.table.GaiaTable;
import gaia.cu1.tools.dal.table.GaiaTableHeader;
import gaia.cu1.tools.dm.GaiaRoot;
import gaia.cu1.tools.exception.GaiaException;
import gaia.cu1.tools.util.props.PropertyLoader;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gaia.cu1.tools.dal.table.GaiaTable.DataType;
import static gaia.cu1.tools.dal.table.GaiaTable.DataType.*;

/**
 * extrait et stocke les métadonnées d'un fichier gbin.
 */
public class GbinFileDescriptor {
    private static final DataType[] SUPPORTED_TYPES = { BOOLEAN, BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, STRING };

    private int versionNumber;
    private Long numberOfObjects;
    private String objectType;
    private String attributesAsString;
    private List<String> attributes;

    public GbinFileDescriptor(Path path) throws Exception {
        PropertyLoader.load();
        loadMetadata(path);
        loadTableHeader(path);
    }

    private void loadMetadata(Path path) throws Exception {
        try (GbinReader<GaiaRoot> reader = GbinFactory.getGbinReader(path.toFile())) {
            GbinMetaData metaData = reader.getGbinMetaData();
            versionNumber = metaData.getGbinVersionNumber();
            numberOfObjects = metaData.getTotalElementCount();

            List<GbinMetaData.ChunkMetaData> chunks = metaData.getChunkList();
            if (chunks.size() != 1) {
                throw new Exception("Seuls les fichiers gbin ne comportant qu'un partie sont supportés.");
            }
            GbinMetaData.ChunkMetaData chunk = chunks.get(0);
            if (chunk.numElements != numberOfObjects) {
                throw new Exception("Le nombre d'éléments de la partie du fichier (" + chunk.numElements +
                        ") n'est pas égal au nombre total d'éléments (" + numberOfObjects + ").");
            }
            objectType = chunk.className;
        }
    }

    private void loadTableHeader(Path path) throws GaiaException {
        String fullPath = FilenameUtils.getFullPath(path.toString());
        String basename = FilenameUtils.getBaseName(path.toString());

        FileStore store = new FileStore();
        GaiaTable gt = null;
        try {
            gt = store.getFile(fullPath, basename);
            GaiaTableHeader tableHeader = gt.getHeader();
            attributesAsString = tableHeader.toString();
            filterAttributes(tableHeader);
        } finally {
            gt.close();
        }
    }

    private void filterAttributes(GaiaTableHeader tableHeader) {
        attributes = new ArrayList<>();
        List<String> fullList = tableHeader.getColumnNames();
        List<DataType> types = tableHeader.getColumnTypes();
        for (int i = 0; i < fullList.size(); ++i) {
            String element = fullList.get(i);
            if (Arrays.asList(SUPPORTED_TYPES).contains(types.get(i))) {
                attributes.add(element);
            }
        }
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public Long getNumberOfObjects() {
        return numberOfObjects;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getAttributesAsString() {
        return attributesAsString;
    }

    public List<String> getAttributes() {
        return attributes;
    }
}
