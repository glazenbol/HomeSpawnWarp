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
		new SetHomeCommand(plugin, "HomeSpawnWarp.sethome",
				true);
		new HomeCommand(plugin, "HomeSpawnWarp.home", true);
		new HomeListCommand(plugin, "HomeSpawnWarp.homelist",
				true);
		new DelHomeCommand(plugin, "HomeSpawnWarp.delhome",
				true);
		new SetSpawnCommand(plugin, "HomeSpawnWarp.setspawn",
				false);
		new SpawnCommand(plugin, "HomeSpawnWarp.spawn", true);
		new SetWarpCommand(plugin, "HomeSpawnWarp.setwarp",
				false);
		new WarpCommand(plugin, "HomeSpawnWarp.warp", true);
		new WarpListCommand(plugin, "HomeSpawnWarp.warplist",
				true);
		new DelWarpCommand(plugin, "HomeSpawnWarp.delwarp",
				false);
		new WarpToCommand(plugin, "HomeSpawnWarp.warpto", true);
		new WarpAcceptCommand(plugin,
				"HomeSpawnWarp.warpaccept", true);
	}
}
