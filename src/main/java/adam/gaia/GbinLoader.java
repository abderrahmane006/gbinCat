package adam.gaia;

import gaia.cu1.tools.dal.ObjectFactory;
import gaia.cu1.tools.dal.file.FileStore;
import gaia.cu1.tools.dal.table.GaiaTable;
import gaia.cu1.tools.exception.GaiaException;
import gaia.cu1.tools.util.props.PropertyLoader;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;


/**
 * Lit et charge un fichier gbin.
 *
 * @author hal
 * @version dec. 2014
 */
public class GbinLoader<V> {
	/**
	 * Accès au fichier gbin.
	 */
	private FileStore store;

	/**
	 * Type de source de données
	 */
	private Class sourceType;

	/**
	 * Initialise l'accès au fichier gbin.
	 *
	 * @param sourceType le type de données stockées dans le fichier (IgslSource.class, CatalogueSource.class)
	 * @throws GaiaException
	 */
	public GbinLoader(Class sourceType) throws GaiaException {
		PropertyLoader.load(); // TODO : à quoi cela sert-il ?
		store = new FileStore();
		this.sourceType = sourceType;
	}
	
	/**
	 * Charge les données.
	 * @return un tableau contenant les données
	 * @throws GaiaException
	 */
	public V[] loadData(Path file) throws GaiaException {
		GaiaTable gt = store.getFile(file.getParent().toString(),
				FilenameUtils.getBaseName(file.getFileName().toString()));
		ObjectFactory<V> of = new ObjectFactory<V>(sourceType);
		return of.getObjects(gt);
	}

//	private Object[] loadDataForProjection(GaiaTable gt, List<String> projList) throws GaiaException {
//		GaiaTableHeader gth = gt.getHeader();
//		List<String> attributeList = projList.equals(Collections.emptyList()) ?
//				gth.getColumnNames() : projList;
//		ArrayList<Object> data = new ArrayList<>();
//		while (gt.next()) {
//			for (String attribute : projList) {
//				System.out.print(attribute + " = " + gt.getAsObject(attribute) + ", ");
//			}
//		}
//		return null;
//	}
}
