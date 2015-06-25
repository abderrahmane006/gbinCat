package adam.gaia.gbincat;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Analyse la ligne de commande et configure gbinCat.
 */
public class Configuration {
    private static final List<String> ALL_ATTRIBUTES = Collections.singletonList("*");

    private Path inputPath;
    private Path outputFile;
    private long numberOfObjectsToProcess;
    private List<String> attributesToProject;

    private Configuration(ConfigurationBuilder builder) {
        this.inputPath = builder.inputPath;
        this.outputFile = builder.outputFile;
        this.numberOfObjectsToProcess = builder.numberOfObjectsToProcess;
        this.attributesToProject = builder.attributesToProject;
    }

    public Path getInputPath() {
        return inputPath;
    }
    public Path getOutputFile() {
        return outputFile;
    }
    public long getNumberOfObjectsToProcess() {
        return numberOfObjectsToProcess;
    }
    public List<String> getAttributesToProject() {
        return attributesToProject;
    }
    public boolean isAllAttributes() { return attributesToProject.equals(ALL_ATTRIBUTES); }

    @Override
    public String toString() {
        return "GbinCatConf{" +
                "inputPath=" + inputPath +
                ", outFile=" + outputFile +
                ", nbObjects=" + numberOfObjectsToProcess +
                ", projectionList=" + attributesToProject +
                '}';
    }

    public static class ConfigurationBuilder {
        private Path inputPath;
        private Path outputFile;
        private long numberOfObjectsToProcess;
        private List<String> attributesToProject;

        public ConfigurationBuilder inputPath(Path inputPath) {
            this.inputPath = inputPath;
            return this;
        }

        public ConfigurationBuilder outputFile(Path outputFile) {
            this.outputFile = outputFile;
            return this;
        }

        public ConfigurationBuilder numberOfObjectsToProcess(long numberOfObjectsToProcess) {
            this.numberOfObjectsToProcess = numberOfObjectsToProcess;
            return this;
        }

        public ConfigurationBuilder attributesToProject(List<String> attributesToProject) {
            this.attributesToProject = attributesToProject;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
