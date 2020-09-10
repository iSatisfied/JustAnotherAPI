package me.satisdev.justanotherapi.commands;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.permissions.PermissionManager;
import me.satisdev.justanotherapi.utils.ServerMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permissions implements CommandExecutor, TabCompleter {

	private JustAnotherAPI justAnotherApi;

	private ChatUtil chatUtil;
	private PermissionManager permissionManager;
	private ServerMessages serverMessages;

	public Permissions(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;

		chatUtil = justAnotherApi.getChatUtil();
		permissionManager = justAnotherApi.getPermissionManager();
		serverMessages = justAnotherApi.getServerMessages();

		justAnotherApi.getCommand("permissions").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player;

		if (sender instanceof Player) {
			player = (Player) sender;

			if (!permissionManager.hasPerms(player, "justanother.admin")) {
				player.sendMessage(serverMessages.getServerName() + serverMessages.getNoPerms());
				return true;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(chatUtil.color("&c/permission <add | remove | get> <player> [permission]"));

		} else if (args.length == 1) {
			sender.sendMessage(chatUtil.color("&cYou must specify a player."));

		} else if (args.length == 2) {
			if (args[0].equals("get")){
				player = Bukkit.getServer().getPlayer(args[1]);

				if (player == null) {
					sender.sendMessage(chatUtil.color("&c" + args[1] + " is offline."));
					return true;
				}

				sender.sendMessage(chatUtil.color("&8" + player.getName() + "&7's permissions are:\n"));

				for (String perm : permissionManager.getPerms(player))
					sender.sendMessage(chatUtil.color("&7 - &f" + perm + "\n"));

			} else sender.sendMessage(chatUtil.color("&cYou must enter a permission."));

		} else {
			player = Bukkit.getServer().getPlayer(args[1]);

			if (player == null) {
				sender.sendMessage(chatUtil.color("&c" + args[1] + " is offline."));
				return true;
			}

			switch (args[0]) {
				case "add":
					if (permissionManager.getPerms(player).contains(args[2])) {
						sender.sendMessage(chatUtil.color("&c" + player.getName() + " already has that permission"));
						break;
					}

					permissionManager.addPerm(player, args[2]);

					sender.sendMessage(chatUtil.color("&aSuccessfully gave " + player.getName() + " the permission &r" + args[2]));
					player.sendMessage(chatUtil.color("&aGiven permission &r" + args[2]));
					break;
				case "remove":
					if (!permissionManager.getPerms(player).contains(args[2])) {
						sender.sendMessage(chatUtil.color("&c" + player.getName() + " does not have that permission"));
						break;
					}

					permissionManager.removePerm(player, args[2]);

					sender.sendMessage(chatUtil.color("&aSuccessfully removed &r" + args[2] + "&a from " + player.getName()));
					player.sendMessage(chatUtil.color("&aPermission &r" + args[2] + "&a removed"));
					break;
				default:
					sender.sendMessage(chatUtil.color("&c/permission <add | remove | get> <player> [permission]"));
					break;
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		String[] options = {"add", "get", "remove"};

		if (args.length == 1) {
			List<String> tabOptions = new ArrayList<>();

			if (args[0].equals("")) tabOptions.addAll(Arrays.asList(options));

			else for (String option : options) if (option.startsWith(args[0].toLowerCase())) tabOptions.add(option);

			return tabOptions;

		} else if (args.length == 2) {
			List<String> players = new ArrayList<>();

			if (args[1].equals(""))
				for (Player player : justAnotherApi.getServer().getOnlinePlayers()) players.add(player.getName());

			else for (Player player : justAnotherApi.getServer().getOnlinePlayers())
				if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) players.add(player.getName());

			return players;
		}
		return null;
	}
}