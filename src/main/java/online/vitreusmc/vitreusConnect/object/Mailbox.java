package online.vitreusmc.vitreusConnect.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.annotations.PostLoad;

@Entity(value = "Mailboxes", noClassnameStored = true)
public class Mailbox implements VitreusObject {
	
	@Id private int id;

	@Indexed(options = @IndexOptions(unique = true))
	private String title;
	
	@Reference private VitreusPlayer owner;
	
	@Embedded private Location location;

	@Property private boolean personal;
	@Transient private Inventory inventory = ((Chest) location.getBlock().getState()).getInventory();	
	
	@PostLoad
	private void update() {
		if (personal) 
			this.title = owner.getUsername();
	}
	
	public Mailbox(VitreusPlayer owner, Location location) {
		this.title = owner.getUsername();
		this.owner = owner;
		this.location = location;
		this.personal = true;
	}
	
	public Mailbox(String title, Location location) {
		this.title = title;
		this.owner = null;
		this.location = location;
		this.personal = false;
	}
	
	public boolean belongsTo(VitreusPlayer player) {
		return player.getUUID() == owner.getUUID();
	}
	
	public void addItem(ItemStack item) {
		inventory.addItem(item);
	}
	
	public void removeItem(ItemStack item) {
		inventory.remove(item);
	}
	
	public void removeItem(Material material) {
		inventory.remove(material);
	}
	
	public boolean containsItem(ItemStack item) {
		return inventory.contains(item);
	}
	
	public boolean containsItem(Material material) {
		return inventory.contains(material);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isPersonal() {
		return personal;
	}

	public void setPersonal(boolean personal) {
		this.personal = personal;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public VitreusPlayer getOwner() {
		return owner;
	}
	
	public void setOwner(VitreusPlayer owner) {
		this.owner = owner;
	}
}
