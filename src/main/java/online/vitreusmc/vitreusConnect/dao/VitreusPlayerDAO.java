package online.vitreusmc.vitreusConnect.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import online.vitreusmc.vitreusConnect.object.VitreusPlayer;

public class VitreusPlayerDAO extends BasicDAO<VitreusPlayer, String> {

	public VitreusPlayerDAO(Class<VitreusPlayer> entityClass, Datastore datastore) {
		super(entityClass, datastore);
	}
	
}
