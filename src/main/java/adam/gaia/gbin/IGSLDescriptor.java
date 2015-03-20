package adam.gaia.gbin;

import gaia.cu1.mdb.cu3.auxdata.igsl.dm.IgslSource;

import java.util.Arrays;
import java.util.List;

/**
 * Décrit le format gbin IGSL.
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
 */
public class IGSLDescriptor {
    public static final String[] ATTRIBUTES = {
            "Alpha", "AlphaEpoch", "AlphaError", "AuxEPC", "AuxGSC23", "AuxHIP", "AuxLQRF", "AuxOGLE", "AuxPPMXL",
            "AuxSDSS", "AuxTMASS", "AuxTYCHO", "AuxUCAC", "Classification", "Delta", "DeltaEpoch", "DeltaError",
            "EclipticLat", "EclipticLon", "GalacticLat", "GalacticLon", "MagBJ", "MagBJError", "MagG", "MagGError",
            "MagGrvs", "MagGrvsError", "MagRF", "MagRFError", "MuAlpha", "MuAlphaError", "MuDelta", "MuDeltaError",
            "SolutionId", "SourceClassification", "SourceId", "SourceMagBJ", "SourceMagG", "SourceMagGrvs",
            "SourceMagRF", "SourceMu", "SourcePosition", "ToggleASC"
    };

    public static Class getSourceClass() {
        return IgslSource.class;
    }

    public static List<String> getAttributesAsList() {
        return Arrays.asList(ATTRIBUTES);
    }
}
