package adam.gaia;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class GbinCatConfTest {
    private static final String PATH_PREFIX = "/home/hal/Devel/gbinCat/";
    private static final Path EXPECTED_IN_PATH = Paths.get(PATH_PREFIX + "data");
    private static final Path EXPECTED_OUT_FILE = Paths.get(PATH_PREFIX + "output/sortie.txt");

    private GbinCatConf config;

    @Before
    public void setup() {
        config = new GbinCatConf();
    }

    @Test(expected = MissingOptionException.class)
    public void parseEmptyCommandLine() throws ParseException {
        config.parse("");
        fail("Never reached.");
    }

    @Test(expected = MissingOptionException.class)
    public void parseCommandLineWithoutInpath() throws ParseException {
        config.parse("-o", PATH_PREFIX + "output/sortie.txt");
        fail("Never reached.");
    }

    @Test(expected = MissingOptionException.class)
    public void parseCommandLineWithoutOutfile() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data");
        fail("Never reached.");
    }

    @Test(expected = MissingArgumentException.class)
    public void parseCommandLineWithEmptyInpath() throws ParseException {
        config.parse("-d");
        fail("Never reached.");
    }

    @Test(expected = MissingArgumentException.class)
    public void parseCommandLineWithEmptyOutfile() throws ParseException {
        config.parse("-o");
        fail("Never reached.");
    }

    @Test
    public void parseMinimalCommandLine() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt");

        assertThat("Le chemin source est incorrect.", config.getInPath(), is(equalTo(EXPECTED_IN_PATH)));
        assertThat("Le fichier de destination est incorrect.", config.getOutFile(), is(equalTo(EXPECTED_OUT_FILE)));
    }

    @Test
    public void parseCommandLineWithNbObjects() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n", "12");

        assertThat("Le chemin source est incorrect.", config.getInPath(), is(equalTo(EXPECTED_IN_PATH)));
        assertThat("Le fichier de destination est incorrect.", config.getOutFile(), is(equalTo(EXPECTED_OUT_FILE)));
        assertThat("Le nombre d'objet est incorrect.", config.getNbObjects(), is(equalTo(12)));
    }

    @Test(expected = MissingArgumentException.class)
    public void parseCommandLineWithMissingNbObjects() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n");
        fail("Never reached.");
    }

    @Test
    public void parseCommandLineWithProjection() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-p", "a,b,c");
        List<String> expectedAttributes = Arrays.asList("a", "b", "c");

        assertThat("Le chemin source est incorrect.", config.getInPath(), is(equalTo(EXPECTED_IN_PATH)));
        assertThat("Le fichier de destination est incorrect.", config.getOutFile(), is(equalTo(EXPECTED_OUT_FILE)));
        assertThat("La liste des attributs pour la projection est incorrecte.", config.getProjection(), is(equalTo(expectedAttributes)));
    }

    @Test(expected = MissingArgumentException.class)
    public void parseCommandLineWithMissingProjection() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-p");
        fail("Never reached.");
    }
}
