package dev.founntain.founnplugin.recipes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class TntPickaxeRecipe extends BaseRecipe{
	private Plugin plugin;
	
	public TntPickaxeRecipe(Plugin plugin) {
		Bukkit.getConsoleSender().sendMessage(this.prefix + "registering " + this.getClass().getSimpleName());
		this.plugin = plugin;
		this.createRecipe();
	}
	
	public void createRecipe() {
		ItemStack tntPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
		ItemMeta meta = tntPickaxe.getItemMeta();
		
		meta.setDisplayName(ChatColor.GOLD + "TNT Pickaxe");
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Use it on a block and wait " + ChatColor.GOLD + "5" + ChatColor.GRAY + " seconds ");
		lore.add(ChatColor.GRAY + "Perfect for finding " + ChatColor.GOLD + "Ancient Debris ");
		lore.add(ChatColor.RED + "Use with caution and distance!");
		meta.setLore(lore);
		
		tntPickaxe.setItemMeta(meta);
		
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this.plugin, "founnplugin_TNT_PICKAXE"), tntPickaxe);
		
		recipe.shape("TTT", "XGX", "XGX");
		
		recipe.setIngredient('T', Material.TNT);
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('X', Material.AIR);
		
		plugin.getServer().addRecipe(recipe);
	}
}
