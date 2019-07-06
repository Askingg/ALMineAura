package me.syn.alminer.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.syn.alautosell.main.ALAutoSell;
import me.syn.alblocks.main.ALBlocks;
import me.syn.alenchant.main.ALEnchantAPI;
import me.syn.alminer.utils.Format;
import me.syn.alminer.utils.Message;

public class Miner {

	public static List<String> blockOrder = Arrays.asList("COBBLESTONE;0", "STONE;0", "STAINED_CLAY;9",
			"STAINED_CLAY;0", "NETHERRACK;0", "STAINED_CLAY;5", "STAINED_CLAY;4", "ENDER_STONE;0", "STAINED_CLAY;2",
			"OBSIDIAN;0", "STAINED_CLAY;3", "STAINED_CLAY;14", "QUARTZ_BLOCK;0");
	public static HashMap<String, Integer> time = new HashMap<String, Integer>(); // PlayerName, Time(Seconds)
	public static HashMap<String, Integer> power = new HashMap<String, Integer>(); // PlayerName, Power
	public static HashMap<String, Integer> block = new HashMap<String, Integer>(); // PlayerName, DefaultBlock
	public static List<Player> mining = new ArrayList<Player>();

	// Main

	public static void enable(Player p) {
		if (mining.contains(p)) {
			Message.player(Main.prefix + "Sorry, but you're already automining", p);
			return;
		}
		if (p.isOp())
			return;
		if (getSeconds(p) < 1) {
			Message.player(
					Main.prefix + "Sorry, but you don't have any &dMineAura Time&7, obtain this via voting: &d/ALVote",
					p);
			return;
		}
		if (p.getInventory().getItemInHand() == null
				|| !p.getInventory().getItemInHand().getType().toString().contains("PICKAXE")) {
			Message.player(Main.prefix + "Sorry, but you must be holding a pickaxe to automine", p);
			return;
		}
		if (!p.getLocation().getWorld().getName().equals("world")) {
			Message.player(Main.prefix + "Sorry, but you cannot mine in this world", p);
			return;
		}
		mining.add(p);
		Message.player(Main.prefix + "&dMineAura&7 was &aenabled", p);
	}

	public static void disable(Player p) {
		if (!mining.contains(p)) {
			Message.player(Main.prefix + "Sorry, but you're not automining currently", p);
			return;
		}
		mining.remove(p);
		Message.player(Main.prefix + "&dMineAura&7 was &cdisabled", p);
	}

	public static void timer(Plugin plug) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plug, new Runnable() {
			@Override
			public void run() {
				if (mining.size() > 0) {
					for (Player p : mining) {
						if (p.isOp())
							return;
						if (getSeconds(p) < 1) {
							Message.player(Main.prefix
									+ "Sorry, but you don't have any &dMineAura Time&7, obtain this via voting: &d/ALVote",
									p);
							disable(p);
							return;
						}
						if (p.getInventory().getItemInHand() == null
								|| !p.getInventory().getItemInHand().getType().toString().contains("PICKAXE")) {
							Message.player(Main.prefix + "Sorry, but you must be holding a pickaxe to automine", p);
							return;
						}
						Location loc = p.getLocation();
						World w = loc.getWorld();
						if (!w.getName().equals("world")) {
							Message.player(Main.prefix + "Sorry, but you cannot mine in this world", p);
							return;
						}
						int pwr = getPower(p);
						Integer r = 2;
						Integer X = loc.getBlockX();
						Integer Y = loc.getBlockY();
						Integer Z = loc.getBlockZ();
						Integer minX = X - r;
						Integer maxX = X + r + 1;
						Integer minY = Y - r;
						Integer maxY = Y + r + 1;
						Integer minZ = Z - r;
						Integer maxZ = Z + r + 1;
						double x = minX;
						while (x < maxX) {
							double y = minY;
							while (y < maxY) {
								double z = minZ;
								while (z < maxZ) {
									Random ra = new Random();
									double xx = ra.nextDouble();
									if (xx < (0.01 * pwr)) {
										Location l = new Location(w, x, y, z);
										ItemStack i = new ItemStack(Material.AIR);
										boolean can = false;
										if (WorldGuardPlugin.inst().canBuild(p, l)) {
											if (l.getBlock().getDrops().size() > 0) {
												i = (ItemStack) l.getBlock().getDrops().toArray()[0];
											}
											can = true;
										} else {
											i = getBlock(p);
											can = false;
										}
										ALEnchantAPI.blockBreak(p, l);
										ALAutoSell.blockBreak(p, l, i.getType(), i.getDurability());
										ALBlocks.blockBreak(p, l);
										if (can)
											l.getBlock().setType(Material.AIR);
									}
									z += 1.0D;
								}
								y += 1.0D;
							}
							x += 1.0D;
						}
						reduceTime(p, 1);
					}
				}
			}
		}, 20, 20);
	}

	// Time

	public static int getSeconds(Player p) {
		int t = 0;
		if (time.containsKey(p.getName()))
			t = time.get(p.getName());
		return t;
	}

	public static int getSeconds(String p) {
		int t = 0;
		if (time.containsKey(p))
			t = time.get(p);
		return t;
	}

	public static String getTime(Player p) {
		return Format.time(getSeconds(p));
	}

	public static String getTime(String p) {
		return Format.time(getSeconds(p));
	}

	public static void addTime(Player p, int secs) {
		time.put(p.getName(), time.get(p.getName()) + secs);
	}

	public static void reduceTime(Player p, int secs) {
		time.put(p.getName(), time.get(p.getName()) - secs);
	}

	// UpgradeBlock

	public static ItemStack blockChip(int a) {
		ItemStack i = new ItemStack(Material.NETHER_STAR, a);
		ItemMeta m = i.getItemMeta();
		List<String> l = new ArrayList<String>();
		m.setDisplayName(Format.color("&d&lMineAura Upgrade Chip"));
		l.add(Format.color("&7"));
		l.add(Format.color("&8 ●&7 Redeem in &d/Miner"));
		l.add(Format.color("&8 ●&7 Upgrades your &dDefaultBlock"));
		m.setLore(l);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack getBlock(Player p) {
		ItemStack i = new ItemStack(Material.getMaterial(blockOrder.get(0).split(";")[0]));
		i.setDurability((byte) Byte.valueOf(blockOrder.get(0).split(";")[1]));
		if (block.containsKey(p.getName())) {
			i.setType(Material.getMaterial(blockOrder.get(block.get(p.getName()) - 1).split(";")[0]));
			i.setDurability((byte) Byte.valueOf(blockOrder.get(block.get(p.getName()) - 1).split(";")[1]));
		}
		return i;
	}

	public static int getBlockStage(Player p) {
		int x = 1;
		if (block.containsKey(p.getName()))
			x = block.get(p.getName());
		return x;
	}

	public static String formatBlock(ItemStack i) {
		String s = i.getType() + ";" + i.getDurability();
		if (s.equals("COBBLESTONE;0"))
			return "Cobblestone";
		if (s.equals("STONE;0"))
			return "Stone";
		if (s.equals("STAINED_CLAY;9"))
			return "Cyan Clay";
		if (s.equals("STAINED_CLAY;0"))
			return "White Clay";
		if (s.equals("NETHERRACK;0"))
			return "Netherrack";
		if (s.equals("STAINED_CLAY;5"))
			return "Lime Clay";
		if (s.equals("STAINED_CLAY;4"))
			return "Yellow Clay";
		if (s.equals("ENDER_STONE;0"))
			return "End Stone";
		if (s.equals("STAINED_CLAY;2"))
			return "Magenta Clay";
		if (s.equals("OBSIDIAN;0"))
			return "Obsidian";
		if (s.equals("STAINED_CLAY;3"))
			return "Light Blue Clay";
		if (s.equals("STAINED_CLAY;14"))
			return "Red Clay";
		if (s.equals("QUARTZ_BLOCK;0"))
			return "Quartz Block";
		return null;
	}

	public static void upgradeBlock(Player p) {
		if (block.containsKey(p.getName())) {
			block.put(p.getName(), block.get(p.getName()) + 1);
		} else {
			block.put(p.getName(), 2);
		}
	}

	// Upgrade Power

	public static int maxPower = 20;

	public static ItemStack powerChip(int a) {
		ItemStack i = new ItemStack(Material.NETHER_STAR, a);
		ItemMeta m = i.getItemMeta();
		List<String> l = new ArrayList<String>();
		m.setDisplayName(Format.color("&d&lMineAura Upgrade Chip"));
		l.add(Format.color("&7"));
		l.add(Format.color("&8 ●&7 Redeem in &d/Miner"));
		l.add(Format.color("&8 ●&7 Upgrades your &dMiningPower"));
		m.setLore(l);
		i.setItemMeta(m);
		return i;
	}

	public static void upgradePower(Player p) {
		if (power.containsKey(p.getName())) {
			power.put(p.getName(), power.get(p.getName()) + 1);
		} else {
			power.put(p.getName(), 2);
		}
	}

	public static int getPower(Player p) {
		int x = 1;
		if (power.containsKey(p.getName()))
			x = power.get(p.getName());
		return x;
	}
}
