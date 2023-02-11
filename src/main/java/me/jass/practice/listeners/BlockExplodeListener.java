package me.jass.practice.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

import me.jass.practice.PracticeAPI;
import me.jass.practice.duels.Duel;
import me.jass.practice.utils.Text;

public class BlockExplodeListener implements Listener {
	@EventHandler
	public void onBlockExplode(final BlockExplodeEvent event) {
		final Duel duel = PracticeAPI.INSTANCE.getDuelManager().getExplosive(event.getBlock().getLocation());

		if (duel == null || !duel.isActive()) {
			if (!PracticeAPI.INSTANCE.getConfigManager().isUnsafeExplosives()) {
				event.setCancelled(true);
			}
			return;
		}

		Text.alert("add world damage");

		for (final Block block : event.blockList()) {
			duel.addWorldDamage(block.getState());
		}
	}
}
