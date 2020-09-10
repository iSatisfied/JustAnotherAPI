package me.satisdev.justanotherapi.events;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.ranks.RankManager;
import me.satisdev.justanotherapi.ranks.Ranks;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinEvent implements Listener {

	private final JustAnotherAPI justAnotherApi;
	private final RankManager rankManager;

	public JoinEvent(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;
		rankManager = justAnotherApi.getRankManager();

		justAnotherApi.getServer().getPluginManager().registerEvents(this, justAnotherApi);
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();

		if (justAnotherApi.getBanned().getFile().getConfigurationSection("banned") != null) {
			for (String section : justAnotherApi.getBanned().getFile().getConfigurationSection("banned").getKeys(false)) {
				if (section.equals(uuid)) {
					if (justAnotherApi.getBanned().getFile().get("banned." + uuid + ".time") != null) {
						event.disallow(PlayerLoginEvent.Result.KICK_OTHER, justAnotherApi.color("&cYou have been ban from &3ServerNetwork&c! " +
								"\nBy: " + justAnotherApi.getBanned().getFile().getString("banned." + uuid + ".banner") +
								"\nReason: " + justAnotherApi.getBanned().getFile().getString("banned." + uuid + ".reason") +
								"\nTime: " + DurationFormatUtils.formatDurationWords(justAnotherApi.getBanned().getFile().getInt("banned." + uuid + ".time"), true ,true)));

					} else {
						event.disallow(PlayerLoginEvent.Result.KICK_OTHER, justAnotherApi.color("&cYou have been ban from &3ServerNetwork&c! " +
								"\nBy: " + justAnotherApi.getBanned().getFile().getString("banned." + uuid + ".banner") +
								"\nReason: " + justAnotherApi.getBanned().getFile().getString("banned." + uuid + ".reason") +
								"\nTime: Permanent"));
					}
				}
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();

		if (justAnotherApi.getPlayersFile().getFile().getConfigurationSection("players") != null) {
			for (String section : justAnotherApi.getPlayersFile().getFile().getConfigurationSection("players").getKeys(false)) {
				if (section.equals(uuid)) {
					if (!justAnotherApi.getPlayersFile().getFile().getString("players." + uuid + ".rank").equals("None")) {
						justAnotherApi.getPlayersFile().getFile().set("players." + uuid + ".rank",
								"\"" + rankManager.getPlayerRank(player) + "\"");
						justAnotherApi.getPlayersFile().saveFile();
						continue;
					}
						rankManager.setPlayerRank(player, Ranks.DEFAULT);
				}
			}
		}

		System.out.println(justAnotherApi.getRankManager().getPlayerRank(player));

		justAnotherApi.setOnline(justAnotherApi.getOnline() + 1);

	}
}
