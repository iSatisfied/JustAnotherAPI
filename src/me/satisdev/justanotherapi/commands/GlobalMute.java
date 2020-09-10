package me.satisdev.justanotherapi.commands;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.utils.ServerMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlobalMute implements CommandExecutor {

	private JustAnotherAPI justAnotherApi;
	private ServerMessages serverMessages;

	public GlobalMute(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;

		serverMessages = justAnotherApi.getServerMessages();

		justAnotherApi.getCommand("globalmute").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(justAnotherApi.color("&cYou must be a player to use this command."));
			return true;
		}

		if (justAnotherApi.checkPermissions(sender, "justanother.staff")) return true;

		if (justAnotherApi.isChat()) {
			justAnotherApi.setChat(false);
			justAnotherApi.broadcastMessage(serverMessages.getChatOff());
		} else {
			justAnotherApi.setChat(true);
			justAnotherApi.broadcastMessage(serverMessages.getChatOn());
		}

		return true;
	}
}