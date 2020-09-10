package me.satisdev.justanotherapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public abstract class SideScoreboard {

	private String scoreboardName;

	private final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
	private final Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
	private final Objective objective;

	public SideScoreboard(String scoreboardName, String objectiveName) {
		this.scoreboardName = scoreboardName;

		objective = scoreboard.registerNewObjective(objectiveName, "dummy");

	}

	public String getScoreboardName() {
		return scoreboardName;
	}

	public void setScoreboardName(String scoreboardName) {
		this.scoreboardName = scoreboardName;
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public Objective getObjective() {
		return objective;
	}

	protected void setObjectiveDisplays() {
		objective.setDisplayName(getScoreboardName());
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public abstract void setSideScoreboard(Player player);
}
