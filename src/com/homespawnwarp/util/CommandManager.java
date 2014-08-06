package com.homespawnwarp.util;

import com.homespawnwarp.cmd.DelHomeCommand;
import com.homespawnwarp.cmd.DelWarpCommand;
import com.homespawnwarp.cmd.HomeCommand;
import com.homespawnwarp.cmd.HomeListCommand;
import com.homespawnwarp.cmd.SetHomeCommand;
import com.homespawnwarp.cmd.SetSpawnCommand;
import com.homespawnwarp.cmd.SetWarpCommand;
import com.homespawnwarp.cmd.SpawnCommand;
import com.homespawnwarp.cmd.WarpAcceptCommand;
import com.homespawnwarp.cmd.WarpCommand;
import com.homespawnwarp.cmd.WarpListCommand;
import com.homespawnwarp.cmd.WarpToCommand;
import com.homespawnwarp.plugin.HomeSpawnWarp;

public class CommandManager {

	private HomeSpawnWarp plugin;

	public CommandManager(HomeSpawnWarp plugin) {
		this.plugin = plugin;
	}

	public void initCommands() {
		new SetHomeCommand(plugin, Permission.SETHOME, true);
		new HomeCommand(plugin, Permission.HOME, true);
		new HomeListCommand(plugin, Permission.HOMELIST, true);
		new DelHomeCommand(plugin, Permission.DELHOME, true);
		new SetSpawnCommand(plugin, Permission.SETSPAWN, false);
		new SpawnCommand(plugin, Permission.SPAWN, true);
		new SetWarpCommand(plugin, Permission.SETWARP, false);
		new WarpCommand(plugin, Permission.WARP, true);
		new WarpListCommand(plugin, Permission.WARPLIST, true);
		new DelWarpCommand(plugin, Permission.DELWARP, false);
		new WarpToCommand(plugin, Permission.WARPTO, true);
		new WarpAcceptCommand(plugin, Permission.WARPACCEPT, true);
	}
}
