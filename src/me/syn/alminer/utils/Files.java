package me.syn.alminer.utils;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.syn.alminer.main.Main;
import me.syn.alminer.main.Miner;

public class Files {

	public static File configFile;
	public static FileConfiguration config;

	public static void base() {
		Main main = Main.getPlugin(Main.class);
		if (!main.getDataFolder().exists()) {
			main.getDataFolder().mkdirs();
			Message.console(
					Main.prefix + "&7Created the '&d" + main.getDataFolder().getName().toString() + "&7' folder");
		}
		configFile = new File(main.getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			main.saveResource("config.yml", false);
			Message.console(Main.prefix + "&7Created the '&dconfig.yml&7' file");
		}
		config = YamlConfiguration.loadConfiguration(configFile);
		if (config.getConfigurationSection("users") != null) {
			for (String p : config.getConfigurationSection("users").getKeys(false)) {
				Integer x = Files.config.getInt("users." + p + ".time");
				if (x != null) {
					Miner.time.put(p, x);
				}
				x = Files.config.getInt("users." + p + ".upgrades.defaultblock");
				if (x != null) {
					Miner.block.put(p, x);
				}
				x = Files.config.getInt("users." + p + ".upgrades.miningpower");
				if (x != null) {
					Miner.power.put(p, x);
				}
			}
		}
	}
}