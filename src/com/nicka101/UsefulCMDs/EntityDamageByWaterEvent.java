package com.nicka101.UsefulCMDs;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByWaterEvent extends EntityDamageEvent{
	
	private Block block;
	
	public EntityDamageByWaterEvent(final LivingEntity damagee, final int damage, final Block damager){
		super(damagee, DamageCause.CUSTOM, damage);
		block = damager;
	}
	public Block getBlock(){
		return block;
	}
	public void setBlock(Block b){
		block = b;
	}
}
