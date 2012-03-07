package com.nicka101.UsefulCMDs;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.ChatColor;

public class UsefulCMDs extends JavaPlugin implements Listener{
	
	public static UsefulCMDs p;
	public final Logger logger = Logger.getLogger("Minecraft");
	public ChatColor RED = ChatColor.RED;
	public ChatColor BLUE = ChatColor.BLUE;
	public ChatColor WHITE = ChatColor.WHITE;
	public PotionEffect nodmg = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2000, 4);
	private Player[] frozen = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, };
	private boolean badBuild = false;
	
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName() + "] has been disabled.");
	}
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName() + "] is now enabled. :)");
		badBuild = false; //Cos Bukkit Likes To Be Fucking Stupid
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if(cmd.getName().equalsIgnoreCase("difficulty") || cmd.getName().equalsIgnoreCase("diff")){
			if((player != null) && player.hasPermission("usefulcmds.difficulty")){
				World world = player.getWorld();
				if(args.length <= 0){
					Difficulty diff = world.getDifficulty();
					player.sendMessage(RED + "[Server]" + BLUE + " Difficulty is Currently: " + WHITE + diff.toString());
					return true;
				} else {
					if((args[0].equalsIgnoreCase("peaceful") )|| args[0].equalsIgnoreCase("p")){
						world.setDifficulty(Difficulty.PEACEFUL);
						player.sendMessage(RED + "[Server]" + WHITE + " Difficulty has been changed to Peaceful.");
						return true;
					} else if(args[0].equalsIgnoreCase("easy" )|| args[0].equalsIgnoreCase("e")){
						world.setDifficulty(Difficulty.EASY);
						player.sendMessage(RED + "[Server]" + WHITE + " Difficulty has been changed to Easy.");
						return true;
					} else if(args[0].equalsIgnoreCase("normal" )|| args[0].equalsIgnoreCase("n")){
						world.setDifficulty(Difficulty.NORMAL);
						player.sendMessage(RED + "[Server]" + WHITE + " Difficulty has been changed to Normal.");
						return true;
					} else if(args[0].equalsIgnoreCase("hard" )|| args[0].equalsIgnoreCase("h")){
						world.setDifficulty(Difficulty.HARD);
						player.sendMessage(RED + "[Server]" + WHITE + " Difficulty has been changed to Hard.");
						return true;
					} else {
						player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " Invalid Argument");
						return false;
					}
				}
			}
		} else if(cmd.getName().equalsIgnoreCase("pvp")){
			if((player != null) && player.hasPermission("usefulcmds.pvp")){
				World world = player.getWorld();
				boolean pvp = world.getPVP();
				String msg = RED + "[Server]" + BLUE + " PvP Flag now set to: " + WHITE + "";
				if(pvp) msg += "False";
				else msg += "True";
				world.setPVP(!pvp);
				player.sendMessage(msg);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("shoot")){
			if((player != null) && player.hasPermission("usefulcmds.shoot")){
				player.shootArrow();
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("thunder")){
			if((player != null) && player.hasPermission("usefulcmds.thunder")){
				World world = player.getWorld();
				world.setStorm(true);
				world.setThunderDuration(1000);
				world.setThundering(true);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("target")){
			if((player != null) && player.hasPermission("usefulcmds.target")){
				if((args.length == 0)||(args.length > 1)){
					return false;
				}
				Player targetedPlayer = null;
				World world = player.getWorld();
				Server s = player.getServer();
				targetedPlayer = s.getPlayer(args[0]);
				if(targetedPlayer != null){
					List<Entity> entities = world.getEntities();
					for(int i=0;i<entities.size();i++){
						if(entities.get(i) instanceof Monster){
							((Monster) entities.get(i)).setTarget(targetedPlayer);
							((Monster) entities.get(i)).addPotionEffect(nodmg, true);
							((Monster) entities.get(i)).teleport(targetedPlayer, TeleportCause.PLUGIN);
						}
					}
					return true;
				} else {
					return false;
				}
			}
		} else if(cmd.getName().equalsIgnoreCase("mount")){
			if((player != null) && player.hasPermission("usefulcmds.mount")){
				if((args.length == 0)||(args.length > 2)){
					return false;
				}
				Player rider = player;
				Player targetedPlayer = null;
				Server s = player.getServer();
				if(args.length == 2){
					rider = s.getPlayer(args[1]);
				}
				targetedPlayer = s.getPlayer(args[0]);
				if(!targetedPlayer.eject()){
					targetedPlayer.setPassenger(rider);
				}
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("spawncj")){
			if((player != null) && player.hasPermission("usefulcmds.spawncj")){
				Player target = null;
				Server server = player.getServer();
				if(args.length == 1) target = server.getPlayer(args[0]);
				if(args.length == 0) target = player;
				if(args.length > 1 || target == null){
					return false;
				}
				
				World world = player.getWorld();
				List<Entity> entities = world.getEntities();
				Creeper creep = null;
				Spider spider = null;
				for(int i=0;i<entities.size();i++){
					if(entities.get(i) instanceof Creeper){
						creep = (Creeper) entities.get(i);
					}
					if(entities.get(i) instanceof Spider){
						spider = (Spider) entities.get(i);
					}
				}
				if(creep != null && spider != null){
					spider.teleport(target, TeleportCause.PLUGIN);
					creep.teleport(creep, TeleportCause.PLUGIN);
					spider.setPassenger(creep);
					creep.setTarget(target);
					spider.setTarget(target);
					
				}
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("effect")){
			if((player != null) && player.hasPermission("usefulcmds.effects")){
				PotionEffectType effectType = null;
				int power = 4, duration = 0;
				Player target = player;
				if(args.length==3){
					if(args[0].equalsIgnoreCase("blind")){
						effectType = PotionEffectType.BLINDNESS;
						power = 100000000;
					} else if(args[0].equalsIgnoreCase("nodmg")){
						effectType = PotionEffectType.DAMAGE_RESISTANCE;
					} else if(args[0].equalsIgnoreCase("jump")){
						effectType = PotionEffectType.JUMP;
					} else if(args[0].equalsIgnoreCase("confuse")){
						effectType = PotionEffectType.CONFUSION;
					} else {
						return false;
					}
					if(args[1].equalsIgnoreCase("short")){
						duration = 2000;
					} else if(args[1].equalsIgnoreCase("medium")){
						duration = 4000;
					} else if(args[1].equalsIgnoreCase("long")){
						duration = 6000;
					} else {
						return false;
					}
					if(args[2] != null){
						target = player.getServer().getPlayer(args[2]);
					}
					if(target==null) return false;
					PotionEffect effect = new PotionEffect(effectType, duration, power);
					target.addPotionEffect(effect, true);
					return true;
				}
				return false;
			}
		} else if(cmd.getName().equalsIgnoreCase("death")){
			if((player != null) && player.hasPermission("usefulcmds.death")){
				Player target;
				if(args.length == 1){
					target = player.getServer().getPlayer(args[0]);
					if(target == null) return false;
				} else if(args.length == 0){
					target = player;
				} else {
					return false;
				}
				target.setHealth(0);
				target.setHealth(20);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("hidefrom")){
			if((player != null) && player.hasPermission("usefulcmds.hide") && args.length==1){
				Player target = player.getServer().getPlayer(args[0]);
				if(target == null) return false;
				if(target.canSee(player)){
					target.hidePlayer(player);
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + "You are now hidden from " + target.getName());
				} else {
					target.showPlayer(player);
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + "You are now visible to " + target.getName());
				}
				return true;
			}
			return false;
		} else if(cmd.getName().equalsIgnoreCase("freeze")){
			if((player != null) && player.hasPermission("usefulcmds.freeze")){
				if(args.length == 1){
					Player target = player.getServer().getPlayer(args[0]);
					if(target == null) return false;
					for(int i=0;i<frozen.length;i++){
						if(frozen[i] == null) break;
						if(frozen[i].equals(target)){
							frozen[i] = null;
							player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " " + target.getName() +" has been Unfrozen.");
							return true;
						}
					}
					for(int i=0;i<frozen.length;i++){
						if(frozen[i]==null){
							frozen[i] = target;
							player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " " + target.getName() +" has been Frozen in Place.");
							return true;
						}
					}
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " " + target.getName() +" cannot be Frozen due to the Freeze Array Being Full.");
					return true;
					
				} else {
					return false;
				}
			}
			return false;
		} else if(cmd.getName().equalsIgnoreCase("buildanything")){
			if((player != null) && player.hasPermission("usefulcmds.buildanything")){
				if(badBuild){
					badBuild = false;
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " You can no longer Disobey Standard Build Rules");
					return true;
				} else {
					badBuild = true;
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " You can now build anything, without restriction");
					return true;
				}
			}
			
		} else if(cmd.getName().equalsIgnoreCase("launchmobs")){
				Vector vector = new Vector(0,30,0);
				List<World> worlds = this.getServer().getWorlds();
				for(World world : worlds){
					List<Entity> entities = world.getEntities();
					for(Entity ent : entities){
						if((ent instanceof LivingEntity) && !(ent instanceof Player)){
							ent.setVelocity(vector);
						}
					}
				}
				return true;
			
		} else if(cmd.getName().equalsIgnoreCase("launchplayer")){
			if((player != null) && player.hasPermission("usefulcmds.launchplayer")){
				Player target = null;
				Vector v = null;
				if(args.length == 0){
					target = player;
				} else {
					target = this.getServer().getPlayer(args[0]);
					if(target==null) return false;
					v = target.getLocation().getDirection().multiply(3);
					
				}
				if(v==null) return false;
				player.setVelocity(v);
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player p = event.getPlayer();
		for(int i=0; i<frozen.length;i++){
			if(frozen[i]== null) continue;
			if(frozen[i].equals(p)){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event){
		Player p = event.getPlayer();
		for(int i=0; i<frozen.length;i++){
			if(frozen[i]== null) continue;
			if(frozen[i].equals(p)){
				event.setCancelled(true);
			}
		}
		
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreakLow(BlockBreakEvent event){
		Player p = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		World world = event.getBlock().getWorld();
		ItemStack itemInHand = p.getItemInHand();
		Material mat = itemInHand.getType();
		if(mat==Material.DIAMOND_PICKAXE || mat==Material.IRON_PICKAXE || mat==Material.STONE_PICKAXE || mat==Material.WOOD_PICKAXE){
			if(itemInHand.containsEnchantment(Enchantment.SILK_TOUCH)){
				Block block = event.getBlock();
				ItemStack drops;
				if(block.getType()==Material.ICE){
					drops = new ItemStack(Material.ICE, 1);
				} else if(block.getTypeId()==97){
					drops = new ItemStack(97, 1, block.getData());
				} else {
					return;
				}
				if(p.getGameMode()==GameMode.CREATIVE) return;
				block.breakNaturally();
				world.dropItemNaturally(loc, drops);
			}
		}
	}
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event){
		Player p = event.getPlayer();
		for(int i=0; i<frozen.length;i++){
			if(frozen[i]== null) continue;
			if(frozen[i].equals(p)){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onCanBuild(BlockCanBuildEvent event){
		if(badBuild){
			event.setBuildable(true);
		}
	}
	@EventHandler
	public void onRedstone(BlockRedstoneEvent event){
		if(badBuild){
			event.setNewCurrent(event.getOldCurrent());
		}
	}
	@EventHandler
	public void onPhysics(BlockPhysicsEvent event){
		if(badBuild){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onShot(ProjectileHitEvent event){
			if(event.getEntity() instanceof Player){
				Player p = (Player) event.getEntity();
				List<Entity> ents = p.getNearbyEntities(1, 1, 1);
				for( Entity ent : ents){
					if(ent instanceof Arrow){
						if(((Arrow)ent).getPassenger()==p){
							Vector v = ent.getVelocity();
							p.getWorld().spawnArrow(ent.getLocation(), v, (float)0.6,(float) 2).setPassenger(p);
						}
					}
				}
			}
	}
}
