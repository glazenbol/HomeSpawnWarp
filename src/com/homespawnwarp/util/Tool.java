package com.homespawnwarp.util;

import com.homespawnwarp.plugin.HomeSpawnWarp;

public class Tool {
	protected static HomeSpawnWarp plugin;

	public static void init(HomeSpawnWarp plugin) {
		Tool.plugin = plugin;
	}
}
