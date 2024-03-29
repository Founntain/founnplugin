package dev.founntain.founnplugin.events;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import dev.founntain.founnplugin.classes.Pair;

public class OnCraftItemEvent implements Listener{
	
	public OnCraftItemEvent() { }

	@EventHandler
	public void onCraftItemEvent(CraftItemEvent e) {
		this.tryEnchantPlus(e);
	}
	
	private void tryEnchantPlus(CraftItemEvent e) throws ClassCastException{
		Recipe recipe = e.getRecipe();
		
		if(recipe == null)
			return;
		
		ItemStack[] contents =  e.getInventory().getContents();
		
		Pair<Enchantment, Integer> ench1 = null;
		ItemStack item1 = null;
		Pair<Enchantment, Integer> ench2 = null;
		ItemStack item2 = null;
		
		for(ItemStack stack : contents) {
			if(stack.getType() == Material.AIR)
				continue;
			
			int storedCount = stack.getEnchantments().keySet().size();
			int count = stack.getItemMeta().getEnchants().keySet().size();
			
			if(storedCount == 0 && count == 0)
				continue;
			
			Map<Enchantment, Integer> enchs = null;
			
			if(storedCount > 0)
				enchs = stack.getEnchantments();
			else if(count > 0)
				enchs = stack.getItemMeta().getEnchants();
			
			for(Map.Entry<Enchantment, Integer> entry : enchs.entrySet()) {
				if(ench1 == null) {
					ench1 = new Pair<Enchantment, Integer>(entry.getKey(), entry.getValue());
					item1 = stack;
				}else {
					ench2 = new Pair<Enchantment, Integer>(entry.getKey(), entry.getValue());
					item2 = stack;
				}
			}
		}
		
		if(ench1 == null || ench2 == null)
			return;
		
		if(ench1.GetItem1() != ench2.GetItem1())
			e.setCancelled(true);
		
		if(ench1.GetItem2() != ench2.GetItem2())
			e.setCancelled(true);
		
		ItemStack result = new ItemStack(item1.getType());
		ItemMeta meta = result.getItemMeta();
		
		meta.addEnchant(ench1.GetItem1(), ench1.GetItem2() + 1, true);
		
		result.setItemMeta(meta);
		
		e.getInventory().setResult(result);
	}
}
