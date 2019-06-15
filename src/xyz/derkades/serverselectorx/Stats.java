package xyz.derkades.serverselectorx;

import java.util.HashMap;
import java.util.Map;

import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import xyz.derkades.serverselectorx.placeholders.PlaceholdersEnabled;

public class Stats {

	static void initialize() {
		final Metrics metrics = new Metrics(Main.getPlugin());

		final Map<String, FileConfiguration> menuConfigs = Main.getConfigurationManager().getAllMenus();

		metrics.addCustomChart(new Metrics.SimplePie("placeholderapi", () -> {
			if (Main.PLACEHOLDER_API instanceof PlaceholdersEnabled) {
				return "yes";
			} else {
				return "no";
			}
		}));

		metrics.addCustomChart(new Metrics.SimplePie("number_of_selectors", () -> {
			return menuConfigs.entrySet().size() + "";
		}));

		metrics.addCustomChart(new Metrics.AdvancedPie("selector_item", () -> {
			final Map<String, Integer> map = new HashMap<>();

			for (final Map.Entry<String, FileConfiguration> menuConfigEntry : menuConfigs.entrySet()) {
				final FileConfiguration config = menuConfigEntry.getValue();

				String materialString;

				if (!config.getBoolean("item.enabled")) {
					materialString = "disabled";
				} else {
					final Material material = Material.getMaterial(config.getString("item.material"));
					if (material == null) continue; //Do not count invalid items
					materialString = material.toString();
				}

				if (map.containsKey(materialString)) {
					map.put(materialString, map.get(materialString + 1));
				} else {
					map.put(materialString, 1);
				}
			}

			return map;
		}));

		metrics.addCustomChart(new Metrics.AdvancedPie("type", () -> {
			final Map<String, Integer> map = new HashMap<>();

			for (final Map.Entry<String, FileConfiguration> menuConfigEntry : menuConfigs.entrySet()) {
				final FileConfiguration config = menuConfigEntry.getValue();

				for (final String slot : config.getConfigurationSection("menu").getKeys(false)) {
					final String action = config.getString("menu." + slot + ".action").split(":")[0].toLowerCase();
					if (map.containsKey(action)) {
						map.put(action, map.get(action) + 1);
					} else {
						map.put(action, 1);
					}
				}
			}

			return map;
		}));

		metrics.addCustomChart(new Metrics.SimplePie("ping_api", () -> {
			return "Premium";
		}));

		metrics.addCustomChart(new Metrics.SimplePie("updater", () -> {
			return "None";
		}));

		metrics.addCustomChart(new Metrics.AdvancedPie("server_pinging", () -> {
			final Map<String, Integer> map = new HashMap<>();

			for (final Map.Entry<String, FileConfiguration> menuConfigEntry : menuConfigs.entrySet()) {
				final FileConfiguration config = menuConfigEntry.getValue();

				for (final String slot : config.getConfigurationSection("menu").getKeys(false)) {
					final String action = config.getString("menu." + slot + ".action", "none").split(":")[0].toLowerCase();
					if (action.equalsIgnoreCase("srv")) {
						if (map.containsKey("Premium")) {
							map.put("Premium", map.get("Enabled") + 1);
						} else {
							map.put("Premium", 1);
						}
					}
				}
			}

			return map;
		}));

//		metrics.addCustomChart(new Metrics.SimplePie("permissions", () -> {
//			if (Main.getConfigurationManager().getGlobalConfig().getBoolean("permissions-enabled", false)) {
//				return "Enabled";
//			} else {
//				return "Disabled";
//			}
//		}));

		metrics.addCustomChart(new Metrics.SimplePie("item_drop", () -> {
			if (Main.getConfigurationManager().getGlobalConfig().getBoolean("cancel-item-drop", false)) {
				return "Cancel";
			} else {
				return "Allow";
			}
		}));

		metrics.addCustomChart(new Metrics.SimplePie("item_move", () -> {
			if (Main.getConfigurationManager().getGlobalConfig().getBoolean("cancel-item-move", false)) {
				return "Cancel";
			} else {
				return "Allow";
			}
		}));

		metrics.addCustomChart(new Metrics.AdvancedPie("menu_item_slot", () -> {
			final Map<String, Integer> map = new HashMap<>();

			for (final Map.Entry<String, FileConfiguration> menuConfigEntry : menuConfigs.entrySet()) {
				final FileConfiguration config = menuConfigEntry.getValue();

				if (config.getBoolean("item.on-join.enabled")) {
					final int slot = config.getInt("item.on-join.inv-slot");
					if (slot < 0) {
						if (map.containsKey("Auto")) {
							map.put("Auto", map.get("Auto") + 1);
						} else {
							map.put("Auto", 1);
						}
					} else {
						if (map.containsKey(slot + "")) {
							map.put(slot + "", map.get(slot + "") + 1);
						} else {
							map.put(slot + "", 1);
						}
					}
				} else {
					if (map.containsKey("Disabled")) {
						map.put("Disabled", map.get("Disabled") + 1);
					} else {
						map.put("Disabled", 1);
					}
				}
			}

			return map;
		}));

		metrics.addCustomChart(new Metrics.AdvancedPie("rows", () -> {
			final Map<String, Integer> map = new HashMap<>();

			for (final Map.Entry<String, FileConfiguration> menuConfigEntry : Main.getConfigurationManager().getAllMenus().entrySet()) {
				final FileConfiguration config = menuConfigEntry.getValue();

				final int rows = config.getInt("rows", 6);
				if (map.containsKey(rows + "")) {
					map.put(rows + "", map.get(rows + "") + 1);
				} else {
					map.put(rows + "", 1);
				}
			}

			return map;
		}));

		metrics.addCustomChart(new Metrics.SimplePie("config_sync", () -> {
			if (Main.getConfigurationManager().getSSXConfig().getBoolean("config-sync.enabled", false)) {
				return "Enabled";
			} else {
				return "Disabled";
			}
		}));
	}

}
