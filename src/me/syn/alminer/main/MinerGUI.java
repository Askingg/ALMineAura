package me.syn.alminer.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.syn.alautosell.utils.Message;
import me.syn.alminer.utils.Format;

public class MinerGUI implements Listener {

	public static HashMap<Player, String> open = new HashMap<Player, String>();

	@SuppressWarnings("deprecation")
	public static Inventory menu(Player p) {
		if (open.get(p).equals("home")) {
			Inventory inv = Bukkit.createInventory(null, 36, Format.color("&d&lMineAura"));
			ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE);
			i.setDurability((byte) 7);
			ItemMeta m = i.getItemMeta();
			m.setDisplayName(Format.color("&7"));
			i.setItemMeta(m);
			inv.setItem(1, i);
			inv.setItem(2, i);
			inv.setItem(3, i);
			inv.setItem(5, i);
			inv.setItem(6, i);
			inv.setItem(7, i);
			inv.setItem(12, i);
			inv.setItem(13, i);
			inv.setItem(14, i);
			inv.setItem(18, i);
			inv.setItem(19, i);
			inv.setItem(25, i);
			inv.setItem(26, i);
			inv.setItem(28, i);
			inv.setItem(29, i);
			inv.setItem(33, i);
			inv.setItem(34, i);
			i.setDurability((byte) 2);
			inv.setItem(0, i);
			inv.setItem(8, i);
			inv.setItem(9, i);
			inv.setItem(10, i);
			inv.setItem(16, i);
			inv.setItem(17, i);
			inv.setItem(20, i);
			inv.setItem(21, i);
			inv.setItem(23, i);
			inv.setItem(24, i);
			inv.setItem(30, i);
			inv.setItem(32, i);
			i = new ItemStack(Material.getMaterial(2261));
			m = i.getItemMeta();
			m.setDisplayName(Format.color(Main.prefix + "&d&lMineAura Time"));
			List<String> l = new ArrayList<String>();
			l.add(Format.color("&7"));
			l.add(Format.color("&8 ●&7 You have &d" + Miner.getTime(p) + "&7 of miner time"));
			if (Miner.time.size() > 0) {
				l.add(Format.color("&7"));
				l.add(Format.color("&7&lTop 5 miner times:"));
				List<String> l2 = Format.highLow(Miner.time);
				for (int x = 1; x <= 5; x++) {
					if (l2.size() < x)
						break;
					String pl = l2.get(x - 1);
					l.add(Format.color("&8 &l(&d&l" + x + "&8&l)&d &l»&7 " + pl + "&d " + Miner.getTime(pl)));
				}
			}
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
			i = new ItemStack(Miner.getBlock(p).getType());
			m = i.getItemMeta();
			m.setDisplayName(Format.color("&d&lUpgrade Default Block"));
			l.clear();
			l.add(Format.color("&7"));
			l.add(Format.color("&7&lProgress:"));
			l.add(Format.color("&8 ●&7 Stage: &d" + Miner.getBlockStage(p) + "&8/&d" + Miner.blockOrder.size()));
			l.add(Format.color("&8 ●&7 Block: &d" + Miner.formatBlock(Miner.getBlock(p))));
			l.add(Format.color("&7"));
			l.add(Format.color("&8 ●&7 This affects the block type that will"));
			l.add(Format.color("&7 &7 &7 be used when &dMineAura&7 trys to mine"));
			l.add(Format.color("&7 &7 &7 air or a block which you cannot break"));
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
			i = new ItemStack(Material.EMERALD_BLOCK);
			m = i.getItemMeta();
			m.setDisplayName(Format.color("&d&lUpgrade Mining Power"));
			l.clear();
			l.add(Format.color("&7"));
			l.add(Format.color("&7&lProgress:"));
			l.add(Format.color("&8 ●&7 Stage: &d" + Miner.getPower(p) + "&8/&d" + Miner.maxPower));
			l.add(Format.color("&8 ●&7 Power: &d" + Miner.getPower(p)));
			l.add(Format.color("&7"));
			l.add(Format.color("&8 ●&7 This affects the amount of blocks that"));
			l.add(Format.color("&7 &7 &7 you will mine per second"));
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
			i = new ItemStack(Material.WOOL);
			i.setDurability((byte) 5);
			m = i.getItemMeta();
			m.setDisplayName(Format.color("&d&lEnable MineAura"));
			l.clear();
			l.add(Format.color("&7"));
			l.add(Format.color("&8 ●&7 Click to start using MineAura"));
			l.add(Format.color("&8 ●&7 You have &d" + Miner.getTime(p) + "&7 of miner time"));
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
			i = new ItemStack(Material.BOOK);
			m = i.getItemMeta();
			m.setDisplayName(Format.color("&d&lMineAura Information"));
			l.clear();
			l.add(Format.color("&7"));
			l.add(Format.color("&8 ●&7 MineAura on AfterLife will randomly break blocks"));
			l.add(Format.color("&7 &7 &7 around your current location, the amount of blocks"));
			l.add(Format.color("&7 &7 &7 broken is based on your &dMiningPower&7 upgrade value"));
			l.add(Format.color("&8 ●&7 If the block is one that you are able to physically"));
			l.add(Format.color("&7 &7 &7 mine, it will break that block, and treat it as the"));
			l.add(Format.color("&7 &7 &7 block type that was broken, otherwise the block will"));
			l.add(Format.color("&7 &7 &7 be treated as your &dDefault Block&7 upgrade value"));
			l.add(Format.color("&8 ●&7 AutoMining relies on you having MineAura Time, which"));
			l.add(Format.color("&7 &7 &7 you have &d" + Miner.getTime(p) + "&7 of"));
			l.add(Format.color("&8 ●&7 To AutoMine you must:"));
			l.add(Format.color("&8 &8 ●&7 Have automining time"));
			l.add(Format.color("&8 &8 ●&7 Be holding a pickaxe"));
			l.add(Format.color("&8 &8 ●&7 Be in the mining world"));
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
			inv.setItem(35, i);
			i = new ItemStack(Material.WOOL);
			i.setDurability((byte) 14);
			m = i.getItemMeta();
			m.setDisplayName(Format.color("&d&lDisable MineAura"));
			l.clear();
			l.add(Format.color("&7"));
			l.add(Format.color("&8 ●&7 Click to stop using MineAura"));
			l.add(Format.color("&8 ●&7 You have &d" + Miner.getTime(p) + "&7 of miner time remaining"));
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
			return inv;
		}
		if (open.get(p).equals("upgradeblock")) {
			Inventory inv = Bukkit.createInventory(null, 45, Format.color("&d&lDefaultBlock Upgrades"));
			ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE);
			i.setDurability((byte) 7);
			ItemMeta m = i.getItemMeta();
			m.setDisplayName(Format.color("&7"));
			i.setItemMeta(m);
			for (int x = 0; x < 9; x++) {
				inv.setItem(x, i);
				inv.setItem(x + 27, i);
				inv.setItem(x + 36, i);
			}
			inv.setItem(9, i);
			inv.setItem(17, i);
			inv.setItem(18, i);
			inv.setItem(26, i);
			int u = Miner.getBlockStage(p);
			for (int x = 1; x < 14; x++) {
				String mat = Miner.blockOrder.get(x - 1);
				ItemStack i2 = new ItemStack(Material.getMaterial(mat.split(";")[0]));
				i2.setDurability((byte) Byte.valueOf(mat.split(";")[1]));
				ItemMeta m2 = i2.getItemMeta();
				if (u >= x) {
					m2.setDisplayName(Format.color("&d&l" + Miner.formatBlock(i2)));
					m2.addEnchant(Enchantment.LUCK, 1, true);
					m2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				} else {
					m2.setDisplayName(Format.color("&d&m" + Miner.formatBlock(i2)));
				}
				i2.setItemMeta(m2);
				inv.addItem(i2);
			}
			i.setDurability((byte) 8);
			while (inv.firstEmpty() != -1) {
				inv.setItem(inv.firstEmpty(), i);
			}
			i = new ItemStack(Material.NETHER_STAR);
			m = i.getItemMeta();
			List<String> l = new ArrayList<String>();
			if (Miner.getBlockStage(p) >= Miner.blockOrder.size()) {
				m.setDisplayName(Format.color("&d&lDefaultBlock Upgrades Completed"));
				l.add(Format.color("&7"));
				l.add(Format.color("&7You have completed all of the &dDefaultBlock&7 upgrades"));
			} else {
				m.setDisplayName(Format.color("&d&lUpgrade "));
				l.add(Format.color("&7"));
				l.add(Format.color("&7&lProgress:"));
				l.add(Format.color("&8 ●&7 Stage: &d" + Miner.getBlockStage(p) + "&8/&d" + Miner.blockOrder.size()));
				l.add(Format.color("&8 ●&7 Block: &d" + Miner.formatBlock(Miner.getBlock(p))));
				l.add(Format.color("&7"));
				l.add(Format.color("&7&lUpgrade:"));
				l.add(Format.color("&8 ●&7 Click to upgrade your &dDefaultBlock"));
				l.add(Format.color("&8 ●&7 Doing so will require a &d&lMineAura Upgrade Chip &8(&dDefaultBlock&8)"));
			}
			m.setLore(l);
			i.setItemMeta(m);
			inv.setItem(31, i);
			return inv;
		}
		if (open.get(p).equals("upgradepower")) {
			Inventory inv = Bukkit.createInventory(null, 54, Format.color("&d&lMiningPower Upgrades"));
			ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE);
			i.setDurability((byte) 7);
			ItemMeta m = i.getItemMeta();
			m.setDisplayName(Format.color("&7"));
			i.setItemMeta(m);
			for (int x = 0; x < 9; x++) {
				inv.setItem(x, i);
				inv.setItem(x + 36, i);
				inv.setItem(x + 45, i);
			}
			inv.setItem(9, i);
			inv.setItem(17, i);
			inv.setItem(18, i);
			inv.setItem(26, i);
			inv.setItem(27, i);
			inv.setItem(35, i);
			int u = Miner.getPower(p);
			for (int x = 1; x < 21; x++) {
				ItemStack i2 = new ItemStack(Material.STAINED_GLASS_PANE);
				ItemMeta m2 = i2.getItemMeta();
				if (u >= x) {
					i2.setDurability((byte) 2);
					m2.setDisplayName(Format.color("&d&lPower: " + x));
				} else {
					i2.setDurability((byte) 10);
					m2.setDisplayName(Format.color("&d&mPower: " + x));
				}
				i2.setItemMeta(m2);
				inv.addItem(i2);
			}
			i.setDurability((byte) 8);
			while (inv.firstEmpty() != -1) {
				inv.setItem(inv.firstEmpty(), i);
			}
			i = new ItemStack(Material.NETHER_STAR);
			m = i.getItemMeta();
			List<String> l = new ArrayList<String>();
			if (Miner.getBlockStage(p) >= Miner.blockOrder.size()) {
				m.setDisplayName(Format.color("&d&lDefaultBlock Upgrades Completed"));
				l.add(Format.color("&7"));
				l.add(Format.color("&7You have completed all of the &dMiningPower&7 upgrades"));
			} else {
				m.setDisplayName(Format.color("&d&lUpgrade "));
				l.add(Format.color("&7"));
				l.add(Format.color("&7&lProgress:"));
				l.add(Format.color("&8 ●&7 Power: &d" + Miner.getBlockStage(p) + "&8/&d" + Miner.blockOrder.size()));
				l.add(Format.color("&7"));
				l.add(Format.color("&7&lUpgrade:"));
				l.add(Format.color("&8 ●&7 Click to upgrade your &dMiningPower"));
				l.add(Format.color("&8 ●&7 Doing so will require a &d&lMineAura Upgrade Chip &8(&dMiningPower&8)"));
			}
			m.setLore(l);
			i.setItemMeta(m);
			inv.setItem(40, i);
			return inv;
		}

		return null;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (open.containsKey(p)) {
			ItemStack ci = e.getCurrentItem();
			if (open.get(p).equals("home")) {
				if (e.getRawSlot() < 36) {
					e.setCancelled(true);
					if (ci != null) {
						if (ci.hasItemMeta()) {
							ItemMeta cm = ci.getItemMeta();
							if (cm.hasDisplayName()
									&& cm.getDisplayName().equals(Format.color("&d&lUpgrade Default Block"))) {
								open.put(p, "upgradeblock");
								p.openInventory(menu(p));
								open.put(p, "upgradeblock");
							}
							if (cm.hasDisplayName()
									&& cm.getDisplayName().equals(Format.color("&d&lUpgrade Mining Power"))) {
								open.put(p, "upgradepower");
								p.openInventory(menu(p));
								open.put(p, "upgradepower");
							}
							if (cm.hasDisplayName()
									&& cm.getDisplayName().equals(Format.color("&d&lEnable MineAura"))) {
								Miner.enable(p);
								p.closeInventory();
							}
							if (cm.hasDisplayName()
									&& cm.getDisplayName().equals(Format.color("&d&lDisable MineAura"))) {
								Miner.disable(p);
								p.closeInventory();
							}
						}
					}
				}
				return;
			}
			if (open.get(p).equals("upgradeblock")) {
				if (e.getRawSlot() < 45) {
					e.setCancelled(true);
					if (e.getRawSlot() == 31) {
						if (Miner.getBlockStage(p) >= Miner.blockOrder.size()) {
							Message.player(Main.prefix + "Sorry, but your &dDefaultBlock&7 is already maxed", p);
							return;
						}
						boolean b = false;
						int x = -1;
						for (ItemStack is : p.getInventory().getContents()) {
							x++;
							if (is != null) {
								if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()
										&& is.getItemMeta().hasDisplayName()) {
									ItemMeta im = is.getItemMeta();
									if (im.getDisplayName().equals(Format.color("&d&lMineAura Upgrade Chip"))
											&& im.getLore().get(1).equals(Format.color("&8 ●&7 Redeem in &d/Miner"))
											&& im.getLore().get(2)
													.equals(Format.color("&8 ●&7 Upgrades your &dDefaultBlock"))) {
										b = true;
										break;
									}
								}
							}
						}
						if (!b) {
							Message.player(Main.prefix
									+ "Sorry, but you don't have any &d&lMineAura Upgrade Chips &8(&dDefaultBlock&8)",
									p);
							return;
						}
						if (p.getInventory().getItem(x).getAmount() > 1) {
							p.getInventory().getItem(x).setAmount(p.getInventory().getItem(x).getAmount() - 1);
						} else {
							p.getInventory().setItem(x, new ItemStack(Material.AIR));
						}
						p.updateInventory();
						Miner.upgradeBlock(p);
						p.setItemOnCursor(null);
						p.openInventory(menu(p));
						open.put(p, "upgradeblock");
						Message.player(Main.prefix + "You upgraded your &dDefaultBlock&7 to &d"
								+ Miner.formatBlock(Miner.getBlock(p)) + "&8 (&d" + Miner.getBlockStage(p) + "&8/&d"
								+ Miner.blockOrder.size() + "&8)", p);
					}
				}
				return;
			}
			if (open.get(p).equals("upgradepower")) {
				if (e.getRawSlot() < 54) {
					e.setCancelled(true);
					if (e.getRawSlot() == 40) {
						if (Miner.getPower(p) >= 20) {
							Message.player(Main.prefix + "Sorry, but your &dMiningPower&7 is already maxed", p);
							return;
						}
						boolean b = false;
						int x = -1;
						for (ItemStack is : p.getInventory().getContents()) {
							x++;
							if (is != null) {
								if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()
										&& is.getItemMeta().hasDisplayName()) {
									ItemMeta im = is.getItemMeta();
									if (im.getDisplayName().equals(Format.color("&d&lMineAura Upgrade Chip"))
											&& im.getLore().get(1).equals(Format.color("&8 ●&7 Redeem in &d/Miner"))
											&& im.getLore().get(2)
													.equals(Format.color("&8 ●&7 Upgrades your &dMiningPower"))) {
										b = true;
										break;
									}
								}
							}
						}
						if (!b) {
							Message.player(Main.prefix
									+ "Sorry, but you don't have any &d&lMineAura Upgrade Chips &8(&dMiningPower&8)",
									p);
							return;
						}
						if (p.getInventory().getItem(x).getAmount() > 1) {
							p.getInventory().getItem(x).setAmount(p.getInventory().getItem(x).getAmount() - 1);
						} else {
							p.getInventory().setItem(x, new ItemStack(Material.AIR));
						}
						p.updateInventory();
						Miner.upgradePower(p);
						p.setItemOnCursor(null);
						p.openInventory(menu(p));
						open.put(p, "upgradepower");
						Message.player(Main.prefix + "You upgraded your &dMiningPower&7 to &d"
								+ Miner.formatBlock(Miner.getBlock(p)) + "&8 (&d" + Miner.getBlockStage(p) + "&8/&d"
								+ Miner.blockOrder.size() + "&8)", p);
					}
				}
				return;
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (open.containsKey(p))
			open.remove(p);
	}
}
