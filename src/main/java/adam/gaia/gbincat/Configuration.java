package adam.gaia.gbincat;

import adam.gaia.gbin.GOGDescriptor;
import adam.gaia.gbin.IGSLDescriptor;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Analyse la ligne de commande et configure gbinCat.
 */
public class Configuration {
    private Path inputPath;
    private Path outputFile;
    private long numberOfObjectsToProcess;
    private List<String> attributesToProject;
    private GbinCat.GbinType filetype;

    private Configuration(ConfigurationBuilder builder) {
        this.inputPath = builder.inputPath;
        this.outputFile = builder.outputFile;
        this.numberOfObjectsToProcess = builder.numberOfObjectsToProcess;
        this.attributesToProject = builder.attributesToProject;
        this.filetype = builder.filetype;
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
        //TODO à reprendre
        if (attributesToProject.equals(Collections.singletonList("*"))) {
            if (getFiletype() == GbinCat.GbinType.GOG) {
                attributesToProject = GOGDescriptor.getAttributesAsList();
            } else if (getFiletype() == GbinCat.GbinType.IGSL) {
                attributesToProject = IGSLDescriptor.getAttributesAsList();
            } else {
                assert false;
            }
        }
        return attributesToProject;
    }
    public GbinCat.GbinType getFiletype() { return filetype; }

    @Override
    public String toString() {
        return "GbinCatConf{" +
                "inputPath=" + inputPath +
                ", outFile=" + outputFile +
                ", nbObjects=" + numberOfObjectsToProcess +
                ", projectionList=" + attributesToProject +
                ", gbinType=" + filetype +
                '}';
    }

    public static class ConfigurationBuilder {
        private Path inputPath;
        private Path outputFile;
        private long numberOfObjectsToProcess;
        private List<String> attributesToProject;
        private GbinCat.GbinType filetype;

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

        public ConfigurationBuilder filetype(GbinCat.GbinType filetype) {
            this.filetype = filetype;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
