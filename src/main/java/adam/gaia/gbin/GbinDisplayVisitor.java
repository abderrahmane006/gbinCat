package adam.gaia.gbin;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

/**
 * Trouve un fichier gbin et extrait les métadonnées.
 */
public class GbinDisplayVisitor extends SimpleFileVisitor<Path> {
    private static final PathMatcher PATTERN_TO_MATCH = FileSystems.getDefault().getPathMatcher("glob:*.gbin");

    /**
     * Invoqué pour chaque fichier.
     *
     * @param file le fichier à tester
     * @param attrs les attributs du fichier
     * @return CONTINUE ou TERMINATE pour contrôler la suite du processus
     */
    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attrs) {
        try {
            processIfPatternMatches(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CONTINUE;
    }

    private void processIfPatternMatches(Path file) throws Exception {
        if (isGbinFile(file.getFileName())) {
            GbinFileDescriptor metadata = new GbinFileDescriptor(file);
            System.out.print(metadata);
        }
    }

    private boolean isGbinFile(Path name) {
        return name != null && PATTERN_TO_MATCH.matches(name);
    }
}
