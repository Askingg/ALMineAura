package me.syn.alminer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.syn.alminer.main.Miner;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!Miner.time.containsKey(p.getName())) {
			Miner.time.put(p.getName(), 300);
			Miner.block.put(p.getName(), 1);
			Miner.power.put(p.getName(), 1);
		}
	}
}
