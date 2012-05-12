package com.nicka101.UsefulCMDs;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class RainDamage implements Runnable {
	private UsefulCMDs p;
	
	public RainDamage(UsefulCMDs plugin){
		p = plugin;
	}
	
	public void run(){
		Player[] players = p.getServer().getOnlinePlayers();
		for(Player player : players){
			if(p.EndPeopleOutside.containsKey(player)){
				EntityDamageEvent theEvent = new EntityDamageEvent(player, DamageCause.CUSTOM, 1);
				p.getServer().getPluginManager().callEvent(theEvent);
				if(!theEvent.isCancelled() && theEvent.getDamage()!=0 && theEvent.getEntity() instanceof Player && p.EndPeopleOutside.get(player)){
					((Player)theEvent.getEntity()).damage(theEvent.getDamage());
				}
				if(p.isRaining){
					reSchedule();
				}
			}
		}
	}
	
	private void reSchedule(){
		p.getServer().getScheduler().scheduleSyncDelayedTask(p, this, 19);
	}
}
