package adam.gaia;

import gaia.cu1.tools.dal.file.FileStore;
import gaia.cu1.tools.dal.gbin.GbinFactory;
import gaia.cu1.tools.dal.gbin.GbinHeaderKeys;
import gaia.cu1.tools.dal.gbin.GbinReader;
import gaia.cu1.tools.dal.gbin.GbinReaderV4;
import gaia.cu1.tools.dm.GaiaRoot;
import gaia.cu1.tools.exception.GaiaDataAccessException;
import gaia.cu1.tools.exception.GaiaException;
import gaia.cu1.tools.util.props.PropertyLoader;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class GbinLoaderTest {
    public static final String IGSL_GBIN_FILENAME = "IgslSource_000-000-246.gbin";
    public static final String GOG_GBIN_FILENAME = "CatalogueSource_442.gbin";

    private GbinReaderV4<GaiaRoot> igslGbinFile;
    private GbinReaderV4<GaiaRoot> gogGbinFile;

    @Before
    public void setup() throws GaiaException, URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        igslGbinFile = new GbinReaderV4<>(classLoader.getResourceAsStream(IGSL_GBIN_FILENAME));
        gogGbinFile = new GbinReaderV4<>(classLoader.getResourceAsStream(GOG_GBIN_FILENAME));
    }

    @Test
    public void verifyObjectTypeInFiles() throws GaiaDataAccessException {
        assertThat("Not the expected IGSL type",
                igslGbinFile.getGbinFileHeader().get(GbinHeaderKeys.objectTypeKey).toString(),
                is(equalTo("gaia.cu1.mdb.cu3.auxdata.igsl.dmimpl.IgslSourceImpl")));
        assertThat("Not the expected GOG type",
                igslGbinFile.getGbinFileHeader().get(GbinHeaderKeys.objectTypeKey).toString(),
                is(equalTo("gaia.cu1.mdb.cu3.auxdata.igsl.dmimpl.IgslSourceImpl")));
    }
}
