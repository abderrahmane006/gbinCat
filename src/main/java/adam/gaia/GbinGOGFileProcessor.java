package adam.gaia;

import gaia.cu9.archivearchitecture.core.dm.CatalogueSource;

/**
 * Traite les enregistrements d'un fichier gbin au format GOG.
 */
public class GbinGOGFileProcessor  extends GbinFileProcessor{
    public GbinGOGFileProcessor(Configuration config, OutputTuple outputTuple) {
        super(config, outputTuple);
    }

    @Override
    protected Class getSourceClass() {
        return GOGDescriptor.getSourceClass();
    }

    @Override
    protected void extractGbinObject(final Object o, OutputTuple outputTuple) throws Exception {
        CatalogueSource gogData = (CatalogueSource)o;
        int attributeIdx = 0;
        for (String attribute : getProjection()) {
            if (attribute.equals("Alpha")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getAlpha()));
            }
            else
            if (attribute.equals("AlphaError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getAlphaError()));
            }
            else
            if (attribute.equals("AstrometricWeight")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getAstrometricWeight()));
            }
            else
            if (attribute.equals("Chi2")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getChi2()));
            }
            else
            if (attribute.equals("DecomposedN")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getDecomposedN()));
            }
            else
            if (attribute.equals("Delta")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getDelta()));
            }
            else
            if (attribute.equals("DeltaError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getDeltaError()));
            }
            else
            if (attribute.equals("DeltaQ")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getDeltaQ()));
            }
            else
            if (attribute.equals("ExcessNoise")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getExcessNoise()));
            }
            else
            if (attribute.equals("ExcessNoiseSig")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getExcessNoiseSig()));
            }
            else
            if (attribute.equals("F2")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getF2()));
            }
            else
            if (attribute.equals("GMean")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getGMean()));
            }
            else
            if (attribute.equals("MatchedObservations")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getMatchedObservations()));
            }
            else
            if (attribute.equals("MuAlphaStar")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getMuAlphaStar()));
            }
            else
            if (attribute.equals("MuAlphaStarError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getMuAlphaStarError()));
            }
            else
            if (attribute.equals("MuDelta")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getMuDelta()));
            }
            else
            if (attribute.equals("MuDeltaError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getMuDeltaError()));
            }
            else
            if (attribute.equals("MuR")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getMuR()));
            }
            else
            if (attribute.equals("MuRerror")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getMuRerror()));
            }
            else
            if (attribute.equals("NObs")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getNObs()));
            }
            else
            if (attribute.equals("NOutliers")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getNOutliers()));
            }
            else
            if (attribute.equals("Observed")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getObserved()));
            }
            else
            if (attribute.equals("ParamMaxValues")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getParamMaxValues()));
            }
            else
            if (attribute.equals("ParamMinValues")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getParamMinValues()));
            }
            else
            if (attribute.equals("ParamOutOfRangeValues")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getParamOutOfRangeValues()));
            }
            else
            if (attribute.equals("ParamsSolved")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getParamsSolved()));
            }
            else
            if (attribute.equals("PrimaryFlag")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getPrimaryFlag()));
            }
            else
            if (attribute.equals("RadialVelocity")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getRadialVelocity()));
            }
            else
            if (attribute.equals("RadialVelocityError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getRadialVelocityError()));
            }
            else
            if (attribute.equals("RandomIndex")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getRandomIndex()));
            }
            else
            if (attribute.equals("RankDefect")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getRankDefect()));
            }
            else
            if (attribute.equals("RefEpoch")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getRefEpoch()));
            }
            else
            if (attribute.equals("RelegationFactor")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getRelegationFactor()));
            }
            else
            if (attribute.equals("RvConstancyProbability")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getRvConstancyProbability()));
            }
            else
            if (attribute.equals("SolutionId")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getSolutionId()));
            }
            else
            if (attribute.equals("SourceId")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getSourceId()));
            }
            else
            if (attribute.equals("Superseded")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getSuperseded()));
            }
            else
            if (attribute.equals("Varpi")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getVarpi()));
            }
            else
            if (attribute.equals("VarpiError")) {
                outputTuple.setAttribute(attributeIdx, String.valueOf(gogData.getVarpiError()));
            }
            else
            {
                throw new IllegalArgumentException("Attribut " + attribute + " inconnu.");
            }
            ++attributeIdx;
        }
    }
}
