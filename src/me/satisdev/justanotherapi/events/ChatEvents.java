package me.satisdev.justanotherapi.events;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.utils.ServerMessages;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvents implements Listener {

	private final JustAnotherAPI justAnotherApi;

	private final ServerMessages serverMessages;

	public ChatEvents(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;
		serverMessages = justAnotherApi.getServerMessages();

		justAnotherApi.getServer().getPluginManager().registerEvents(this, justAnotherApi);
	}

	@EventHandler
	public void ChatEventsListener(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();

		if (!justAnotherApi.isChat()) {
			if (!event.getPlayer().hasPermission("shattered.staff")) {
				event.setCancelled(true);
				justAnotherApi.sendLogMessage(player, serverMessages.getChatMuted());
			}
		}

		if (justAnotherApi.getMuted().getFile().getConfigurationSection("muted") != null) {
			for (String section : justAnotherApi.getMuted().getFile().getConfigurationSection("muted").getKeys(false)) {
				if (section.equals(uuid)) {
					if (justAnotherApi.getMuted().getFile().get("muted." + uuid + ".time") != null) {
						event.setCancelled(true);
						justAnotherApi.sendLogMessage(player, "&cYou have been muted from &3ServerNetwork&c!" +
								"\nBy: " + justAnotherApi.getMuted().getFile().getString("muted." + uuid + ".banner") +
								"\nReason: " + justAnotherApi.getMuted().getFile().getString("muted." + uuid + ".reason") +
								"\nTime: " + DurationFormatUtils.formatDurationWords(justAnotherApi.getMuted().getFile().getInt("muted." + uuid + ".time"), true, true));
					} else {
						event.setCancelled(true);
						justAnotherApi.sendLogMessage(player, "&cYou have been muted from &3ServerNetwork&c!" +
								"\nBy: " + justAnotherApi.getMuted().getFile().getString("muted." + uuid + ".banner") +
								"\nReason: " + justAnotherApi.getMuted().getFile().getString("muted." + uuid + ".reason") +
								"\nTime: Permanent");
					}
				}
			}
		}
	}
}
