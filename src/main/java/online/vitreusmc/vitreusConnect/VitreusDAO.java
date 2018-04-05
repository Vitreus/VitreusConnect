package online.vitreusmc.vitreusConnect;

import java.util.HashMap;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import online.vitreusmc.vitreusConnect.object.VitreusObject;

public class VitreusDAO {
	
	private Datastore datastore;
	private HashMap<Class<? extends VitreusObject>, BasicDAO<? extends VitreusObject, String>> DAOMap = new HashMap<>();
	
	public VitreusDAO(Datastore datastore) {
		this.datastore = datastore;
	}
	
	public <T extends VitreusObject> BasicDAO<? extends VitreusObject, String> getDAO(Class<T> entityClass) {
		
		if (!DAOMap.containsKey(entityClass)) {
			BasicDAO<T, String> dao = new BasicDAO<>(entityClass, datastore);
			DAOMap.put(entityClass, dao);
		}
		
		return DAOMap.get(entityClass);
	}
	
}