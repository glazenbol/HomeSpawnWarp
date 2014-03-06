package com.homespawnwarp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.homespawnwarp.event.TeleportWarmupCompleteEvent;
import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Teleportation;

public class TeleportWarmupCompleteListener implements Listener {

	public TeleportWarmupCompleteListener(final HomeSpawnWarp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onTimerCompleteEvent(TeleportWarmupCompleteEvent e) {
		if (e.getPlayer().isOnline()) {

			Teleportation.teleportPlayer(e.getPlayer(), e.getLocation(),
					e.getTeleportationType(), e.getPrice(), true, false);

		}
	}
}
