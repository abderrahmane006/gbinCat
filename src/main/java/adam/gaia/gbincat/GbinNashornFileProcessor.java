package adam.gaia.gbincat;

import adam.gaia.gbin.GbinFileDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Traite les objets du gbin avec un script Javascript.
 */
public class GbinNashornFileProcessor extends GbinFileProcessor {
    private static final Logger logger = LoggerFactory.getLogger(GbinNashornFileProcessor.class);

    private GbinFileDescriptor metadata;
    private ElementAccessScript script;
    private ScriptEngine engine;

    public GbinNashornFileProcessor(Configuration config, GbinFileDescriptor metadata, OutputTuple outputTuple, ElementAccessScript script) {
        super(config, outputTuple);
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("nashorn");
        this.metadata = metadata;
        this.script = script;
    }

    @Override
    protected Class getSourceClass() throws ClassNotFoundException {
        return metadata.getDefinitionClass();
    }

    @Override
    protected void extractGbinObject(Object o, OutputTuple outputTuple) throws Exception {
        engine.put("data", o);
        engine.put("buffer", outputTuple);
        engine.eval(script.toString());
    }
}
