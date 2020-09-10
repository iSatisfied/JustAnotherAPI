package me.satisdev.justanotherapi.commands;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.permissions.PermissionManager;
import me.satisdev.justanotherapi.utils.ServerMessages;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;


public class Ban implements CommandExecutor {

    private final JustAnotherAPI justAnotherApi;

    private final PermissionManager permissionManager;
    private final ServerMessages serverMessages;

    public Ban(JustAnotherAPI justAnotherApi) {
        this.justAnotherApi = justAnotherApi;

        permissionManager = this.justAnotherApi.getPermissionManager();
        serverMessages = this.justAnotherApi.getServerMessages();

        justAnotherApi.getCommand("ban").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target;
        String banner = "Console";

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (permissionManager.hasPerms(player, "justanother.staff")) {
                banner = player.getName();

            } else {
                sender.sendMessage(serverMessages.getNoPerms());
                return true;
            }
        }

            if (args.length == 0) {
                sender.sendMessage(justAnotherApi.color("&cYou must specify a player."));
                return true;

            } else if (args.length == 1) {
                sender.sendMessage(justAnotherApi.color("&cYou must enter a time then reason."));
                return true;

            } else if (args.length == 2) {
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

                    target.kickPlayer(justAnotherApi.color("&cYou have been banned from &3ServerNetwork&c! " +
                            "\nBy: " + banner +
                            "\nReason: " + reason +
                            "\nTime: Permanent"));

                    justAnotherApi.getServer().getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, banner);

                    Bukkit.broadcastMessage(justAnotherApi.color("&c" +target.getName() + " has been banned for " + reason + "."));
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

                        target.kickPlayer(justAnotherApi.color("&cYou have been banned from &3SJustAnother&c!" +
                                "\nBy: " + banner +
                                "\nReason: " + reason +
                                "\nUntil: " + howLong));

                        justAnotherApi.getServer().getBanList(BanList.Type.NAME)
                                .addBan(target.getName(), reason, howLong, banner);

                    } catch (Exception e) {
                        if (sender != null) sender.sendMessage(ChatColor.RED + "Invalid date format: " + args[1]);
                        return true;
                    }
                }

            }
        return true;
    }
}
