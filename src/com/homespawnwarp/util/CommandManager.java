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

	private SetHomeCommand setHomeCommand;
	private HomeCommand homeCommand;
	private HomeListCommand homeListCommand;
	private DelHomeCommand delHomeCommand;
	private SetSpawnCommand setSpawnCommand;
	private SpawnCommand spawnCommand;
	private SetWarpCommand setWarpCommand;
	private WarpCommand warpCommand;
	private WarpListCommand warpListCommand;
	private DelWarpCommand delWarpCommand;
	private WarpToCommand warpToCommand;
	private WarpAcceptCommand warpAcceptCommand;

	public CommandManager(HomeSpawnWarp plugin) {
		this.plugin = plugin;
	}

	public void initCommands() {
		setHomeCommand = new SetHomeCommand(plugin, "HomeSpawnWarp.sethome",
				true);
		homeCommand = new HomeCommand(plugin, "HomeSpawnWarp.home", true);
		homeListCommand = new HomeListCommand(plugin, "HomeSpawnWarp.homelist",
				true);
		delHomeCommand = new DelHomeCommand(plugin, "HomeSpawnWarp.delhome",
				true);
		setSpawnCommand = new SetSpawnCommand(plugin, "HomeSpawnWarp.setspawn",
				false);
		spawnCommand = new SpawnCommand(plugin, "HomeSpawnWarp.spawn", true);
		setWarpCommand = new SetWarpCommand(plugin, "HomeSpawnWarp.setwarp",
				false);
		warpCommand = new WarpCommand(plugin, "HomeSpawnWarp.warp", true);
		warpListCommand = new WarpListCommand(plugin, "HomeSpawnWarp.warplist",
				true);
		delWarpCommand = new DelWarpCommand(plugin, "HomeSpawnWarp.delwarp",
				false);
		warpToCommand = new WarpToCommand(plugin, "HomeSpawnWarp.warpto", true);
		warpAcceptCommand = new WarpAcceptCommand(plugin,
				"HomeSpawnWarp.warpaccept", true);
	}
}
