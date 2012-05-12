package com.nicka101.UsefulCMDs;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class ChatezMap extends MapRenderer {
	
	private final int CANVAS_HEIGHT = 128;
	private final int CANVAS_WIDTH = 128;
	
	public ChatezMap(){
		super(false);
		return;
	}
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player){
		ItemStack item = player.getItemInHand();
		if(item != null && item.getType()==Material.MAP){
			for(int j = 0; j < CANVAS_HEIGHT; j++) {
				for(int i = 0; i < CANVAS_WIDTH; i++) {
                	canvas.setPixel(i, j, MapPalette.TRANSPARENT);
                }
            }
			MapFont font = MinecraftFont.Font;
			try{
				canvas.drawText(0, MinecraftFont.Font.getHeight() * 1, font, "§28;Welcome To Chatez' §36;");
				canvas.drawText(0, MinecraftFont.Font.getHeight() * 3, font, "§28;3 Worlds Server§36;");
				canvas.drawText(0, MinecraftFont.Font.getHeight() * 5, font, "§16;Using UsefulCMDs§36;");
				canvas.drawText(0, MinecraftFont.Font.getHeight() * 7, font, "§48;Coding By Nicka101§36;");
			} catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
	}
}
