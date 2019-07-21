package xyz.derkades.serverselectorx;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;

public class HDBHeads implements Listener {
	
	private HeadDatabaseAPI api = null;
	
	public HDBHeads() {
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
	}
	
	@EventHandler
	public void onDatabaseLoad(final DatabaseLoadEvent e) {
		this.api = new HeadDatabaseAPI();
	}
	
	public ItemStack getHead(final String id) {
		if (this.api == null)
			return new ItemStack(Material.STONE);
		else
			return this.api.getItemHead("7129");
	}

}
