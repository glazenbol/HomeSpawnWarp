package com.homespawnwarp.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.Teleportation;
import com.homespawnwarp.util.TeleportationType;
import com.homespawnwarp.util.Tools;

public class JoinListener implements Listener {
	HomeSpawnWarp plugin;

	public JoinListener(HomeSpawnWarp hsw) {
		hsw.getServer().getPluginManager().registerEvents(this, hsw);
		plugin = hsw;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPlayedBefore()) {

			String[] s = Tools.getLocations().getConfigurationSection("spawn")
					.getKeys(false).toArray(new String[0]);

			if (s.length != 0) {
				Location l = LocationIO.read("spawn." + s[0]);

				if (l != null) {

					Teleportation.teleportPlayer(e.getPlayer(), l,
							TeleportationType.SPAWN, 0, false, false, 0);
				}
			}
		}
	}
}
