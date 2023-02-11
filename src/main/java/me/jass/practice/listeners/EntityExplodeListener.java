package me.jass.practice.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.jass.practice.PracticeAPI;
import me.jass.practice.duels.Duel;

public class EntityExplodeListener implements Listener {
	@EventHandler
	public void onEntityExplode(final EntityExplodeEvent event) {
		final Duel duel = PracticeAPI.INSTANCE.getDuelManager().getExplosive(event.getLocation().getBlock().getLocation());
		if (duel == null || !duel.isActive()) {
			if (!PracticeAPI.INSTANCE.getConfigManager().isUnsafeExplosives()) {
				event.setCancelled(true);
			}

			return;
		}

		for (final Block block : event.blockList()) {
			duel.addWorldDamage(block.getState());
		}
	}
}