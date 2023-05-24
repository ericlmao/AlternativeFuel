package com.bonksmp.alternativefuel;

import com.bonksmp.alternativefuel.listener.AlternateFuelBlockListener;
import com.bonksmp.alternativefuel.listener.AlternateFuelListener;
import org.bukkit.*;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AlternativeFuel extends JavaPlugin {

    private static AlternativeFuel instance;

    private NamespacedKey shard;
    private NamespacedKey block;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.shard = new NamespacedKey(this, "peat-shard");
        this.block = new NamespacedKey(this, "peat-block");

        registerRecipes();

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new AlternateFuelBlockListener(this), this);
        manager.registerEvents(new AlternateFuelListener(this), this);

    }

    private void registerRecipes() {
        ItemStack peatShard = new ItemStack(Material.COAL, 1);
        ItemMeta peatShardMeta = peatShard.getItemMeta();

        if (peatShardMeta == null) return;

        peatShardMeta.setDisplayName(ChatColor.GREEN + "Peat Shard");
        peatShardMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        peatShardMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer shardContainer = peatShardMeta.getPersistentDataContainer();
        shardContainer.set(shard, PersistentDataType.BYTE, (byte) 1);

        peatShard.setItemMeta(peatShardMeta);

        ItemStack peatBlock = new ItemStack(Material.COAL_BLOCK, 1);
        ItemMeta peatBlockMeta = peatBlock.getItemMeta();

        if (peatBlockMeta == null) return;

        peatBlockMeta.setDisplayName(ChatColor.GREEN + "Peat Block");
        peatBlockMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        peatBlockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer blockContainer = peatBlockMeta.getPersistentDataContainer();
        blockContainer.set(block, PersistentDataType.BYTE, (byte) 1);

        peatBlock.setItemMeta(peatBlockMeta);

        Material[] materials = new Material[] {
                // Seeds
                Material.BEETROOT_SEEDS,
                Material.MELON_SEEDS,
                Material.PUMPKIN_SEEDS,
                Material.WHEAT_SEEDS,

                // Leaves
                Material.OAK_LEAVES,
                Material.SPRUCE_LEAVES,
                Material.BIRCH_LEAVES,
                Material.JUNGLE_LEAVES,
                Material.ACACIA_LEAVES,
                Material.CHERRY_LEAVES,
                Material.DARK_OAK_LEAVES,
                Material.MANGROVE_LEAVES,
                Material.AZALEA_LEAVES,
                Material.FLOWERING_AZALEA_LEAVES,
        };

        for (Material material : materials) {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, material.name() + "_peat_recipe"), peatShard);
            recipe.shape("XXX", "XXX", "XXX");
            recipe.setIngredient('X', material);

            getServer().addRecipe(recipe);
        }

        ShapedRecipe recipe = new ShapedRecipe(block, peatBlock);
        recipe.shape("SSS", "SSS", "SSS");
        recipe.setIngredient('S', new RecipeChoice.ExactChoice(peatShard));

        getServer().addRecipe(recipe);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AlternativeFuel get() {
        return instance;
    }

    public NamespacedKey getBlockKey() {
        return block;
    }

    public NamespacedKey getShardKey() {
        return shard;
    }
}
