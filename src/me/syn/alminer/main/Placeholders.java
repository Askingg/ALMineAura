package me.syn.alminer.main;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholders extends PlaceholderExpansion {

	public String getIdentifier() {
		return "almisc";
	}

	public String getPlugin() {
		return null;
	}

	public String getAuthor() {
		return "Synysterrr";
	}

	public String getVersion() {
		return "1.0";
	}

	public String onPlaceholderRequest(Player p, String identifier) {

		// Placeholder: %almisc_ram_max%
		if (identifier.equalsIgnoreCase("ram_max")) {
			return Runtime.getRuntime().maxMemory() / 1048576L + "mb";
		}

		// Placeholder: %almisc_ram_allocated%
		if (identifier.equalsIgnoreCase("ram_allocated")) {
			return Runtime.getRuntime().totalMemory() / 1048576L + "mb";
		}

		// Placeholder: %almisc_ram_using%
		if (identifier.equalsIgnoreCase("ram_using")) {
			return (Runtime.getRuntime().totalMemory() / 1048576L) - (Runtime.getRuntime().freeMemory() / 1048576L)
					+ "mb";
		}

		// Placeholder: %almisc_ram_free%
		if (identifier.equalsIgnoreCase("ram_free")) {
			return Runtime.getRuntime().freeMemory() / 1048576L + "mb";
		}

		return null;
	}
}