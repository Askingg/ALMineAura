package me.syn.alminer.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.syn.alminer.main.Main;
import me.syn.alminer.main.Miner;
import me.syn.alminer.main.MinerGUI;
import me.syn.alminer.utils.Format;
import me.syn.alminer.utils.Message;

public class ALMinerCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lab, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				MinerGUI.open.put(p, "home");
				p.openInventory(MinerGUI.menu(p));
			}
		} else {
			if (args[0].equalsIgnoreCase("help")) {
				Message.sender(Main.prefix + "Commands for &dAfterLife-MineAura", sender);
				Message.sender("&8 ● &d/ALMineAura&7 Open the MineAura panel", sender);
				Message.sender("&8 ● &d/ALMineAura Help&7 View the help list", sender);
				Message.sender("&8 ● &d/ALMineAura Time&7 View how much MineAura time you have", sender);
				Message.sender("&8 ● &d/ALMineAura addTime&7 Add time directly to a user", sender);
				Message.sender("&8 ● &d/ALMineAura giveTime&7 Give a user a time chip", sender);
				Message.sender("&8 ● &d/ALMineAura giveUpgrade&7 Give a user an upgrade chip", sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("time")) {
				if (sender instanceof Player) {
					Message.sender(
							Main.prefix + "You have &d" + Miner.getTime((Player) sender) + "&7 of &dMineAura&7 time",
							sender);
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("addTime")) { // ALMiner addTime <Player> <Seconds>
				if (sender instanceof ConsoleCommandSender || sender.hasPermission("alminer.command.addtime")) {
					if (args.length >= 3) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p == null) {
							Message.sender(Main.prefix + "Sorry, but &c" + args[1] + "&7 is is an invalid player",
									sender);
							return true;
						}
						int a = 0;
						try {
							a = Integer.parseInt(args[2]);
						} catch (Exception ex) {
							Message.sender(Main.prefix + "Sorry, but &c" + args[2] + "&7 is an invalid integer",
									sender);
							return true;
						}
						if (a < 1) {
							Message.sender(Main.prefix + "Sorry, but the amount must be &c> 0", sender);
							return true;
						}
						Miner.addTime(p, a);
						Message.sender(Main.prefix + "You gave &d" + Format.time(a) + "&7 of &dMineAura&7 time to &d"
								+ args[1], sender);
						Message.player(Main.prefix + "&a+ &d" + Format.time(a) + " MineAura&7 time", p);
					}
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("giveUpgrade")) { // ALMiner giveUpgrade <Player> <UpgradeType> <amount>
				if (sender instanceof ConsoleCommandSender || sender.hasPermission("alminer.command.giveupgrade")) {
					if (args.length >= 3) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p == null) {
							Message.sender(Main.prefix + "Sorry, but &c" + args[1] + "&7 is is an invalid player",
									sender);
							return true;
						}
						if (!(args[2].equalsIgnoreCase("defaultblock") || args[2].equalsIgnoreCase("miningpower"))) {
							Message.sender(
									Main.prefix + "Sorry, but &c" + args[2]
											+ "&7 is an invalid &cUpgradeType&7: &dDefaultBlock&7, &dMiningPower",
									sender);
							return true;
						}
						int a = 1;
						if (args.length == 4) {
							try {
								a = Integer.parseInt(args[3]);
							} catch (Exception ex) {
								Message.sender(Main.prefix + "Sorry, but &c" + args[3] + "&7 is an invalid integer",
										sender);
								return true;
							}
							if (a < 1) {
								Message.sender(Main.prefix + "Sorry, but the amount must be &c> 0", sender);
								return true;
							}
						}
						if (args[2].equalsIgnoreCase("defaultblock")) {
							p.getInventory().addItem(Miner.blockChip(a));
							Message.sender(
									Main.prefix + "You gave &d" + a
											+ "&d MineAura Upgrade Chips &8(&dDefaultBlock&8)&7 to &d" + args[1],
									sender);
							Message.player(Main.prefix + "&a + &d" + a + " MineAura Upgrade Chips &8(&dDefaultBlock&8)",
									p);
						}
						if (args[2].equalsIgnoreCase("miningpower")) {
							p.getInventory().addItem(Miner.powerChip(a));
							Message.sender(
									Main.prefix + "You gave &d" + a
											+ "&d MineAura Upgrade Chips &8(&dMiningPower&8)&7 to &d" + args[1],
									sender);
							Message.player(Main.prefix + "&a + &d" + a + " MineAura Upgrade Chips &8(&dMiningPower&8)",
									p);
						}
						return true;
					} else {
						Message.sender(Main.prefix + "Usage &d/ALMiner giveUpgrade <Player> <UpgradeType> <Amount>",
								sender);
						return true;
					}
				}
				return true;
			}
		}
		return false;
	}

}
