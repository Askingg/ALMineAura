package me.syn.alminer.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.syn.alminer.commands.ALMinerCMD;
import me.syn.alminer.events.PlayerJoin;
import me.syn.alminer.utils.Files;
import me.syn.alminer.utils.Message;

public class Main extends JavaPlugin {

	public static String prefix = "&8&l(&d&l!&8&l)&d &l»&7 ";
	
	public void onEnable() {
		Message.console("&7");

		Files.base();
		getCommand("almineaura").setExecutor(new ALMinerCMD());
		getServer().getPluginManager().registerEvents(new MinerGUI(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new Placeholders().register();
		}
		Miner.timer(this);
		
		Message.console("&7");
	}
	
	public void onDisable() {
		Files.config.set("users", null);
		for (String p : Miner.time.keySet()) {
			Files.config.set("users." + p + ".time", Miner.time.get(p));
		}
		for (String p : Miner.block.keySet()) {
			Files.config.set("users." + p + ".upgrades.defaultblock", Miner.block.get(p));
		}
		for (String p : Miner.power.keySet()) {
			Files.config.set("users." + p + ".upgrades.miningpower", Miner.power.get(p));
		}
		try {
			Files.config.save(Files.configFile);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
