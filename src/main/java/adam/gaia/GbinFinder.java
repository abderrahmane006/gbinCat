package adam.gaia;

import au.com.bytecode.opencsv.CSVWriter;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

/**
 * Recherche les fichiers gbin dans une arborescence.
 */
public class GbinFinder extends SimpleFileVisitor<Path> {
    private static final PathMatcher PATTERN_TO_MATCH = FileSystems.getDefault().getPathMatcher("glob:*.gbin");

    private Configuration config;
    private GbinFileProcessor gbinFileProcessor;
    private long nbProcessedObjects = 0L;
    private OutputTuple outputTuple;
    private CSVWriter writer;

    public GbinFinder(Configuration config, CSVWriter writer) {
        this.config = config;
        this.writer = writer;
        outputTuple = new OutputTuple(config.getAttributesToProject().size());
        //TODO utiliser le pattern abstract factory
        //TODO créer une méthode isIGSL
        if (config.getFiletype() == GbinCat.GbinType.IGSL) {
            this.gbinFileProcessor = new GbinIGSLFileProcessor(config, outputTuple);
        } else if (config.getFiletype() == GbinCat.GbinType.GOG) {
            this.gbinFileProcessor = new GbinGOGFileProcessor(config, outputTuple);
        } else {
            assert false;
        }
    }

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
            //TODO à traiter
        }
        return (nbProcessedObjects >= config.getNumberOfObjectsToProcess()) ? TERMINATE : CONTINUE;
    }

    private void processIfPatternMatches(Path file) throws Exception {
        if (isGbinFile(file.getFileName())) {
            nbProcessedObjects = gbinFileProcessor.process(file, nbProcessedObjects, writer);
        }
    }

    private boolean isGbinFile(Path name) {
        return name != null && PATTERN_TO_MATCH.matches(name);
    }
}
