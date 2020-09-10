package me.satisdev.justanotherapi.utils;

import me.satisdev.justanotherapi.JustAnotherAPI;

public class ServerMessages {

	private final String serverName;
	private final String noPerms;
	private final String noPlayer;
	private final String chatMuted;
	private final String chatOff;
	private final String chatOn;

	public ServerMessages(JustAnotherAPI api) {
		serverName = api.color("&0[&4JustAnother&0] &9");
		noPerms = api.color("&cYou do not have permission to use this command.");
		noPlayer = api.color("&cYou must specify a player.");
		chatMuted = api.color("&bChat is currently muted");
		chatOff = api.color("&6Chat is now muted!");
		chatOn = api.color("&aChat is no-longer muted!");
	}

	public String getServerName() {
		return serverName;
	}

	public String getNoPerms() {
		return noPerms;
	}

	public String getNoPlayer() {
		return noPlayer;
	}

	public String getChatMuted() {
		return chatMuted;
	}

	public String getChatOff() {
		return chatOff;
	}

	public String getChatOn() {
		return chatOn;
	}
}
