package com.bonksmp.alternativefuel.listener;

import com.bonksmp.alternativefuel.AlternativeFuel;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AlternateFuelBlockListener implements Listener {

    private final NamespacedKey key;

    public AlternateFuelBlockListener(AlternativeFuel plugin) {
        this.key = plugin.getBlockKey();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BYTE)) return;

        event.setBuild(false);
        event.setCancelled(true);
    }
}
