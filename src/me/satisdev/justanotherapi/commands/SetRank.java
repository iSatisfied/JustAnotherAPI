package me.satisdev.justanotherapi.commands;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.permissions.PermissionManager;
import me.satisdev.justanotherapi.ranks.RankManager;
import me.satisdev.justanotherapi.ranks.Ranks;
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

public class SetRank implements CommandExecutor, TabCompleter {

	private JustAnotherAPI justAnotherApi;

	private PermissionManager permissionManager;
	private RankManager rankManager;
	private ServerMessages serverMessages;

	public SetRank(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;

		permissionManager = this.justAnotherApi.getPermissionManager();
		rankManager = this.justAnotherApi.getRankManager();
		serverMessages = this.justAnotherApi.getServerMessages();

		justAnotherApi.getCommand("setrank").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player;
		Player target;

		if (sender instanceof Player) {
			player = (Player) sender;

			if (!permissionManager.hasPerms(player, "justanother.admin")) {
				sender.sendMessage(serverMessages.getNoPerms());
				return true;
			}
		}

		if (args.length == 0) {
			sender.sendMessage(serverMessages.getNoPlayer());
			return true;
		} else if (args.length == 1) {
			sender.sendMessage(justAnotherApi.color("&cYou must enter a valid rank."));
			return true;

		} else {
			target = Bukkit.getServer().getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(justAnotherApi.color("&c" + args[0] + " is offline."));
				return true;
			}

			switch (args[1].toLowerCase()) {
				case "default":
					rankManager.setPlayerRank(target, Ranks.DEFAULT);

					break;
				case "donor":
					rankManager.setPlayerRank(target, Ranks.DONOR);

					break;
				case "builder":
					rankManager.setPlayerRank(target, Ranks.BUILDER);

					break;
				case "staff":
					rankManager.setPlayerRank(target, Ranks.STAFF);

					break;
				case "admin":
					rankManager.setPlayerRank(target, Ranks.ADMIN);

					break;
				case "dev":
					rankManager.setPlayerRank(target, Ranks.DEV);

					break;
				case "owner":
					rankManager.setPlayerRank(target, Ranks.OWNER);

					break;
				default:
					sender.sendMessage(justAnotherApi.color("&cYou must enter a valid rank."));
					return true;
			}

			sender.sendMessage(justAnotherApi.color("&aSuccessfully set " + target.getName() + "'s to &r" +
					rankManager.getPlayerRank(target)));

					justAnotherApi.sendMessage(target, "&aRank changed to &r" + rankManager.getPlayerRank(target));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 1) {
			List<String> players = new ArrayList<>();

			if (args[0].equals("")) {
				for (Player player : justAnotherApi.getServer().getOnlinePlayers()) {
					players.add(player.getName());
				}
			} else {
				for (Player player : justAnotherApi.getServer().getOnlinePlayers()) {
					if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
						players.add(player.getName());
					}
				}
			}

			return players;

		} else if (args.length == 2) {
			List<String> ranks = new ArrayList<>();

			if (args[1].equals("")) ranks.addAll(Arrays.asList(rankManager.getAllRanks()));

			else for (String rank : rankManager.getAllRanks()) if (rank.toLowerCase().startsWith(args[1].toLowerCase())) ranks.add(rank);

			return ranks;
		}
		return null;
	}
}
