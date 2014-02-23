package com.homespawnwarp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.homespawnwarp.event.TimerCompleteEvent;
import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Teleportation;

public class TimerCompleteListener implements Listener {

	public TimerCompleteListener(final HomeSpawnWarp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onTimerCompleteEvent(TimerCompleteEvent e) {
		if (e.getPlayer().isOnline()) {

			Teleportation.teleportPlayer(e.getPlayer(), e.getLocation(),
					e.getTeleportationType(), true, false);

		}
	}
}
