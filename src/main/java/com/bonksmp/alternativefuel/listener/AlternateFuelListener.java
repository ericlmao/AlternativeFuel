package com.bonksmp.alternativefuel.listener;

import com.bonksmp.alternativefuel.AlternativeFuel;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AlternateFuelListener implements Listener {

    private final NamespacedKey key;

    public AlternateFuelListener(AlternativeFuel plugin) {
        this.key = plugin.getBlockKey();
    }


    @EventHandler
    public void onSmelt(FurnaceStartSmeltEvent event) {
        Block block = event.getBlock();
        if (!(block.getState() instanceof Furnace furnace)) return;

        FurnaceInventory inv = furnace.getSnapshotInventory();
        ItemStack fuel = inv.getFuel();
        if (fuel == null) return;

        ItemMeta meta = fuel.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BYTE)) return;

        event.setTotalCookTime(event.getTotalCookTime() / 2);
    }
}
