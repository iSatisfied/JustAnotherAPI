package me.satisdev.justanotherapi.inventories;

public class Inventories {

	private final LobbyInventory lobbyInventory;

	public Inventories() {
		lobbyInventory = new LobbyInventory();
	}

	public LobbyInventory getLobbyInventory() {
		return lobbyInventory;
	}
}
