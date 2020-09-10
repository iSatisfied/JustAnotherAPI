package me.satisdev.justanotherapi.commands;

import me.satisdev.justanotherapi.JustAnotherAPI;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class Mute implements CommandExecutor {

	private final JustAnotherAPI justAnotherApi;

	public Mute(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;

		justAnotherApi.getCommand("mute").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player target;
		String muter = "Console";

		if (justAnotherApi.checkPermissions(sender, "justanother.staff")) return true;

		if (sender instanceof Player) muter = sender.getName();

		if (args.length == 0) {
			sender.sendMessage(justAnotherApi.color("&cYou must specify a player."));
			return true;

		} else if (args.length == 1) {
			sender.sendMessage(justAnotherApi.color("&cYou must enter a time then reason."));
			return true;

		}else if (args.length == 2) {
			sender.sendMessage(justAnotherApi.color("&cYou must enter a reason."));
			return true;

		} else {
			target = Bukkit.getServer().getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(justAnotherApi.color("&cCannot find player '" + args[0] + "'"));
				return true;
			}

			String reason = "";

			for (int i = 2; i < args.length; i++) {
				reason += args[i] + " ";
			}

			reason = reason.trim();

			if (args[1].equals("perm")) {

				target.sendMessage(justAnotherApi.color("&cYou have been muted from &3ServerNetwork&c! " +
						"\nBy: " + muter +
						"\nReason: " + reason +
						"\nTime: Permanent"));

				justAnotherApi.getMuted().getFile().set("muted." + target.getUniqueId().toString() + ".muter", muter);
				justAnotherApi.getMuted().getFile().set("muted." + target.getUniqueId().toString() + ".reason", reason);
				justAnotherApi.getMuted().saveFile();

				Bukkit.broadcastMessage(justAnotherApi.color("&c" +target.getName() + " has been muted for " + reason + "."));
				return true;

			} else if (args[1].substring(0, 1).matches("[0-9]")) {

				try {
					char timeLength = args[1].charAt(args[1].length() - 1);
					long time = Long.parseLong(args[1].substring(0, args[1].length() - 1));
					if (time < 1) {
						sender.sendMessage(ChatColor.RED + "Valid date format required (#s/m/h/d/w/n)");
						return true;
					}

					if (timeLength == 's' || timeLength == 'S') {
						time = time * 1000;
					} else if (timeLength == 'm' || timeLength == 'M') {
						time = time * 1000 * 60;
					} else if (timeLength == 'h' || timeLength == 'H') {
						time = time * 1000 * 60 * 60;
					} else if (timeLength == 'd' || timeLength == 'D') {
						time = time * 1000 * 60 * 60 * 24;
					} else if (timeLength == 'w' || timeLength == 'W') {
						time = time * 1000 * 60 * 60 * 24 * 7;
					} else if (timeLength == 'n' || timeLength == 'N') {
						time = time * 1000 * 60 * 60 * 24 * 30;
					} else if (timeLength == 'y' || timeLength == 'Y') {
						time = time * 1000 * 60 * 60 * 24 * 365;
					} else {
						sender.sendMessage(ChatColor.RED + "Valid date format required (#s/m/h/d/w/n)");
						return true;
					}

					if (sender != null && !sender.isOp() && time > (long) 1000 * 60 * 60 * 24 * 365) {
						sender.sendMessage(ChatColor.RED + "You are not allowed to ban/mute for more than 1 year");
						return true;
					}

					Date howLong = new Date(System.currentTimeMillis() + time);

					target.sendMessage(justAnotherApi.color("&cYou have been muted from &3SJustAnother&c!" +
							"\nBy: " + muter +
							"\nReason: " + reason +
							"\nTime: " + DurationFormatUtils.formatDurationWords(howLong.getTime(), true, true)));

					justAnotherApi.getMuted().getFile().set("muted." + target.getUniqueId().toString() + ".muter", muter);
					justAnotherApi.getMuted().getFile().set("muted." + target.getUniqueId().toString() + ".reason", reason);
					justAnotherApi.getMuted().getFile().set("muted." + target.getUniqueId().toString() + ".time", time);
					justAnotherApi.getMuted().saveFile();

					Bukkit.broadcastMessage(justAnotherApi.color("&c" +target.getName() + " has been muted for " + reason + "."));

					new BukkitRunnable() {
						public void run() {
							justAnotherApi.getMuted().getFile().set("muted." + target.getUniqueId().toString() + ".time",
									justAnotherApi.getMuted().getFile().getInt("muted." + target.getUniqueId().toString() + ".time") - 1);
							justAnotherApi.getMuted().saveFile();

							if (justAnotherApi.getMuted().getFile().getInt("muted." + target.getUniqueId().toString() + ".time") == 0) {
								justAnotherApi.getMuted().getFile().set("muted." + target.getUniqueId().toString(), null);
								justAnotherApi.getMuted().saveFile();
							}
						}
					}.runTaskTimerAsynchronously(justAnotherApi, 0, 20);

				} catch (Exception e) {
					if (sender != null) sender.sendMessage(ChatColor.RED + "Invalid date format: " + args[1]);
					return true;
				}
			}
		}
		return true;
	}
}
