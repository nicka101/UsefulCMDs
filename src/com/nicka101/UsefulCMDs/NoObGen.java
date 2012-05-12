package com.nicka101.UsefulCMDs;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class NoObGen implements Runnable {
	
	private Block b;
	
	public NoObGen(Block block){
		b = block;
	}
	public void run(){
		b.setType(Material.REDSTONE_WIRE);
	}
}
