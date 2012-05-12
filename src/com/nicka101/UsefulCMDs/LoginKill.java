package com.nicka101.UsefulCMDs;

import org.bukkit.entity.Player;

public class LoginKill implements Runnable{
	private Player player;
	
	public LoginKill(Player p){
		player = p;
	}
	public void run(){
		player.setHealth(0);
	}
}
