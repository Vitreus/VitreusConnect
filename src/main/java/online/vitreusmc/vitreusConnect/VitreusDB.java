package online.vitreusmc.vitreusConnect;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.MapperOptions;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import online.vitreusmc.vitreusConnect.object.VitreusObject;

public class VitreusDB {

	private Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
	
	private ServerAddress serverAddress;
	private List<MongoCredential> credientials = new ArrayList<MongoCredential>();
	private String DBName;
	
	private MongoClient mongoClient;
	private Morphia morphiaClient;
	private Datastore datastore;
	
	private VitreusDAO DAO;
	
	public VitreusDB(String host, int port, String user, String userDb, String password, String db) {
		MongoCredential vitreusCredential = MongoCredential.createCredential(user, userDb, password.toCharArray());
		
		DBName = db;
		serverAddress = new ServerAddress(host, port);
		credientials.add(vitreusCredential);		
	}
	
	public VitreusDB(FileConfiguration config) {
		MongoCredential vitreusCredential;
		
		int port = config.getInt("db.port");
		String host = config.getString("db.host");
		String user = config.getString("db.user.name");
		String userDb = config.getString("db.user.authDBName");
		String password = config.getString("db.user.password");
		String db = config.getString("db.name");
		
		vitreusCredential = MongoCredential.createCredential(user, userDb, password.toCharArray());
		
		serverAddress = new ServerAddress(host, port);
		DBName = db;
		credientials.add(vitreusCredential);
	}
	
	public void connect() {
		MapperOptions mappingOptions;

		mongoClient = new MongoClient(serverAddress, credientials);
		morphiaClient = new Morphia();	

		mappingOptions = morphiaClient.getMapper().getOptions();
		mappingOptions.setStoreEmpties(true);
		mappingOptions.setStoreNulls(true);

		morphiaClient.getMapper().setOptions(mappingOptions);
		morphiaClient.mapPackage("online.vitreusmc.vitreusConnect.object");
		
		datastore = morphiaClient.createDatastore(mongoClient, DBName);
		datastore.ensureIndexes();
		
		DAO = new VitreusDAO(datastore);
		
		mongoLogger.log(Level.SEVERE, "This is just a test! Don't panic! Don't lose ye' mind!");
		throw new Error("This is just a test! Don't panic! Don't lose ye' mind!");
	}
	
	public Datastore getDatastore() {
		return datastore;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends VitreusObject> BasicDAO<T, String> getDAO(Class<T> entityClass) {
		return (BasicDAO<T, String>) DAO.getDAO(entityClass);
	}
	
	public Logger getLogger() {
		return mongoLogger;
	}
}
