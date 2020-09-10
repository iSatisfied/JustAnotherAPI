package me.satisdev.justanotherapi;

import me.satisdev.justanotherapi.commands.*;
import me.satisdev.justanotherapi.events.ChatEvents;
import me.satisdev.justanotherapi.events.JoinEvent;
import me.satisdev.justanotherapi.inventories.Inventories;
import me.satisdev.justanotherapi.permissions.PermissionManager;
import me.satisdev.justanotherapi.ranks.RankManager;
import me.satisdev.justanotherapi.settings.SettingsManager;
import me.satisdev.justanotherapi.utils.ServerMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class JustAnotherAPI extends JavaPlugin {

	private boolean chat = true;
	private long online = 0;

	private static JustAnotherAPI API;
	private Inventories inventories;
	private PermissionManager permissionManager;
	private RankManager rankManager;
	private ServerMessages serverMessages;

	private SettingsManager banned;
	private SettingsManager muted;
	private SettingsManager playersFile;

	@Override
	public void onEnable() {
		API = this;

		inventories = new Inventories();
		permissionManager = new PermissionManager(this);
		rankManager = new RankManager(this);
		serverMessages = new ServerMessages(this);

		registerCommands();
		registerEvents();
		registerSettings();
		punishmentTimer();
	}

	@Override
	public void onDisable() {
		API = null;
	}

	private void registerCommands() {

		new Ban(this);
		new GlobalMute(this);
		new Mute(this);
		new SetRank(this);
		new Permissions(this);

	}

	private void registerEvents() {

		new ChatEvents(this);
		new JoinEvent(this);

	}

	private void registerSettings() {

		// Banned playersFile Setting Manager
		banned = new SettingsManager(this, "banned_players");
		banned.setupFile();
		banned.saveFile();
		banned.reloadFile();

		// Muted playersFile Setting Manager
		muted = new SettingsManager(this, "muted_players");
		muted.setupFile();
		muted.saveFile();
		muted.reloadFile();

		// Player files Setting Manager
		playersFile = new SettingsManager(this, "player_files");
		playersFile.setupFile();
		playersFile.saveFile();
		playersFile.reloadFile();
	}

	private void punishmentTimer() {
		if (getMuted().getFile().getConfigurationSection("muted") != null) {
			for (String uuid : getMuted().getFile().getConfigurationSection("muted").getKeys(false)) {
				if (getMuted().getFile().get("muted." + uuid + ".time") != null) {
					new BukkitRunnable() {
						public void run() {
							getMuted().getFile().set("muted." + uuid + ".time",
									getMuted().getFile().getInt("muted." + uuid + ".time") - 1);
							getMuted().saveFile();

							if (getMuted().getFile().getInt("muted." + uuid + ".time") <= 0) {
								getMuted().getFile().set("muted." + uuid, null);
								getMuted().saveFile();
								getMuted().reloadFile();
							}
						}
					}.runTaskTimerAsynchronously(this, 0, 20);
				}
			}
		}
	}

	public boolean checkPermissions(CommandSender sender, String permission) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (!permissionManager.hasPerms(player, permission)) {
				sendLogMessage(player, serverMessages.getNoPerms());
				return true;
			}
		}
		return false;
	}

	public void sendLogMessage(Player player, String message) {
		player.sendMessage(color(message));
	}

	public void broadcastMessage(String message) {
		getServer().broadcastMessage(message);
	}

	public String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public boolean isChat() {
		return chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}

	public long getOnline() {
		return online;
	}

	public void setOnline(long online) {
		this.online = online;
	}

	public static JustAnotherAPI getAPI() {
		return API;
	}

	public Inventories getInventories() {
		return inventories;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}

	public RankManager getRankManager() {
		return rankManager;
	}

	public ServerMessages getServerMessages() {
		return serverMessages;
	}

	public SettingsManager getBanned() {
		return banned;
	}

	public SettingsManager getMuted() {
		return muted;
	}

	public SettingsManager getPlayersFile() {
		return playersFile;
	}
}