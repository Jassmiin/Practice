package me.jass.practice.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jass.practice.datatypes.GUI;
import me.jass.practice.utils.Text;

public class SettingsGUI extends DuelGUI {
	public SettingsGUI(final Player player) {
		final Inventory gui = Bukkit.createInventory(null, getSize(GUI.SETTINGS, null), centerTitle(Text.color("&8Settings")));
		setGUI(gui, player, GUI.SETTINGS);
	}

	public void loadDuelRequests() {
		final String duelRequests;

		if (menuManager.hasDuelRequests(player)) {
			inventory.setItem(2, createItem(Material.LIME_STAINED_GLASS_PANE, Text.color("#limeEnabled"), 1));
			inventory.setItem(20, createItem(Material.LIME_STAINED_GLASS_PANE, Text.color("#limeEnabled"), 1));

			duelRequests = Text.color("#limeYes");
		} else {
			inventory.setItem(2, createItem(Material.RED_STAINED_GLASS_PANE, Text.color("#redDisabled"), 1));
			inventory.setItem(20, createItem(Material.RED_STAINED_GLASS_PANE, Text.color("#redDisabled"), 1));

			duelRequests = Text.color("#redNo");
		}

		inventory.setItem(11, createItem(Material.BELL, Text.color("#yellowDuel Requests"), 1, Text.color("&7Enabled: ") + duelRequests));

	}

	public void loadDuelChat() {
		final String duelChat;

		if (menuManager.hasDuelChat(player)) {
			inventory.setItem(6, createItem(Material.LIME_STAINED_GLASS_PANE, Text.color("#limeEnabled"), 1));
			inventory.setItem(24, createItem(Material.LIME_STAINED_GLASS_PANE, Text.color("#limeEnabled"), 1));

			duelChat = Text.color("#limeYes");
		} else {
			inventory.setItem(6, createItem(Material.RED_STAINED_GLASS_PANE, Text.color("#redDisabled"), 1));
			inventory.setItem(24, createItem(Material.RED_STAINED_GLASS_PANE, Text.color("#redDisabled"), 1));

			duelChat = Text.color("#redNo");
		}

		inventory.setItem(15, createItem(Material.PAPER, Text.color("&fDuel Chat"), 1, Text.color("&7Enabled: ") + duelChat));
	}

	public void toggle(final Material material) {
		if (material == Material.BELL) {
			menuManager.setDuelRequests(player, !menuManager.hasDuelRequests(player));
			loadDuelRequests();
		}

		else if (material == Material.PAPER) {
			menuManager.setDuelChat(player, !menuManager.hasDuelChat(player));
			loadDuelChat();
		}
	}

	@Override
	public void loadGUI() {
		fillBorders();

		loadDuelRequests();
		loadDuelChat();

		inventory.setItem(13, createItem(Material.BEACON, Text.color("#aquaPing Range"), 1));

		inventory.setItem(4, arrowUp());
		inventory.setItem(22, arrowDown());

		hideFlags();
	}

	@Override
	public void clicked(final ItemStack item, final int slot, final ItemStack with) {
		if (isBorder(item)) {
			return;
		}

		final String name = item.getItemMeta().getDisplayName();

		if (name.contains("Duel Requests") || name.contains("Duel Chat")) {
			toggle(item.getType());
			ding();
		}

		else if (name.contains("More") || name.contains("Less")) {
			int amount = 0;
			if (name.contains("More")) {
				amount = inventory.getItem(slot + 9).getAmount();
				if (amount == 64) {
					return;
				}

				inventory.getItem(slot + 9).setAmount(amount + 1);
			}

			else if (name.contains("Less")) {
				amount = inventory.getItem(slot - 9).getAmount();
				if (amount == 1) {
					return;
				}

				inventory.getItem(slot - 9).setAmount(amount - 1);
			}

			if (amount == 1) {
				amount = -1;
			}

			menuManager.setPingRange(player, amount);
			ding();
		}
	}

	@Override
	public boolean canMove(final ItemStack item) {
		return false;
	}
}
