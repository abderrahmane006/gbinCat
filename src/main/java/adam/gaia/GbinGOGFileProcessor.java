package adam.gaia;

import gaia.cu9.archivearchitecture.core.dm.CatalogueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Traite les enregistrements d'un fichier gbin au format GOG.
 *
 * Ce format propose les attributs suivants :
 * Alpha
 * AlphaError
 * AstrometricWeight (float[])
 * Chi2 (float[])
 * DecomposedN (float[])
 * Delta
 * DeltaError
 * DeltaQ
 * ExcessNoise
 * ExcessNoiseSig
 * F2
 * GMean (MeanPhotometry)
 * MatchedObservations
 * MuAlphaStar
 * MuAlphaStarError
 * MuDelta
 * MuDeltaError
 * MuR
 * MuRerror
 * NObs (int[])
 * NOutliers (int[])
 * Observed
 * ParamMaxValues (Map<String, Double>)
 * ParamMinValues (Map<String, Double>)
 * ParamOutOfRangeValues (Map<String, Double>)
 * ParamsSolved
 * PrimaryFlag
 * RadialVelocity
 * RadialVelocityError
 * RandomIndex
 * RankDefect
 * RefEpoch
 * RelegationFactor
 * RvConstancyProbability
 * SolutionId
 * SourceId
 * Superseded
 * Varpi
 * VarpiError
 *
 * @author hal
 * @version 03/2015
 */
public class GbinGOGFileProcessor  extends GbinFileProcessor{
    public static String[] allAttributes = {
            "Alpha", "AlphaError", "Delta", "DeltaError", "DeltaQ", "ExcessNoise", "ExcessNoiseSig", "F2",
            "MatchedObservations", "MuAlphaStar", "MuAlphaStarError", "MuDelta", "MuDeltaError", "MuR", "MuRerror",
            "Observed", "ParamsSolved", "PrimaryFlag", "RadialVelocity", "RadialVelocityError", "RandomIndex",
            "RankDefect", "RefEpoch", "RelegationFactor", "RvConstancyProbability", "SolutionId", "SourceId",
            "Superseded", "Varpi", "VarpiError"
    };

    public GbinGOGFileProcessor(GbinCatConf config) {
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
        CatalogueSource gogData = (CatalogueSource)o;
        int attributeIdx = FIRST_POSITION;
        for (String attribute : getProjection()) {
            if (attribute.equals("Alpha")) {
                outData[attributeIdx] = String.valueOf(gogData.getAlpha());
            }
            else
            if (attribute.equals("AlphaError")) {
                outData[attributeIdx] = String.valueOf(gogData.getAlphaError());
            }
            else
            if (attribute.equals("AstrometricWeight")) {
                outData[attributeIdx] = String.valueOf(gogData.getAstrometricWeight());
            }
            else
            if (attribute.equals("Chi2")) {
                outData[attributeIdx] = String.valueOf(gogData.getChi2());
            }
            else
            if (attribute.equals("DecomposedN")) {
                outData[attributeIdx] = String.valueOf(gogData.getDecomposedN());
            }
            else
            if (attribute.equals("Delta")) {
                outData[attributeIdx] = String.valueOf(gogData.getDelta());
            }
            else
            if (attribute.equals("DeltaError")) {
                outData[attributeIdx] = String.valueOf(gogData.getDeltaError());
            }
            else
            if (attribute.equals("DeltaQ")) {
                outData[attributeIdx] = String.valueOf(gogData.getDeltaQ());
            }
            else
            if (attribute.equals("ExcessNoise")) {
                outData[attributeIdx] = String.valueOf(gogData.getExcessNoise());
            }
            else
            if (attribute.equals("ExcessNoiseSig")) {
                outData[attributeIdx] = String.valueOf(gogData.getExcessNoiseSig());
            }
            else
            if (attribute.equals("F2")) {
                outData[attributeIdx] = String.valueOf(gogData.getF2());
            }
            else
            if (attribute.equals("GMean")) {
                outData[attributeIdx] = String.valueOf(gogData.getGMean());
            }
            else
            if (attribute.equals("MatchedObservations")) {
                outData[attributeIdx] = String.valueOf(gogData.getMatchedObservations());
            }
            else
            if (attribute.equals("MuAlphaStar")) {
                outData[attributeIdx] = String.valueOf(gogData.getMuAlphaStar());
            }
            else
            if (attribute.equals("MuAlphaStarError")) {
                outData[attributeIdx] = String.valueOf(gogData.getMuAlphaStarError());
            }
            else
            if (attribute.equals("MuDelta")) {
                outData[attributeIdx] = String.valueOf(gogData.getMuDelta());
            }
            else
            if (attribute.equals("MuDeltaError")) {
                outData[attributeIdx] = String.valueOf(gogData.getMuDeltaError());
            }
            else
            if (attribute.equals("MuR")) {
                outData[attributeIdx] = String.valueOf(gogData.getMuR());
            }
            else
            if (attribute.equals("MuRerror")) {
                outData[attributeIdx] = String.valueOf(gogData.getMuRerror());
            }
            else
            if (attribute.equals("NObs")) {
                outData[attributeIdx] = String.valueOf(gogData.getNObs());
            }
            else
            if (attribute.equals("NOutliers")) {
                outData[attributeIdx] = String.valueOf(gogData.getNOutliers());
            }
            else
            if (attribute.equals("Observed")) {
                outData[attributeIdx] = String.valueOf(gogData.getObserved());
            }
            else
            if (attribute.equals("ParamMaxValues")) {
                outData[attributeIdx] = String.valueOf(gogData.getParamMaxValues());
            }
            else
            if (attribute.equals("ParamMinValues")) {
                outData[attributeIdx] = String.valueOf(gogData.getParamMinValues());
            }
            else
            if (attribute.equals("ParamOutOfRangeValues")) {
                outData[attributeIdx] = String.valueOf(gogData.getParamOutOfRangeValues());
            }
            else
            if (attribute.equals("ParamsSolved")) {
                outData[attributeIdx] = String.valueOf(gogData.getParamsSolved());
            }
            else
            if (attribute.equals("PrimaryFlag")) {
                outData[attributeIdx] = String.valueOf(gogData.getPrimaryFlag());
            }
            else
            if (attribute.equals("RadialVelocity")) {
                outData[attributeIdx] = String.valueOf(gogData.getRadialVelocity());
            }
            else
            if (attribute.equals("RadialVelocityError")) {
                outData[attributeIdx] = String.valueOf(gogData.getRadialVelocityError());
            }
            else
            if (attribute.equals("RandomIndex")) {
                outData[attributeIdx] = String.valueOf(gogData.getRandomIndex());
            }
            else
            if (attribute.equals("RankDefect")) {
                outData[attributeIdx] = String.valueOf(gogData.getRankDefect());
            }
            else
            if (attribute.equals("RefEpoch")) {
                outData[attributeIdx] = String.valueOf(gogData.getRefEpoch());
            }
            else
            if (attribute.equals("RelegationFactor")) {
                outData[attributeIdx] = String.valueOf(gogData.getRelegationFactor());
            }
            else
            if (attribute.equals("RvConstancyProbability")) {
                outData[attributeIdx] = String.valueOf(gogData.getRvConstancyProbability());
            }
            else
            if (attribute.equals("SolutionId")) {
                outData[attributeIdx] = String.valueOf(gogData.getSolutionId());
            }
            else
            if (attribute.equals("SourceId")) {
                outData[attributeIdx] = String.valueOf(gogData.getSourceId());
            }
            else
            if (attribute.equals("Superseded")) {
                outData[attributeIdx] = String.valueOf(gogData.getSuperseded());
            }
            else
            if (attribute.equals("Varpi")) {
                outData[attributeIdx] = String.valueOf(gogData.getVarpi());
            }
            else
            if (attribute.equals("VarpiError")) {
                outData[attributeIdx] = String.valueOf(gogData.getVarpiError());
            }
            else
            {
                throw new IllegalArgumentException("Attribut " + attribute + " inconnu.");
            }
            ++attributeIdx;
        }
    }
}
