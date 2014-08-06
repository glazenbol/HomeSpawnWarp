package com.homespawnwarp.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;

final public class Teleportation {

	private static boolean warmupsEnabled = false;
	private static int homeWarmup;
	private static int spawnWarmup;
	private static int warpWarmup;
	private static int requestWarmup;

	private static HomeSpawnWarp plugin;
	private static FireworkEffectPlayer fwep;

	static volatile public HashMap<UUID, TeleportWarmup> teleportWarmups = new HashMap<UUID, TeleportWarmup>();
	static volatile public HashMap<UUID, TeleportRequest> teleportRequests = new HashMap<UUID, TeleportRequest>();

	public static void init(HomeSpawnWarp plugin, boolean useFireworkEffects) {

		Teleportation.plugin = plugin;

		if (useFireworkEffects) {
			fwep = new FireworkEffectPlayer();
		}
	}

	public static boolean createTeleportWarmup(final Player player,
			final Location l, final TeleportationType type, double price) {
		if (warmupsEnabled) {
			if (teleportWarmups.containsKey(player.getUniqueId())) {
				return true;
			} else {
				switch (type) {
				case HOME:
					if (homeWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getUniqueId(),
								new TeleportWarmup(plugin, player, l, type,
										homeWarmup, price));
					}
					break;
				case SPAWN:
					if (spawnWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getUniqueId(),
								new TeleportWarmup(plugin, player, l, type,
										spawnWarmup, price));
					}
					break;
				case WARP:
					if (warpWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getUniqueId(),
								new TeleportWarmup(plugin, player, l, type,
										warpWarmup, price));
					}
					break;
				case REQUEST:
					if (requestWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getUniqueId(),
								new TeleportWarmup(plugin, player, l, type,
										requestWarmup, price));
					}
					break;
				}
				new Thread(teleportWarmups.get(player.getUniqueId())).start();
				return true;
			}
		} else {
			return false;
		}
	}

	public static boolean usesWarmup() {
		return warmupsEnabled;
	}

	public static void setWarmups(double homeWarmup, double spawnWarmup,
			double warpWarmup, double requestWarmup) {
		warmupsEnabled = (homeWarmup > 0 || spawnWarmup > 0 || warpWarmup > 0
				&& requestWarmup > 0);
		Teleportation.homeWarmup = (int) homeWarmup;
		Teleportation.spawnWarmup = (int) spawnWarmup;
		Teleportation.warpWarmup = (int) warpWarmup;
		Teleportation.requestWarmup = (int) requestWarmup;
	}

	public static void createRequest(final Player player,
			final Player targetPlayer, double price) {
		teleportRequests.put(targetPlayer.getUniqueId(), new TeleportRequest(
				player, targetPlayer));
		new Thread(teleportRequests.get(targetPlayer.getUniqueId())).start();
	}

	public static void removeRequest(final Player targetPlayer) {
		if (teleportRequests.containsKey(targetPlayer.getUniqueId())) {
			teleportRequests.remove(targetPlayer.getUniqueId());
		}
	}

	public static void removeWarmup(final Player player) {
		teleportWarmups.remove(player.getUniqueId());
	}

	public static void teleportPlayer(final Player player, final Location l,
			final TeleportationType type, double price,
			final boolean sendMessage, final boolean useWarmup,
			final boolean useFirework) {

		if (useWarmup
				&& !PermissionAgent.checkPerm(player, Permission.NOWARMUP,
						false, false)
				&& createTeleportWarmup(player, l, type, price)) {

			return;

		}

		finalizeTeleport(player, type, l, sendMessage, price, useFirework);
	}

	public static void teleportPlayer(final Player player, final Location l,// default
																			// teleporting
			final TeleportationType type, double price) {
		teleportPlayer(player, l, type, price, true, true, true);
	}

	public static void finalizeTeleport(Player player, TeleportationType type,
			Location l, boolean sendMessage, double price, boolean useFirework) {
		if (MoneyMachine.takeMoney(player, price)) {

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

			if (useFirework
					&& fwep != null
					&& PermissionAgent.checkPerm(player,
							Permission.TELEPORTEFFECT, true, false)) {
				try {
					fwep.playFirework(player.getWorld(), player.getLocation(),
							FireworkEffectGenerator.randomBurstEffect());
				} catch (Exception e) {
				}
			}

			player.teleport(l); // ALL teleports go thru here
		}
	}

	public static boolean warmupsContainsPlayer(Player player) {
		return teleportWarmups.containsKey(player.getUniqueId());
	}

	public static void cancel(Player player) {
		teleportWarmups.get(player.getUniqueId()).cancel();
		removeRequest(player);// THIS IS A BIT TRICKY
	}
}