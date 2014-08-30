package com.homespawnwarp.util.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.EconomyManager;

public class MessageSender {

	// Maybe preload the messages?

	private static String getMessage(String path) {

		StringBuilder sb = new StringBuilder();

		String rawMessage = ConfigIO.getMessages().getString(path);

		while (rawMessage.indexOf('<') >= 0) {
			sb.setLength(0);
			for (int j = rawMessage.indexOf('<') + 1; rawMessage.charAt(j) != '>'; j++) {
				sb.append(rawMessage.charAt(j));
			}

			ChatColor color = ChatColor.valueOf(sb.toString().toUpperCase());
			String replaceString = "<" + sb.toString() + ">";

			rawMessage = rawMessage.replace(replaceString, color + "");

		}

		return rawMessage;

	}

	public static void message(Message message, CommandSender sender) {
		sender.sendMessage(getMessage(message.toString()));
	}

	public static void moneyMessage(Message message, CommandSender sender,
			double price) {

		String messageStr = getMessage(message.toString());

		messageStr = messageStr.replace("%PRICE%", price + "");

		if (messageStr.indexOf("%CURRENCYPLURAL%") >= 0) {
			messageStr = messageStr.replace("%CURRENCYPLURAL%", EconomyManager
					.getEconomy().currencyNamePlural());
		}

		if (messageStr.indexOf("%CURRENCYSINGULAR%") >= 0) {
			messageStr = messageStr.replace("%CURRENCYSINGULAR%",
					EconomyManager.getEconomy().currencyNamePlural());
		}

		sender.sendMessage(messageStr);
	}

	public static void playerMessage(Message message, CommandSender sender,
			Player includingName) {

		String messageStr = getMessage(message.toString());

		messageStr = messageStr.replace("%PLAYER%", includingName.getName());

		sender.sendMessage(messageStr);
	}

	public static void timeMessage(Message message, CommandSender sender,
			float seconds) {

		String messageStr = getMessage(message.toString());

		messageStr = messageStr.replace("%SECONDS%", seconds + "");

		sender.sendMessage(messageStr);
	}
}
