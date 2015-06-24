package adam.gaia.gbincat;

import adam.gaia.gbin.GbinFileDescriptor;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

/**
 * Trouve un fichier gbin et extrait les métadonnées.
 */
public class MetadataExtractor extends SimpleFileVisitor<Path> {
    private static final PathMatcher PATTERN_TO_MATCH = FileSystems.getDefault().getPathMatcher("glob:*.gbin");

    private GbinFileDescriptor metadata;
    private Exception exceptionDuringProcessing;

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
        FileVisitResult result = CONTINUE;
        try {
            result = processIfPatternMatches(file);
        } catch (Exception e) {
            exceptionDuringProcessing = e;
            result = TERMINATE;
        }
        return result;
    }

    public GbinFileDescriptor getMetadata() {
        return metadata;
    }

    public Exception getExceptionDuringProcessing() {
        return exceptionDuringProcessing;
    }

    private FileVisitResult processIfPatternMatches(Path file) throws Exception {
        if (isGbinFile(file.getFileName())) {
            metadata = new GbinFileDescriptor(file);
            return TERMINATE;
        }
        return CONTINUE;
    }

    private boolean isGbinFile(Path name) {
        return name != null && PATTERN_TO_MATCH.matches(name);
    }
}
