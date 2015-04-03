package adam.gaia.gbin;

import gaia.cu1.tools.dal.file.FileStore;
import gaia.cu1.tools.dal.gbin.GbinFactory;
import gaia.cu1.tools.dal.gbin.GbinMetaData;
import gaia.cu1.tools.dal.gbin.GbinReader;
import gaia.cu1.tools.dal.table.GaiaTable;
import gaia.cu1.tools.dal.table.GaiaTableHeader;
import gaia.cu1.tools.dm.GaiaRoot;
import gaia.cu1.tools.exception.GaiaDataAccessException;
import gaia.cu1.tools.exception.GaiaException;
import gaia.cu1.tools.util.props.PropertyLoader;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GbinAPITest {
    @Parameterized.Parameters(name = "{index}: file {0} (gbin v{1}) has {2} objects of type {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"IgslSource_000-000-246.gbin", 4, 566798, "gaia.cu1.mdb.cu3.auxdata.igsl.dmimpl.IgslSourceImpl", "alpha:DOUBLE|alphaEpoch:FLOAT|alphaError:FLOAT|auxEPC:BOOLEAN|auxGSC23:BOOLEAN|auxHIP:BOOLEAN|auxLQRF:BOOLEAN|auxOGLE:BOOLEAN|auxPPMXL:BOOLEAN|auxSDSS:BOOLEAN|auxTMASS:BOOLEAN|auxTYCHO:BOOLEAN|auxUCAC:BOOLEAN|classification:BOOLEAN|delta:DOUBLE|deltaEpoch:FLOAT|deltaError:FLOAT|eclipticLat:FLOAT|eclipticLon:FLOAT|galacticLat:FLOAT|galacticLon:FLOAT|magBJ:FLOAT|magBJError:FLOAT|magG:FLOAT|magGError:FLOAT|magGrvs:FLOAT|magGrvsError:FLOAT|magRF:FLOAT|magRFError:FLOAT|muAlpha:FLOAT|muAlphaError:FLOAT|muDelta:FLOAT|muDeltaError:FLOAT|solutionId:LONG|sourceClassification:BYTE|sourceId:LONG|sourceMagBJ:BYTE|sourceMagG:BYTE|sourceMagGrvs:BYTE|sourceMagRF:BYTE|sourceMu:BYTE|sourcePosition:BYTE|toggleASC:BOOLEAN"},
                {"CatalogueSource_442.gbin", 4, 1404, "gaia.cu9.archivearchitecture.core.dmimpl.CatalogueSourceImpl", "alpha:DOUBLE|alphaError:DOUBLE|astrometricWeight:FLOAT:1|chi2:FLOAT:1|decomposedN:FLOAT:1|delta:DOUBLE|deltaError:DOUBLE|deltaQ:FLOAT|excessNoise:DOUBLE|excessNoiseSig:DOUBLE|f2:FLOAT|gMean:OBJECT|matchedObservations:SHORT|muAlphaStar:DOUBLE|muAlphaStarError:DOUBLE|muDelta:DOUBLE|muDeltaError:DOUBLE|muR:DOUBLE|muRerror:DOUBLE|nObs:INT:1|nOutliers:INT:1|observed:BOOLEAN|paramsSolved:BYTE|primaryFlag:BOOLEAN|radialVelocity:DOUBLE|radialVelocityError:DOUBLE|randomIndex:LONG|rankDefect:INT|refEpoch:DOUBLE|relegationFactor:FLOAT|rvConstancyProbability:DOUBLE|solutionId:LONG|sourceId:LONG|superseded:BOOLEAN|varpi:DOUBLE|varpiError:DOUBLE"},
                {"CatalogueSource_rds14a_N0000000.gbin", 4, 231767, "gaia.cu9.archivearchitecture.core.dmimpl.CatalogueSourceImpl", "alpha:DOUBLE|alphaError:DOUBLE|astrometricWeight:FLOAT:1|chi2:FLOAT:1|decomposedN:FLOAT:1|delta:DOUBLE|deltaError:DOUBLE|deltaQ:FLOAT|excessNoise:DOUBLE|excessNoiseSig:DOUBLE|f2:FLOAT|gMean:OBJECT|matchedObservations:SHORT|muAlphaStar:DOUBLE|muAlphaStarError:DOUBLE|muDelta:DOUBLE|muDeltaError:DOUBLE|muR:DOUBLE|muRerror:DOUBLE|nObs:INT:1|nOutliers:INT:1|observed:BOOLEAN|paramsSolved:BYTE|primaryFlag:BOOLEAN|radialVelocity:DOUBLE|radialVelocityError:DOUBLE|randomIndex:LONG|rankDefect:INT|refEpoch:DOUBLE|relegationFactor:FLOAT|rvConstancyProbability:DOUBLE|solutionId:LONG|sourceId:LONG|superseded:BOOLEAN|varpi:DOUBLE|varpiError:DOUBLE"},
                {"UMStellar_N010103321.gbin", 2, 240, "gaia.cu1.mdb.cu2.um.umtypes.dmimpl.UMStellarSourceImpl", "age:DOUBLE|alphaFe:DOUBLE|astrometry:OBJECT|bondAlbedo:DOUBLE|colorVminusI:DOUBLE|eccentricity:DOUBLE|feH:DOUBLE|flagInteracting:INT|geomAlbedo:DOUBLE|hasPhotocenterMotion:BOOLEAN|host:INT|inclination:DOUBLE|logg:DOUBLE|longitudeAscendingNode:DOUBLE|mass:DOUBLE|mbol:DOUBLE|meanAbsoluteV:DOUBLE|nc:INT|nt:INT|openClusterName:STRING|orbitPeriod:DOUBLE|periastronArgument:DOUBLE|periastronDate:DOUBLE|phase:DOUBLE|photometry:OBJECT|population:INT|rEnvRStar:DOUBLE|radius:DOUBLE|semimajorAxis:DOUBLE|spectralType:STRING|teff:DOUBLE|variabilityAmplitude:DOUBLE|variabilityPeriod:DOUBLE|variabilityPhase:DOUBLE|variabilityType:STRING|vsini:DOUBLE|sourceExtendedId:STRING|sourceId:LONG"}
        });
    }

    @Parameterized.Parameter
    public String filename;

    @Parameterized.Parameter(value=1)
    public int gbinVersion;

    @Parameterized.Parameter(value=2)
    public long nbObjects;

    @Parameterized.Parameter(value=3)
    public String objectType;

    @Parameterized.Parameter(value=4)
    public String attributes;

    private GbinReader<GaiaRoot> source;
    private GaiaTableHeader tableHeader;

    @Before
    public void setup() throws GaiaException, URISyntaxException {
        PropertyLoader.load();
        ClassLoader classLoader = getClass().getClassLoader();
        source = GbinFactory.getGbinReader(classLoader.getResourceAsStream(filename));

        FileStore store = new FileStore();
        URL url = classLoader.getResource(filename);
        Path path = Paths.get(url.getPath());
        GaiaTable gt = store.getFile(path.getParent().toString(),
                FilenameUtils.getBaseName(path.getFileName().toString()));
        tableHeader = gt.getHeader();
    }

    @Test
    public void testSourceMetadata() throws GaiaDataAccessException {
        GbinMetaData metaData = source.getGbinMetaData();
        assertThat("Wrong gbin version", metaData.getGbinVersionNumber(), is(gbinVersion));
        assertThat("Wrong number of objects", metaData.getTotalElementCount(), is(nbObjects));

        List<GbinMetaData.ChunkMetaData> chunks = metaData.getChunkList();
        assertThat("Gbin file has several chunks", chunks.size(), is(1));

        GbinMetaData.ChunkMetaData chunk = chunks.get(0);
        assertThat("Wrong Object type", chunk.className, is(objectType));
        assertThat("Incoherent number of objects", chunk.numElements, is(nbObjects));
    }

    @Test
    public void testTableHeader() {
        assertThat("Wrong attributes", tableHeader.toString(), is(attributes));
    }
}
