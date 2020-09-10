package me.satisdev.justanotherapi.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class LobbyInventory {

	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;

	LobbyInventory() {
		helmet = new ItemStack(Material.AIR);
		chestplate = new ItemStack(Material.AIR);
		leggings = new ItemStack(Material.AIR);
		boots = new ItemStack(Material.AIR);
	}

	public void setInventory(Player player) {
		PlayerInventory playerInventory = player.getInventory();

		player.setHealth(20.0);
		player.setFoodLevel(20);

		playerInventory.clear();
		playerInventory.setArmorContents(new ItemStack[] {getBoots(), getLeggings(), getChestplate(), getHelmet()});

	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public void setLeggings(ItemStack leggings) {
		this.leggings = leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}
}
