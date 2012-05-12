package com.nicka101.UsefulCMDs;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class WaterDamage implements Runnable{
	private EntityDamageEvent theEvent;
	private UsefulCMDs plugin;
	private Player player;
	
	public WaterDamage(EntityDamageEvent event, UsefulCMDs plugin, Player p){
		theEvent = event;
		this.plugin = plugin;
		player = p;
	}
	
	public void run(){
		if(theEvent!=null){
			if(plugin.NetherPersonInWater.containsKey(player)){
				Block block = plugin.NetherPersonInWater.get(player);
				EntityDamageByWaterEvent newEvent = new EntityDamageByWaterEvent(player, 4, block);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new WaterDamage(newEvent, plugin, player), 21);
			}
			plugin.getServer().getPluginManager().callEvent(theEvent);
			if(!theEvent.isCancelled() && theEvent.getEntityType()==EntityType.PLAYER && theEvent.getDamage()!=0){
				if(!((Player)theEvent.getEntity()).hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)){
					((Player)theEvent.getEntity()).damage(theEvent.getDamage());
				}
				
			}
			
		}
	}
}
