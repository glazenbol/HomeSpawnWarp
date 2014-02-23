package com.homespawnwarp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.Tools;

public class PlayerMoveListener implements Listener {

	public PlayerMoveListener(HomeSpawnWarp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (Teleportation.warmupsContainsPlayer(e.getPlayer())) {
			e.getPlayer().sendMessage(
					Tools.getMessage("teleportation-cancelled-by-move"));
			Teleportation.cancel(e.getPlayer());
			Teleportation.removeWarmup(e.getPlayer());
		}
	}
}
