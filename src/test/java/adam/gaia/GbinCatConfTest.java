package adam.gaia;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
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
    public void parseMinimalIgslCommandLine() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-igsl");

        assertThat("Le chemin source est incorrect.", config.getInPath(), is(equalTo(EXPECTED_IN_PATH)));
        assertThat("Le fichier de destination est incorrect.", config.getOutFile(), is(equalTo(EXPECTED_OUT_FILE)));
        assertThat("Le nombre d'objets est incorrect", config.getNbObjects(), is(equalTo(-1L)));
        assertThat("Les attributs de projection sont incorrects", config.getProjection(), is(equalTo(Collections.<String>emptyList())));
        assertThat("Le type de fichier est incorrect.", config.getGbinType(), is(equalTo(GbinCat.GbinType.IGSL)));
    }

    @Test
    public void parseMinimalGogCommandLine() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-gog");

        assertThat("Le chemin source est incorrect.", config.getInPath(), is(equalTo(EXPECTED_IN_PATH)));
        assertThat("Le fichier de destination est incorrect.", config.getOutFile(), is(equalTo(EXPECTED_OUT_FILE)));
        assertThat("Le nombre d'objets est incorrect", config.getNbObjects(), is(equalTo(-1L)));
        assertThat("Les attributs de projection sont incorrects", config.getProjection(), is(equalTo(Collections.<String>emptyList())));
        assertThat("Le type de fichier est incorrect.", config.getGbinType(), is(equalTo(GbinCat.GbinType.GOG)));
    }

    @Test
    public void parseCommandLineWithNbObjects() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n", "12", "-igsl");

        assertThat("Le chemin source est incorrect.", config.getInPath(), is(equalTo(EXPECTED_IN_PATH)));
        assertThat("Le fichier de destination est incorrect.", config.getOutFile(), is(equalTo(EXPECTED_OUT_FILE)));
        assertThat("Le nombre d'objet est incorrect.", config.getNbObjects(), is(equalTo(12L)));
        assertThat("Le type de fichier est incorrect.", config.getGbinType(), is(equalTo(GbinCat.GbinType.IGSL)));
    }

    @Test
    public void parseCommandLineWithNbObjectsInK() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n", "12K", "-igsl");

        assertThat("Le nombre d'objet est incorrect.", config.getNbObjects(), is(equalTo(12000L)));
    }

    @Test
    public void parseCommandLineWithNbObjectsInM() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n", "12M", "-igsl");

        assertThat("Le nombre d'objet est incorrect.", config.getNbObjects(), is(equalTo(12000000L)));
    }

    @Test
    public void parseCommandLineWithNbObjectsInG() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n", "12G", "-igsl");

        assertThat("Le nombre d'objet est incorrect.", config.getNbObjects(), is(equalTo(12000000000L)));
    }

    @Test
    public void parseCommandLineWithNbObjectsInT() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n", "12T", "-igsl");

        assertThat("Le nombre d'objet est incorrect.", config.getNbObjects(), is(equalTo(12000000000000L)));
    }

    @Test(expected = MissingArgumentException.class)
    public void parseCommandLineWithMissingNbObjects() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-n");
        fail("Never reached.");
    }

    @Test
    public void parseCommandLineWithProjection() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-p", "a,b,c", "-igsl");
        List<String> expectedAttributes = Arrays.asList("a", "b", "c");

        assertThat("Le chemin source est incorrect.", config.getInPath(), is(equalTo(EXPECTED_IN_PATH)));
        assertThat("Le fichier de destination est incorrect.", config.getOutFile(), is(equalTo(EXPECTED_OUT_FILE)));
        assertThat("La liste des attributs pour la projection est incorrecte.", config.getProjection(), is(equalTo(expectedAttributes)));
        assertThat("Le type de fichier est incorrect.", config.getGbinType(), is(equalTo(GbinCat.GbinType.IGSL)));
    }

    @Test(expected = MissingArgumentException.class)
    public void parseCommandLineWithMissingProjection() throws ParseException {
        config.parse("-d", PATH_PREFIX + "data", "-o", PATH_PREFIX + "output/sortie.txt", "-p");
        fail("Never reached.");
    }
}
