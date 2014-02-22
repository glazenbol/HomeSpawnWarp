package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

	// MAKE LISTENER FAILPROOF

	HomeSpawnWarp plugin;

	public RespawnListener(HomeSpawnWarp hsw) {
		hsw.getServer().getPluginManager().registerEvents(this, hsw);
		plugin = hsw;
	}
	
	@EventHandler
	void onPlayerJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPlayedBefore()) {
			Location l;
			String[] s = plugin.spawn.getKeys(false)
					.toArray(new String[0]);

			if (s.length != 0) {
				l = new Location(plugin.getServer().getWorld(s[0]),
						(float) plugin.spawn.getDouble(s[0] + ".x"),
						(float) plugin.spawn.getDouble(s[0] + ".y"),
						(float) plugin.spawn.getDouble(s[0] + ".z"),
						(float) plugin.spawn.getDouble(s[0] + ".yaw"),
						(float) plugin.spawn.getDouble(s[0] + ".pitch"));
				l.getChunk().load();
				e.getPlayer().teleport(l);
			}
		}
	}

	@EventHandler
	void onPlayerRespawn(PlayerRespawnEvent e) {
		if (!e.isBedSpawn()) {
			Location l;
			
			if (plugin.useGeneralSpawn) {
				// Exact general spawn teleport
				String[] s = plugin.spawn.getKeys(false)
						.toArray(new String[0]);

				if (s.length != 0) {
					l = new Location(plugin.getServer().getWorld(s[0]),
							(float) plugin.spawn.getDouble(s[0] + ".x"),
							(float) plugin.spawn.getDouble(s[0] + ".y"),
							(float) plugin.spawn.getDouble(s[0] + ".z"),
							(float) plugin.spawn.getDouble(s[0] + ".yaw"),
							(float) plugin.spawn.getDouble(s[0] + ".pitch"));
					l.getChunk().load();
					e.setRespawnLocation(l);
				}

			} else {
				// Exact players current world spawn
				String s = e.getRespawnLocation().getWorld().getName();

				if (plugin.getConfig().contains(s)) {
					l = new Location(e.getRespawnLocation().getWorld(),
							(float) plugin.spawn.getDouble(s + ".x"),
							(float) plugin.spawn.getDouble(s + ".y"),
							(float) plugin.spawn.getDouble(s + ".z"),
							(float) plugin.spawn.getDouble(s + ".yaw"),
							(float) plugin.spawn.getDouble(s + ".pitch"));
					
					l.getChunk().load();
					e.setRespawnLocation(l);
				}

			}

		}
	}
}
