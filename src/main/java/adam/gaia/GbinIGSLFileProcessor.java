package adam.gaia;

import gaia.cu1.mdb.cu3.auxdata.igsl.dm.IgslSource;

/**
 * Traite les enregistrements d'un fichier gbin au format IGSL.
 *
 * @author hal
 * @version 03/2015
 */
public class GbinIGSLFileProcessor extends GbinFileProcessor {
    public GbinIGSLFileProcessor(GbinCatConf config) {
        super(config);
    }

    /**
     * Retourne le type de source.
     *
     * @return la classe IgslSource
     */
    @Override
    protected Class getSourceClass() {
        return IgslSource.class;
    }

    /**
     * Décode un enregistrement du fichier gbin IGSL.
     *
     * @param o l'enregistrement
     * @param outData les données à écrire dans le flux de sortie
     * @throws Exception
     */
    @Override
    protected void processGbinObject(final Object o, final String[] outData) throws Exception {
        IgslSource igslData = (IgslSource)o;
        int attributeIdx = 0;
        for (String attribute : getProjection()) {
            if (attribute.equals("alpha")) {
                outData[attributeIdx] = String.valueOf(igslData.getAlpha());
            } else if (attribute.equals("delta")) {
                outData[attributeIdx] = String.valueOf(igslData.getDelta());
            } else {
                throw new Exception("Attribut " + attribute + " pas encore supporté.");
            }
            ++attributeIdx;
        }
    }
}
