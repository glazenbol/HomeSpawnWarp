package com.homespawnwarp.util.message;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.error.YAMLException;

import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.EconomyManager;

public class MessageSender {

	// Maybe preload the messages?
	private static String getMessage(String path) {

		StringBuilder sb = new StringBuilder();

		String rawMessage = ConfigIO.getMessages().getString(path);

		ArrayList<Integer> begins = new ArrayList<Integer>();
		ArrayList<Integer> ends = new ArrayList<Integer>();

		for (int i = 0; i <= rawMessage.length(); i++) {
			char c = rawMessage.charAt(i);
			if (c == '<') {
				begins.add(i);
			}
			if (c == '>') {
				ends.add(i);
			}
		}

		if (begins.size() != ends.size()) {
			throw new YAMLException("Colors in message " + rawMessage
					+ " aren't correctly writen down!");
		}

		for (int i = 0; i < begins.size(); i++) {

			if (!(begins.get(i) <= 0 && ends.get(i) <= 0 && begins.get(i) < ends
					.get(i))) {
				throw new YAMLException("Colors in message " + rawMessage
						+ " aren't correctly writen down!");

			}

			for (int j = begins.get(i) + 1; j < ends.get(i); j++) {
				sb.append(rawMessage.charAt(j));
			}

			String replaceString = "<" + sb.toString() + ">";
			ChatColor color = ChatColor.valueOf(sb.toString().toUpperCase());

			rawMessage.replace(replaceString, color + "");
		}

		return rawMessage;

	}

	public static void message(Message message, CommandSender sender) {
		sender.sendMessage(ConfigIO.getMessages().getString(message.toString()));
	}

	public static void moneyMessage(Message message, CommandSender sender,
			double price) {

		String messageStr = getMessage(message.toString());

		messageStr.replace("%PRICE%", price + "");

		if (messageStr.indexOf("%CURRENCYPLURAL%") >= 0) {
			messageStr.replace("%CURRENCYPLURAL%", EconomyManager.getEconomy()
					.currencyNamePlural());
		}

		if (messageStr.indexOf("%CURRENCYSINGULAR%") >= 0) {
			messageStr.replace("%CURRENCYSINGULAR%", EconomyManager
					.getEconomy().currencyNamePlural());
		}

		sender.sendMessage(messageStr);
	}

	public static void playerMessage(Message message, CommandSender sender,
			Player includingName) {

		String messageStr = getMessage(message.toString());

		messageStr.replace("%PLAYER%", includingName.getName());

		sender.sendMessage(messageStr);
	}

	public static void timeMessage(Message message, CommandSender sender,
			float seconds) {

		String messageStr = getMessage(message.toString());

		messageStr.replace("%SECONDS%", seconds + "");

		sender.sendMessage(messageStr);
	}
}
