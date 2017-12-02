package online.vitreusmc.vitreusConnect;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import net.md_5.bungee.api.ChatColor;
import online.vitreusmc.vitreusConnect.dao.VitreusPlayerDAO;
import online.vitreusmc.vitreusConnect.object.VitreusPlayer;

public class VitreusDB {

	private ServerAddress address;
	private List<MongoCredential> credientials = new ArrayList<MongoCredential>();
	private String db;
	
	private MongoClient client;
	private Morphia morphia;
	private Datastore datastore;
	
	private VitreusPlayerDAO playerDAO;
	
	public VitreusDB(String host, int port, String user, String userDb, String password, String db) {
		credientials.add(MongoCredential.createCredential(user, userDb, password.toCharArray()));
		this.db = db;
		
		try {
			address = new ServerAddress(host, port);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public VitreusDB(FileConfiguration config) {
		String host = config.getString("db.host");
		int port = config.getInt("db.port");
		String user = config.getString("db.user.name");
		String userDb = config.getString("db.user.authDBName");
		String password = config.getString("db.user.password");
		String db = config.getString("db.name");
		
		credientials.add(MongoCredential.createCredential(user, userDb, password.toCharArray()));
		this.db = db;
		
		try {
			address = new ServerAddress(host, port);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void connect() {
		
		try {
			client = new MongoClient(address, credientials);
			
			morphia = new Morphia();	
			morphia.map(VitreusPlayer.class);
		
			datastore = morphia.createDatastore(client, db);
			datastore.ensureIndexes();
				
			playerDAO = new VitreusPlayerDAO(VitreusPlayer.class, datastore);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	// Player Database Tasks
	public VitreusPlayer getPlayer(Player player) {
		VitreusPlayer vitreusPlayer = playerDAO.findOne("uuid", player.getUniqueId());
		
		if (vitreusPlayer == null) {
			vitreusPlayer = createPlayer(player);
		}
		
		return vitreusPlayer;
	}
	
	public void savePlayer(VitreusPlayer vitreusPlayer) {
		playerDAO.save(vitreusPlayer);
	}
	
	public VitreusPlayer createPlayer(Player player) {
		VitreusPlayer vitreusPlayer = new VitreusPlayer();
		
		vitreusPlayer.setUUID(player.getUniqueId().toString());
		vitreusPlayer.setUsername(player.getName());
		vitreusPlayer.setMinutesPlayed(player.getStatistic(Statistic.PLAY_ONE_TICK) / 20 / 60);
		vitreusPlayer.setLastOnline(new Date());
		vitreusPlayer.setColor(ChatColor.WHITE);
		
		playerDAO.save(vitreusPlayer);
		
		return vitreusPlayer;
	}
	
}