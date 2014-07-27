package com.homespawnwarp.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.homespawnwarp.event.TeleportEvent;
import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.FireworkEffectGenerator;
import com.homespawnwarp.util.FireworkEffectPlayer;
import com.homespawnwarp.util.MoneyMachine;
import com.homespawnwarp.util.PermissionAgent;
import com.homespawnwarp.util.TeleportationType;
import com.homespawnwarp.util.Tools;

public class TeleportListener implements Listener {

	private FireworkEffectPlayer fwep;

	public TeleportListener(final HomeSpawnWarp plugin,
			boolean useFireworkEffects) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		if (useFireworkEffects) {
			fwep = new FireworkEffectPlayer();
		}
	}

	@EventHandler
	public void OnTeleportEvent(TeleportEvent te) {

		boolean sendMessage = te.sendMessage();
		TeleportationType type = te.getTeleportationType();
		Player player = te.getPlayer();
		Location l = te.getLocation();

		if (MoneyMachine.takeMoney(player, te.getPrice())) {

			if (!l.getChunk().isLoaded()) {
				l.getChunk().load();
			}

			if (sendMessage) {
				switch (type) {
				case HOME:
					player.sendMessage(Tools.getMessage("teleport-to-home"));
					break;
				case SPAWN:
					player.sendMessage(Tools.getMessage("teleport-to-spawn"));
					break;
				case WARP:
					player.sendMessage(Tools.getMessage("teleport-to-warp"));
					break;
				case REQUEST:
					player.sendMessage(Tools.getMessage("teleporting"));
				default:
					break;
				}
			}

			if (fwep != null
					&& PermissionAgent.checkPerm(player,
							"HomeSpawnWarp.teleporteffect", true, false)) {
				try {
					fwep.playFirework(player.getWorld(), player.getLocation(),
							FireworkEffectGenerator.randomBurstEffect());
				} catch (Exception e) {
				}
			}

			player.teleport(l); // ALL teleports go thru here
		}
	}

}
