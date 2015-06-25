package adam.gaia.gbincat;

import adam.gaia.gbin.GbinFileDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Script pour l'accès aux élémements d'un fichier gbin.
 */
public class ElementAccessScript {
    private static final Logger logger = LoggerFactory.getLogger(ElementAccessScript.class);

    private Configuration config;
    private GbinFileDescriptor metadata;
    private String script;

    public ElementAccessScript(Configuration config, GbinFileDescriptor metadata) throws Exception {
        this.config = config;
        this.metadata = metadata;
        this.script = "";
        generate();
    }

    private void generate() throws Exception {
        String tpl = "buffer.setAttribute(%d, data.get%s()); ";
        List<String> supportedAttributes = metadata.getSupportedAttributes().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        List<String> expectedAttributes = config.getAttributesToProject();
        if (config.isAllAttributes()) {
            expectedAttributes = supportedAttributes;
        }
        for (int i = 0; i < expectedAttributes.size(); ++i) {
            String attribute = expectedAttributes.get(i);
            if (supportedAttributes.contains(attribute)) {
                String attributeWithUppercase = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
                script += String.format(tpl, i, attributeWithUppercase);
            } else {
                throw new Exception(String.format("L'attribut %s n'existe pas dans le schéma des fichiers gbin", attribute));
            }
        }
        logger.trace(script);
    }

    @Override
    public String toString() {
        return script;
    }

}
