package com.homespawnwarp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.TeleportWarmup;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;

public class OnPlayerMoveWarmupCanceller implements Listener {

	public OnPlayerMoveWarmupCanceller(HomeSpawnWarp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (TeleportWarmup.contains(e.getPlayer())) {
			
			MessageSender.message(Message.TP_CANCELLED_BY_MOVEMENT,
					e.getPlayer());
			TeleportWarmup.cancel(e.getPlayer());
		}
	}
}
