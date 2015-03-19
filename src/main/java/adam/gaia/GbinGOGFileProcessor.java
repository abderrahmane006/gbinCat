package adam.gaia;

import gaia.cu9.archivearchitecture.core.dm.CatalogueSource;

/**
 * Traite les enregistrements d'un fichier gbin au format GOG.
 *
 * @author hal
 * @version 03/2015
 */
public class GbinGOGFileProcessor  extends GbinFileProcessor{
    public GbinGOGFileProcessor(GbinCatConf config) {
        super(config);
    }

    /**
     * Retourne le type de source.
     *
     * @return la classe CatalogueSource (GOG)
     */
    @Override
    protected Class getSourceClass() {
        return CatalogueSource.class;
    }

    /**
     * Décode un enregistrement du fichier gbin GOG.
     *
     * @param o l'enregistrement
     * @param outData les données à écrire dans le flux de sortie
     * @throws Exception
     */
    @Override
    protected void processGbinObject(final Object o, final String[] outData) throws Exception {
        CatalogueSource igslData = (CatalogueSource)o;
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
