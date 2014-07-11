package com.homespawnwarp.tool;

import com.homespawnwarp.plugin.HomeSpawnWarp;

public class Tool {
	protected static HomeSpawnWarp plugin;

	public static void init(HomeSpawnWarp plugin) {
		Tool.plugin = plugin;
	}
}
