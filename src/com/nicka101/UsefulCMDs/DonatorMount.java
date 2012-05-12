package com.nicka101.UsefulCMDs;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class DonatorMount implements Runnable {
	
	private Player p;
	private Player r;
	private HashMap<Player, Integer> tasks;
	
	public DonatorMount(UsefulCMDs plugin, Player player, Player riding){
		p = player;
		r = riding;
		tasks = plugin.getTasks();
	}
	public void run(){
		if(p.isInsideVehicle()){
			if(p.getVehicle() == r){
				r.eject();
				tasks.put(p, -1);
			}
		}
	}
}
