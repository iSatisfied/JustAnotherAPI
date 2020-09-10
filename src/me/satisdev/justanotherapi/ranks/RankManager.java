package me.satisdev.justanotherapi.ranks;

import me.satisdev.justanotherapi.JustAnotherAPI;
import me.satisdev.justanotherapi.permissions.PermissionManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankManager {

	private final JustAnotherAPI justAnotherApi;

	private final PermissionManager permissionManager;
	private final String[] allRanks = {"default", "donor", "builder", "staff", "admin", "dev", "owner"};

	public RankManager(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;

		permissionManager = justAnotherApi.getPermissionManager();
	}

	public String getPlayerRank(Player player) {
		String uuid = player.getUniqueId().toString();

		if (justAnotherApi.getPlayersFile().getFile().get("players." + uuid + ".rank").toString() == null) return "None";

		return justAnotherApi.getPlayersFile().getFile().get("players." + uuid + ".rank").toString().replace("\"", "");
	}

	public void setPlayerRank(Player player, Ranks rank) {
		List<String> newPerms = new ArrayList<>();
		String uuid = player.getUniqueId().toString();

		justAnotherApi.getPlayersFile().getFile().set("players." + uuid + ".rank", rank.getRank());
		justAnotherApi.getPlayersFile().saveFile();

		for (String playerPerms : permissionManager.getPerms(player))
			if (!permissionManager.getPermissionsList().contains(playerPerms)) newPerms.add(playerPerms);

		permissionManager.removePerm(player, "all");
		newPerms.addAll(Arrays.asList(permissionManager.getPermissions()).subList(0, rank.getInt()));

		if (rank.getInt() == 4) newPerms.remove(permissionManager.getSpecificPerms(2));
		for (String add : newPerms) permissionManager.addPerm(player, add);
	}

	public String[] getAllRanks() {
		return allRanks;
	}
}
