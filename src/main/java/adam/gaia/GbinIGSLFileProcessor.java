package adam.gaia;

import gaia.cu1.mdb.cu3.auxdata.igsl.dm.IgslSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Traite les enregistrements d'un fichier gbin au format IGSL.
 *
 * Ce format propose les attributs suivants :
 * Alpha
 * AlphaEpoch
 * AlphaError
 * AuxEPC
 * AuxGSC23
 * AuxHIP
 * AuxLQRF
 * AuxOGLE
 * AuxPPMXL
 * AuxSDSS
 * AuxTMASS
 * AuxTYCHO
 * AuxUCAC
 * Classification
 * Delta
 * DeltaEpoch
 * DeltaError
 * EclipticLat
 * EclipticLon
 * GalacticLat
 * GalacticLon
 * MagBJ
 * MagBJError
 * MagG
 * MagGError
 * MagGrvs
 * MagGrvsError
 * MagRF
 * MagRFError
 * MuAlpha
 * MuAlphaError
 * MuDelta
 * MuDeltaError
 * ParamMaxValues (Map<String, Double>)
 * ParamMinValues (Map<String, Double>)
 * ParamOutOfRangeValues (Map<String, Double>)
 * SolutionId
 * SourceClassification
 * SourceId
 * SourceMagBJ
 * SourceMagG
 * SourceMagGrvs
 * SourceMagRF
 * SourceMu
 * SourcePosition
 * ToggleASC
 *
 * @author hal
 * @version 03/2015
 */
public class GbinIGSLFileProcessor extends GbinFileProcessor {
    public static String[] allAttributes = {
            "Alpha", "AlphaEpoch", "AlphaError", "AuxEPC", "AuxGSC23", "AuxHIP", "AuxLQRF", "AuxOGLE", "AuxPPMXL",
            "AuxSDSS", "AuxTMASS", "AuxTYCHO", "AuxUCAC", "Classification", "Delta", "DeltaEpoch", "DeltaError",
            "EclipticLat", "EclipticLon", "GalacticLat", "GalacticLon", "MagBJ", "MagBJError", "MagG", "MagGError",
            "MagGrvs", "MagGrvsError", "MagRF", "MagRFError", "MuAlpha", "MuAlphaError", "MuDelta", "MuDeltaError",
            "SolutionId", "SourceClassification", "SourceId", "SourceMagBJ", "SourceMagG", "SourceMagGrvs",
            "SourceMagRF", "SourceMu", "SourcePosition", "ToggleASC"
    };

    public GbinIGSLFileProcessor(GbinCatConf config) {
        super(config);
    }

    /**
     * Retourne la liste des attributs pour la projection.
     * @return attributs pour la projection
     */
    @Override
    protected List<String> getProjection() {
        List<String> attributeList = super.getProjection();
        if (attributeList.equals(Collections.emptyList())) {
            attributeList = Arrays.asList(allAttributes);
        }
        return attributeList;
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
            if (attribute.equals("Alpha")) {
                outData[attributeIdx] = String.valueOf(igslData.getAlpha());
            }
            else
            if (attribute.equals("AlphaEpoch")) {
                outData[attributeIdx] = String.valueOf(igslData.getAlphaEpoch());
            }
            else
            if (attribute.equals("AlphaError")) {
                outData[attributeIdx] = String.valueOf(igslData.getAlphaError());
            }
            else
            if (attribute.equals("AuxEPC")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxEPC());
            }
            else
            if (attribute.equals("AuxGSC23")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxGSC23());
            }
            else
            if (attribute.equals("AuxHIP")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxHIP());
            }
            else
            if (attribute.equals("AuxLQRF")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxLQRF());
            }
            else
            if (attribute.equals("AuxOGLE")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxOGLE());
            }
            else
            if (attribute.equals("AuxPPMXL")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxPPMXL());
            }
            else
            if (attribute.equals("AuxSDSS")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxSDSS());
            }
            else
            if (attribute.equals("AuxTMASS")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxTMASS());
            }
            else
            if (attribute.equals("AuxTYCHO")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxTYCHO());
            }
            else
            if (attribute.equals("AuxUCAC")) {
                outData[attributeIdx] = String.valueOf(igslData.getAuxUCAC());
            }
            else
            if (attribute.equals("Classification")) {
                outData[attributeIdx] = String.valueOf(igslData.getClassification());
            }
            else
            if (attribute.equals("Delta")) {
                outData[attributeIdx] = String.valueOf(igslData.getDelta());
            }
            else
            if (attribute.equals("DeltaEpoch")) {
                outData[attributeIdx] = String.valueOf(igslData.getDeltaEpoch());
            }
            else
            if (attribute.equals("DeltaError")) {
                outData[attributeIdx] = String.valueOf(igslData.getDeltaError());
            }
            else
            if (attribute.equals("EclipticLat")) {
                outData[attributeIdx] = String.valueOf(igslData.getEclipticLat());
            }
            else
            if (attribute.equals("EclipticLon")) {
                outData[attributeIdx] = String.valueOf(igslData.getEclipticLon());
            }
            else
            if (attribute.equals("GalacticLat")) {
                outData[attributeIdx] = String.valueOf(igslData.getGalacticLat());
            }
            else
            if (attribute.equals("GalacticLon")) {
                outData[attributeIdx] = String.valueOf(igslData.getGalacticLon());
            }
            else
            if (attribute.equals("MagBJ")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagBJ());
            }
            else
            if (attribute.equals("MagBJError")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagBJError());
            }
            else
            if (attribute.equals("MagG")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagG());
            }
            else
            if (attribute.equals("MagGError")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagGError());
            }
            else
            if (attribute.equals("MagGrvs")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagGrvs());
            }
            else
            if (attribute.equals("MagGrvsError")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagGrvsError());
            }
            else
            if (attribute.equals("MagRF")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagRF());
            }
            else
            if (attribute.equals("MagRFError")) {
                outData[attributeIdx] = String.valueOf(igslData.getMagRFError());
            }
            else
            if (attribute.equals("MuAlpha")) {
                outData[attributeIdx] = String.valueOf(igslData.getMuAlpha());
            }
            else
            if (attribute.equals("MuAlphaError")) {
                outData[attributeIdx] = String.valueOf(igslData.getMuAlphaError());
            }
            else
            if (attribute.equals("MuDelta")) {
                outData[attributeIdx] = String.valueOf(igslData.getMuDelta());
            }
            else
            if (attribute.equals("MuDeltaError")) {
                outData[attributeIdx] = String.valueOf(igslData.getMuDeltaError());
            }
            else
            if (attribute.equals("ParamMaxValues")) {
                outData[attributeIdx] = String.valueOf(igslData.getParamMaxValues());
            }
            else
            if (attribute.equals("ParamMinValues")) {
                outData[attributeIdx] = String.valueOf(igslData.getParamMinValues());
            }
            else
            if (attribute.equals("ParamOutOfRangeValues")) {
                outData[attributeIdx] = String.valueOf(igslData.getParamOutOfRangeValues());
            }
            else
            if (attribute.equals("SolutionId")) {
                outData[attributeIdx] = String.valueOf(igslData.getSolutionId());
            }
            else
            if (attribute.equals("SourceClassification")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourceClassification());
            }
            else
            if (attribute.equals("SourceId")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourceId());
            }
            else
            if (attribute.equals("SourceMagBJ")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourceMagBJ());
            }
            else
            if (attribute.equals("SourceMagG")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourceMagG());
            }
            else
            if (attribute.equals("SourceMagGrvs")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourceMagGrvs());
            }
            else
            if (attribute.equals("SourceMagRF")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourceMagRF());
            }
            else
            if (attribute.equals("SourceMu")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourceMu());
            }
            else
            if (attribute.equals("SourcePosition")) {
                outData[attributeIdx] = String.valueOf(igslData.getSourcePosition());
            }
            else
            if (attribute.equals("ToggleASC")) {
                outData[attributeIdx] = String.valueOf(igslData.getToggleASC());
            }
            else
            {
                throw new IllegalArgumentException("Attribut " + attribute + " inconnu.");
            }
            ++attributeIdx;
        }
    }
}
