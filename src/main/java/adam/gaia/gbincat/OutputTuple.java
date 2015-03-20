package adam.gaia.gbincat;

import java.util.Arrays;

/**
 * Un tuple pour préparer la sortie.
 */
public class OutputTuple {
    public static final int FILENAME_POSITION_IN_TUPLE = 0;
    public static final int FIRST_AVAILABLE_POSITION_FOR_DATA_IN_TUPLE = 1;

    String[] tuple;

    public OutputTuple(final int numberOfAttributes) {
        this.tuple = new String[numberOfAttributes + 1]; // + 1 pour le nom du fichier
    }

    public void setFilename(String filename) {
        tuple[FILENAME_POSITION_IN_TUPLE] = filename;
    }

    public void setAttribute(int index, String value) {
        tuple[FIRST_AVAILABLE_POSITION_FOR_DATA_IN_TUPLE + index] = value;
    }

    public String[] asArray() {
        return tuple;
    }

    @Override
    public String toString() {
        return Arrays.toString(tuple);
    }
}
