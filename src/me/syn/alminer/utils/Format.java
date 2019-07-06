package me.syn.alminer.utils;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class Format {

	public static List<String> highLow(HashMap<String, Integer> inputs) {
		List<String> sorted = inputs.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.map(Map.Entry::getKey).collect(Collectors.toList());
		return sorted;
	}
	
	public static String time(int seconds) {
		if (seconds < 60) {
			return seconds + "s";
		}
		int minutes = seconds / 60;
		int s = 60 * minutes;
		int secondsLeft = seconds - s;
		if (minutes < 60) {
			if (secondsLeft > 0) {
				return String.valueOf(minutes + "m " + secondsLeft + "s");
			}
			return String.valueOf(minutes + "m");
		}
		if (minutes < 1440) {
			String time = "";
			int hours = minutes / 60;
			time = hours + "h";
			int inMins = 60 * hours;
			int leftOver = minutes - inMins;
			if (leftOver >= 1) {
				time = time + " " + leftOver + "m";
			}
			if (secondsLeft > 0) {
				time = time + " " + secondsLeft + "s";
			}
			return time;
		}
		String time = "";
		int days = minutes / 1440;
		time = days + "d";
		int inMins = 1440 * days;
		int leftOver = minutes - inMins;
		if (leftOver >= 1) {
			if (leftOver < 60) {
				time = time + " " + leftOver + "m";
			} else {
				int hours = leftOver / 60;
				time = time + " " + hours + "h";
				int hoursInMins = 60 * hours;
				int minsLeft = leftOver - hoursInMins;
				if (leftOver >= 1) {
					time = time + " " + minsLeft + "m";
				}
			}
		}
		if (secondsLeft > 0) {
			time = time + " " + secondsLeft + "s";
		}
		return time;
	}

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static String decimals(Integer decimalSpaces, Double number) {
		return String.format("%1$,." + decimalSpaces + "f", number);
	}

	public static String papi(Player p, String str) {
		return PlaceholderAPI.setPlaceholders((OfflinePlayer) p, str);
	}

	public static String number(double d) {
		if (d < 1000.0D) {
			return format(d);
		}
		if (d < 1000000.0D) {
			return format(d / 1000.0D) + "k";
		}
		if (d < 1.0E9D) {
			return format(d / 1000000.0D) + "M";
		}
		if (d < 1.0E12D) {
			return format(d / 1.0E9D) + "B";
		}
		if (d < 1.0E15D) {
			return format(d / 1.0E12D) + "T";
		}
		if (d < 1.0E18D) {
			return format(d / 1.0E15D) + "Q";
		}

		long l = (long) d;
		return String.valueOf(l);
	}

	public static String format(double d) {
		NumberFormat localNumberFormat = NumberFormat.getInstance(Locale.ENGLISH);

		localNumberFormat.setMaximumFractionDigits(2);

		localNumberFormat.setMinimumFractionDigits(0);

		return localNumberFormat.format(d);
	}

	public static void hover(Player p, String chat, String click, String hover) {
		p.spigot()
				.sendMessage(new ComponentBuilder(color(chat))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color(hover)).create()))
						.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, click)).create());
	}

	public static void hoverBroadcast(String chat, String click, String hover) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.spigot().sendMessage(new ComponentBuilder(color(chat))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color(hover)).create()))
					.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, click)).create());
		}
	}
}
