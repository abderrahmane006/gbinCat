package adam.gaia.gbin;

import java.io.IOException;

/**
 * Un problème lors de l'accès à un fichier gbin.
 */
public class GbinException extends IOException {
    public GbinException(String msg) {
        super(msg);
    }
}
