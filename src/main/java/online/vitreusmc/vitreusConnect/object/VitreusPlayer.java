package online.vitreusmc.vitreusConnect.object;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.PostLoad;
import org.mongodb.morphia.annotations.Reference;

import net.md_5.bungee.api.ChatColor;
import online.vitreusmc.vitreusConnect.property.Flag;
import online.vitreusmc.vitreusConnect.property.Milestone;

@Entity(value = "Players", noClassnameStored = true)
public class VitreusPlayer implements VitreusObject {

	@Id private ObjectId id;
	
	@Indexed(options = @IndexOptions(unique = true))
	private String UUID;
	
	@Indexed(options = @IndexOptions(unique = true))
	private String username;

	@Reference private Mailbox mailbox;
	
	@Embedded private ArrayList<Flag> flags; 
	@Embedded private ArrayList<Milestone> milestones;
	
	private ChatColor color;
	private Date lastOnline;
	private int minutesPlayed;
	
	@PostLoad
	public void initialize() {
		if (flags == null)
			flags = new ArrayList<Flag>();
		if (milestones == null)
			milestones = new ArrayList<Milestone>();
	}
	
	public static VitreusPlayer create(Player player) {
		VitreusPlayer vitreusPlayer = new VitreusPlayer();
				
		vitreusPlayer.UUID = player.getUniqueId().toString();
		vitreusPlayer.username = player.getName();
		vitreusPlayer.mailbox = null;
		vitreusPlayer.flags = new ArrayList<Flag>();
		vitreusPlayer.milestones = new ArrayList<Milestone>();
		vitreusPlayer.color = ChatColor.WHITE;
		vitreusPlayer.lastOnline = new Date();
		vitreusPlayer.minutesPlayed = player.getStatistic(Statistic.PLAY_ONE_TICK) / 20 / 60;
		
		return vitreusPlayer;
	}
	
	public void setUUID(String UUID) {
		this.UUID = UUID;
	}
	
	public String getUUID() {
		return UUID;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setColor(ChatColor color) {
		this.color = color;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public void setLastOnline(Date date) {
		this.lastOnline = date;
	}
	
	public Date getLastOnline() {
		return lastOnline;
	}
	
	public void setMinutesPlayed(int minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}
	
	public int getMinutesPlayed() {
		return minutesPlayed;
	}
	
	public void addFlag(Flag flag) {
		flags.add(flag);
	}
	
	public void removeFlag(Flag flag) {
		flags.remove(flag);
	}
	
	public ArrayList<Flag> getFlags() {
		return flags;
	}
	
	public boolean hasFlag(Flag flag) {
//		if (flags == null)
//			return false;
//		else
//			return flags.contains(flag);
		return false;
	}
	
	public void addMilestone(Milestone milestone) {
		milestones.remove(milestone);
	}
	
	public void removeMilestone(Milestone milestone) {
		milestones.remove(milestone);
	}

	public ArrayList<Milestone> getMilestones() {
		return milestones;
	}
	
	public boolean hasMilestone(Milestone milestone) {
//		if (milestones == null)
//			return false;
//		else
//			return milestones.contains(milestone);
		return false;
	}
	
	public Mailbox getMailbox() {
		return mailbox;
	}
	
	public void setMailbox(Mailbox mailbox) {
		this.mailbox = mailbox;
	}
	
	public boolean hasMailbox() {
		return mailbox != null;
	}

}
