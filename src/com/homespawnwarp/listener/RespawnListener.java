package com.homespawnwarp.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.Tools;

public class RespawnListener implements Listener {

	HomeSpawnWarp plugin;

	public RespawnListener(HomeSpawnWarp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if (!e.isBedSpawn()) {
			Player player = e.getPlayer();

			Location l = LocationIO.read("homes." + player.getUniqueId()
					+ ".default");

			if (l != null) {

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				e.setRespawnLocation(l);

			} else {
				if (plugin.useGeneralSpawn) {
					// Exact general spawn teleport
					String[] s = Tools.getLocations()
							.getConfigurationSection("spawn").getKeys(false)
							.toArray(new String[0]);
					if (s.length != 0) {
						l = LocationIO.read("spawn." + s[0]);

						if (l != null) {

							if (!l.getChunk().isLoaded()) {
								l.getChunk().load();
							}
							e.setRespawnLocation(l);
						}
					}


				} else {
					// Exact players current world spawn
					String s = e.getRespawnLocation().getWorld().getName();

					if (Tools.getLocations().contains("spawn." + s)) {

						l = LocationIO.read("spawn." + s);

						if (!l.getChunk().isLoaded()) {
							l.getChunk().load();
						}
						e.setRespawnLocation(l);
					}
				}
			}
		}
	}
}
