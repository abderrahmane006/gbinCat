package adam.gaia;

import gaia.cu9.archivearchitecture.core.dm.CatalogueSource;

import java.util.Arrays;
import java.util.List;

/**
 * Décrit le format gbin GOG.
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
 */
public class GOGDescriptor {
    public static final String[] ATTRIBUTES = {
            "Alpha", "AlphaError", "Delta", "DeltaError", "DeltaQ", "ExcessNoise", "ExcessNoiseSig", "F2",
            "MatchedObservations", "MuAlphaStar", "MuAlphaStarError", "MuDelta", "MuDeltaError", "MuR", "MuRerror",
            "Observed", "ParamsSolved", "PrimaryFlag", "RadialVelocity", "RadialVelocityError", "RandomIndex",
            "RankDefect", "RefEpoch", "RelegationFactor", "RvConstancyProbability", "SolutionId", "SourceId",
            "Superseded", "Varpi", "VarpiError"
    };

    public static Class getSourceClass() {
        return CatalogueSource.class;
    }

    public static List<String> getAttributesAsList() {
        return Arrays.asList(ATTRIBUTES);
    }
}
