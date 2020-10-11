package me.satisdev.justanotherapi.commands;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.permissions.PermissionManager;
import me.satisdev.justanotherapi.utils.ServerMessages;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Unban implements CommandExecutor {

	private JustAnotherAPI justAnotherApi;

	private PermissionManager permissionManager;
	private ServerMessages serverMessages;

	public Unban(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;

		permissionManager = this.justAnotherApi.getPermissionManager();
		serverMessages = this.justAnotherApi.getServerMessages();

		justAnotherApi.getCommand("unban").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player target;

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (!permissionManager.hasPerms(player, "justanother.staff")) {
				sender.sendMessage(serverMessages.getNoPerms());
				return true;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(justAnotherApi.color("&cYou must specify a player."));
			return true;

		} else {

			target = Bukkit.getServer().getPlayer(args[0]);
			String uuid;

			if (target == null) {
				uuid = Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId().toString();
			} else {
				uuid = target.getUniqueId().toString();
			}

			justAnotherApi.getServer().getBanList(BanList.Type.NAME).pardon(uuid);

		}

		return true;
	}
}