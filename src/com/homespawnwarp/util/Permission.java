package com.homespawnwarp.util;

public enum Permission {
	
	// CommandPermissions
	SETHOME("HomeSpawnWarp.sethome"),
	HOME("HomeSpawnWarp.home"),
	HOMELIST("HomeSpawnWarp.homelist"),
	DELHOME("HomeSpawnWarp.delhome"),
	SETSPAWN("HomeSpawnWarp.setspawn"),
	SPAWN("HomeSpawnWarp.spawn"),
	SETWARP("HomeSpawnWarp.setwarp"),
	WARP("HomeSpawnWarp.warp"),
	WARPLIST("HomeSpawnWarp.warplist"),
	DELWARP("HomeSpawnWarp.delwarp"),
	WARPTO("HomeSpawnWarp.warpto"),
	WARPACCEPT("HomeSpawnWarp.warpaccept"),
	// Misc permissions
	UNLIMITED_HOMES("HomeSpawnWarp.home.UNLIMITED"),
	HOMEGROUP("HomeSpawnWarp.home.GROUP"),
	PRICES("HomeSpawnWarp.prices"),
	
	TELEPORTEFFECT("HomeSpawnWarp.teleporteffect"),
	
	NOFEE("HomeSpawnWarp.nofee"),
	NOWARMUP("HomeSpawnWarp.nowarmup");
	
	private final String stringValue;

	private Permission(final String s) {
		stringValue = s;
	}

	public String toString() {
		return stringValue;
	}
}
