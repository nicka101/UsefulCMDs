package com.nicka101.UsefulCMDs;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OverworldWaterBreath implements Runnable {
	
	private UsefulCMDs plugin;
	private Player player;
	private static PotionEffect pot;
	
	public OverworldWaterBreath(UsefulCMDs plug, Player p){
		plugin = plug;
		player = p;
		pot = new PotionEffect(PotionEffectType.WATER_BREATHING, 50, 4);
	}
	public void run(){
		if(plugin.MinecraftNoDrown.containsKey(player) && plugin.MinecraftNoDrown.get(player)){
			player.addPotionEffect(pot, true);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 46);
		}
	}
}
