package me.satisdev.justanotherapi.permissions;

import me.satisdev.justanotherapi.JustAnotherAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionManager {

	private final JustAnotherAPI justAnotherApi;

	private final String[] permissions = {"justanother.default","justanother.donor","justanother.builder",
			"justanother.staff", "justanother.admin", "justanother.dev", "justanother.owner"};

	private final List<String> permissionsList = new ArrayList<>(Arrays.asList(permissions));

	public PermissionManager(JustAnotherAPI justAnotherApi) {
		this.justAnotherApi = justAnotherApi;
	}

	public List<String> getPerms(Player player) {
		String uuid = player.getUniqueId().toString();

		return justAnotherApi.getPlayersFile().getFile().getStringList("players." + uuid + ".perms");
	}

	public boolean hasPerms(Player player, String perm) {
		List<String> perms = getPerms(player);

		return perms.contains(perm);
	}

	public void addPerm(Player player, String perm) {
		String uuid = player.getUniqueId().toString();
		List<String> perms = getPerms(player);
		perms.add(perm);

		justAnotherApi.getPlayersFile().getFile().set("players." + uuid + ".perms", perms);
		justAnotherApi.getPlayersFile().saveFile();
	}

	public void removePerm(Player player, String perm) {
		String uuid = player.getUniqueId().toString();
		List<String> perms = getPerms(player);

		if (perm.equals("all")) perms.clear(); else perms.remove(perm);

		justAnotherApi.getPlayersFile().getFile().set("players." + uuid + ".perms", perms);
		justAnotherApi.getPlayersFile().saveFile();
	}

	public String[] getPermissions() {
		return permissions;
	}

	public String getSpecificPerms(int perm) {
		return permissions[perm];
	}

	public List<String> getPermissionsList() {
		return permissionsList;
	}

}
