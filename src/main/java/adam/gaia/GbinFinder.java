package adam.gaia;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Recherche les fichiers gbin dans une arborescence.
 *
 * @author hal
 * @version dec. 2014
 */
public class GbinFinder extends SimpleFileVisitor<Path> {
    /**
     * Motif à rechercher (*.gbin).
     */
    private static final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.gbin");

    /**
     * Liste des fichiers gbin trouvés.
     */
    private List<Path> gbinFiles = new ArrayList<>();

    // Compares the glob pattern against
    // the file or directory name.

    /**
     * Retourne la liste des fichiers trouvés.
     * @return fichiers trouvés
     */
    public List<Path> getGbinFiles() {
        return Collections.unmodifiableList(gbinFiles);
    }

    /**
     * Invoque le test pour chaque fichier.
     * @param file le fichier à tester
     * @param attrs les attributs du fichier
     * @return le code de retour
     */
    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    /**
     * Compare le motif avec le nom du fichier ou du répertoire.
     * @param file chemin à comparer
     */
    private void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            gbinFiles.add(file);
        }
    }
}
