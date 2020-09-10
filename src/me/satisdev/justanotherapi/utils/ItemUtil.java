package me.satisdev.justanotherapi.utils;

import me.satisdev.justanotherapi.JustAnotherAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtil extends ItemStack {

	private final JustAnotherAPI api = JustAnotherAPI.getAPI();

	private final Material material;
	private final String name;
	private final int amount;
	private final boolean unbreakable;
	private final String[] lore;

	public ItemUtil(Material material) {
		this.material = material;
		amount = 1;
		name = material.getData().getName();
		unbreakable = false;
		lore = new String[] {};

		create();
	}

	public ItemUtil(Material material, boolean unbreakable) {
		this.material = material;
		amount = 1;
		name = material.getData().getName();
		this.unbreakable = unbreakable;
		lore = new String[] {};

		create();
	}

	public ItemUtil(Material material, String name) {
		this.material = material;
		amount = 1;
		this.name = name;
		unbreakable = false;
		lore = new String[] {};

		create();
	}

	public ItemUtil(Material material, String name, boolean unbreakable) {
		this.material = material;
		amount = 1;
		this.name = name;
		this.unbreakable = unbreakable;
		lore = new String[] {};

		create();
	}

	public ItemUtil(Material material, int amount, String name) {
		this.material = material;
		this.amount = amount;
		this.name = name;
		unbreakable = false;
		lore = new String[]{};

		create();
	}

	public ItemUtil(Material material, int amount, String name, boolean unbreakable) {
		this.material = material;
		this.amount = amount;
		this.name = name;
		this.unbreakable = unbreakable;
		lore = new String[]{};

		create();
	}

	public ItemUtil(Material material, String name, String[] lore) {
		this.material = material;
		amount = 1;
		this.name = name;
		unbreakable = false;
		this.lore = lore;

		create();
	}

	public ItemUtil(Material material, String name, boolean unbreakable, String[] lore) {
		this.material = material;
		amount = 1;
		this.name = name;
		this.unbreakable = unbreakable;
		this.lore = lore;

		create();
	}

	public ItemUtil(Material material, int amount, String name, String[] lore) {
		this.material = material;
		this.amount = amount;
		this.name = name;
		unbreakable = false;
		this.lore = lore;

		create();
	}

	public ItemUtil(Material material, int amount, String name, boolean unbreakable, String[] lore) {
		this.material = material;
		this.amount = amount;
		this.name = name;
		this.unbreakable = unbreakable;
		this.lore = lore;

		create();
	}

	private void create() {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(api.color(name));
		itemMeta.setLore(Arrays.asList(lore));
		itemMeta.spigot().setUnbreakable(unbreakable);
		item.setItemMeta(itemMeta);
	}
}