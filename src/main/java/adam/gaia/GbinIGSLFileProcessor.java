package adam.gaia;

import gaia.cu1.mdb.cu3.auxdata.igsl.dm.IgslSource;

/**
 * Traite les enregistrements d'un fichier gbin au format IGSL.
 */
public class GbinIGSLFileProcessor extends GbinFileProcessor {
    public GbinIGSLFileProcessor(GbinCatConf config, OutputTuple outputTuple) {
        super(config, outputTuple);
    }

    @Override
    protected Class getSourceClass() {
        return IGSLDescriptor.getSourceClass();
    }

    @Override
    protected void extractGbinObject(final Object o, OutputTuple outputTuple) throws Exception {
        IgslSource igslData = (IgslSource)o;
        int attributeIdx = 0;
        for (String attribute : getProjection()) {
            if (attribute.equals("Alpha")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAlpha()));
            }
            else
            if (attribute.equals("AlphaEpoch")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAlphaEpoch()));
            }
            else
            if (attribute.equals("AlphaError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAlphaError()));
            }
            else
            if (attribute.equals("AuxEPC")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxEPC()));
            }
            else
            if (attribute.equals("AuxGSC23")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxGSC23()));
            }
            else
            if (attribute.equals("AuxHIP")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxHIP()));
            }
            else
            if (attribute.equals("AuxLQRF")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxLQRF()));
            }
            else
            if (attribute.equals("AuxOGLE")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxOGLE()));
            }
            else
            if (attribute.equals("AuxPPMXL")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxPPMXL()));
            }
            else
            if (attribute.equals("AuxSDSS")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxSDSS()));
            }
            else
            if (attribute.equals("AuxTMASS")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxTMASS()));
            }
            else
            if (attribute.equals("AuxTYCHO")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxTYCHO()));
            }
            else
            if (attribute.equals("AuxUCAC")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getAuxUCAC()));
            }
            else
            if (attribute.equals("Classification")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getClassification()));
            }
            else
            if (attribute.equals("Delta")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getDelta()));
            }
            else
            if (attribute.equals("DeltaEpoch")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getDeltaEpoch()));
            }
            else
            if (attribute.equals("DeltaError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getDeltaError()));
            }
            else
            if (attribute.equals("EclipticLat")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getEclipticLat()));
            }
            else
            if (attribute.equals("EclipticLon")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getEclipticLon()));
            }
            else
            if (attribute.equals("GalacticLat")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getGalacticLat()));
            }
            else
            if (attribute.equals("GalacticLon")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getGalacticLon()));
            }
            else
            if (attribute.equals("MagBJ")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagBJ()));
            }
            else
            if (attribute.equals("MagBJError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagBJError()));
            }
            else
            if (attribute.equals("MagG")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagG()));
            }
            else
            if (attribute.equals("MagGError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagGError()));
            }
            else
            if (attribute.equals("MagGrvs")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagGrvs()));
            }
            else
            if (attribute.equals("MagGrvsError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagGrvsError()));
            }
            else
            if (attribute.equals("MagRF")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagRF()));
            }
            else
            if (attribute.equals("MagRFError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMagRFError()));
            }
            else
            if (attribute.equals("MuAlpha")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMuAlpha()));
            }
            else
            if (attribute.equals("MuAlphaError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMuAlphaError()));
            }
            else
            if (attribute.equals("MuDelta")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMuDelta()));
            }
            else
            if (attribute.equals("MuDeltaError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getMuDeltaError()));
            }
            else
            if (attribute.equals("ParamMaxValues")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getParamMaxValues()));
            }
            else
            if (attribute.equals("ParamMinValues")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getParamMinValues()));
            }
            else
            if (attribute.equals("ParamOutOfRangeValues")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getParamOutOfRangeValues()));
            }
            else
            if (attribute.equals("SolutionId")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSolutionId()));
            }
            else
            if (attribute.equals("SourceClassification")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourceClassification()));
            }
            else
            if (attribute.equals("SourceId")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourceId()));
            }
            else
            if (attribute.equals("SourceMagBJ")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourceMagBJ()));
            }
            else
            if (attribute.equals("SourceMagG")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourceMagG()));
            }
            else
            if (attribute.equals("SourceMagGrvs")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourceMagGrvs()));
            }
            else
            if (attribute.equals("SourceMagRF")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourceMagRF()));
            }
            else
            if (attribute.equals("SourceMu")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourceMu()));
            }
            else
            if (attribute.equals("SourcePosition")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getSourcePosition()));
            }
            else
            if (attribute.equals("ToggleASC")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.getToggleASC()));
            }
            else
            {
                throw new IllegalArgumentException("Attribut " + attribute + " inconnu.");
            }
            ++attributeIdx;
        }
    }
}
