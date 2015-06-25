package adam.gaia.gbin;

import gaia.cu1.tools.dal.ObjectFactory;
import gaia.cu1.tools.dal.file.FileStore;
import gaia.cu1.tools.dal.table.GaiaTable;
import gaia.cu1.tools.dm.GaiaRoot;
import gaia.cu1.tools.exception.GaiaException;
import gaia.cu1.tools.util.props.PropertyLoader;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;

/**
 * Lit et charge un fichier gbin.
 */
public class GbinLoader {
	/**
	 * Accès au fichier gbin.
	 */
	private FileStore store;

	/**
	 * Type de source de données
	 */
	private Class<?> sourceType;

	/**
	 * Initialise l'accès au fichier gbin.
	 *
	 * @param sourceType le type de données stockées dans le fichier (IgslSource.class, CatalogueSource.class, ...)
	 * @throws GaiaException
	 */
	public GbinLoader(Class<?> sourceType) throws GaiaException {
		PropertyLoader.load(); // nécéssaire pour initialiser certaines propriétés (http://gaia.esac.esa.int/GaiaTools/api/latest/gaia/cu1/tools/util/props/PropertyLoader.html)
		store = new FileStore();
		this.sourceType = sourceType;
	}

	/**
	 * Charge les données.
	 *
	 * @return un tableau contenant les données
	 * @throws GaiaException
	 */
	public GaiaRoot[] loadData(Path file) throws GaiaException {
		GaiaTable gt = store.getFile(file.getParent().toString(),
				FilenameUtils.getBaseName(file.getFileName().toString()));
		ObjectFactory<GaiaRoot> of = new ObjectFactory<>(sourceType);
		return of.getObjects(gt);
	}
}
