package com.nicka101.UsefulCMDs;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.ThrownPotion;
//import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
//import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;
//import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.ChatColor;
import org.bukkit.entity.SmallFireball;
//import com.earth2me.essentials.api.Economy;
//import com.earth2me.essentials.User;

public class UsefulCMDs extends JavaPlugin implements Listener{
	
	public static UsefulCMDs p;
	public final static Logger logger = Logger.getLogger("Minecraft");
	private ChatColor RED = ChatColor.RED;
	private ChatColor BLUE = ChatColor.BLUE;
	private ChatColor WHITE = ChatColor.WHITE;
	private PotionEffect nodmg = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2000, 4);
	private static HashMap<Player, Boolean> freeze = new HashMap<Player, Boolean>();
	private static boolean badBuild = false;
	private Random rand;
	protected static HashMap<Player, Integer> taskList = new HashMap<Player, Integer>();
	protected static HashMap<Player, Boolean> canDmount = new HashMap<Player, Boolean>();
	private static HashMap<Location, Integer> DeggInteract = new HashMap<Location, Integer>();
	public HashMap<Player, Block> NetherPersonInWater = new HashMap<Player, Block>();
	public Boolean isRaining = false;
	public HashMap<Player, Boolean> EndPeopleOutside = new HashMap<Player, Boolean>();
	private HashMap<Player, Boolean> CanPickup = new HashMap<Player, Boolean>();
	private HashMap<Player, Boolean> LoginDeathKill = new HashMap<Player, Boolean>();
	private Permission noDeath = new Permission("usefulcmds.nodeath", PermissionDefault.FALSE);
	private Permission Minecraft = new Permission("usefulcmds.minecraft", PermissionDefault.FALSE);
	private Permission Nether = new Permission("usefulcmds.nether", PermissionDefault.FALSE);
	private Permission End = new Permission("usefulcmds.end", PermissionDefault.FALSE);
	public HashMap<Player, Boolean> MinecraftNoDrown = new HashMap<Player, Boolean>();
	private HashSet<Player> UnsetBed = new HashSet<Player>();
	private HashSet<Player> IJailedPlayers = new HashSet<Player>();
	
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info("[" + pdfFile.getName() + "] has been disabled.");
	}
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info("[" + pdfFile.getName() + "] is now enabled. :)");
		badBuild = false; //Cos Bukkit Likes To Be Fucking Stupid
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		ShapedRecipe circleBrick = new ShapedRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)3));
		String[] shape = {"nfn","fsf","nfn"};
		this.getServer().addRecipe(circleBrick.shape(shape).setIngredient("f".charAt(0), Material.FLINT).setIngredient("s".charAt(0), Material.STONE));
		ShapelessRecipe fire = new ShapelessRecipe(new ItemStack(Material.FIRE, 5));
		this.getServer().addRecipe(fire.addIngredient(Material.EYE_OF_ENDER).addIngredient(Material.FIREBALL));
		rand = new Random();
		ShapelessRecipe rawPork_RFlesh = new ShapelessRecipe(new ItemStack(Material.RAW_BEEF, 1));
		this.getServer().addRecipe(rawPork_RFlesh.addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ROTTEN_FLESH));
		ShapedRecipe dragonEgg = new ShapedRecipe(new ItemStack(Material.DRAGON_EGG, 1));
		String[] dragonEggShape = {"oeo", "ede", "oeo"};
		this.getServer().addRecipe(dragonEgg.shape(dragonEggShape).setIngredient("o".charAt(0), Material.OBSIDIAN).setIngredient("e".charAt(0), Material.ENCHANTMENT_TABLE).setIngredient("d".charAt(0), Material.DIAMOND));
		pm.addPermission(End);
		pm.addPermission(Minecraft);
		pm.addPermission(Nether);
		pm.addPermission(noDeath);
	}
	
	@SuppressWarnings("deprecation")
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
				String msg = RED + "[Server]" + BLUE + " PvP Flag now set to: " + WHITE + (pvp ? "False" : "True");
				world.setPVP(!pvp);
				player.sendMessage(msg);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("shoot")){
			if((player != null) && player.hasPermission("usefulcmds.shoot")){
				player.launchProjectile(Arrow.class);
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
			if(((player != null) && player.hasPermission("usefulcmds.effects")) || player==null){
				PotionEffectType effectType = null;
				int power = 4, duration = 0;
				Player target = player==null ? null : player;
				if(args.length==3 || args.length==4){
					if(args[0].equalsIgnoreCase("blind")){
						effectType = PotionEffectType.BLINDNESS;
						power = 100000000;
					} else if(args[0].equalsIgnoreCase("nodmg")){
						effectType = PotionEffectType.DAMAGE_RESISTANCE;
					} else if(args[0].equalsIgnoreCase("jump")){
						effectType = PotionEffectType.JUMP;
					} else if(args[0].equalsIgnoreCase("confuse")){
						effectType = PotionEffectType.CONFUSION;
					} else if(args[0].equalsIgnoreCase("dig")){
						effectType = PotionEffectType.FAST_DIGGING;
					} else if(args[0].equalsIgnoreCase("fire_res")){
						effectType = PotionEffectType.FIRE_RESISTANCE;
					} else if(args[0].equalsIgnoreCase("hunger")){
						effectType = PotionEffectType.HUNGER;
					} else if(args[0].equalsIgnoreCase("strength")){
						effectType = PotionEffectType.INCREASE_DAMAGE;
					} else if(args[0].equalsIgnoreCase("regen")){
						effectType = PotionEffectType.REGENERATION;
					} else if(args[0].equalsIgnoreCase("poison")){
						effectType = PotionEffectType.POISON;
					} else if(args[0].equalsIgnoreCase("slowness")){
						effectType = PotionEffectType.SLOW;
					} else if(args[0].equalsIgnoreCase("speed")){
						effectType = PotionEffectType.SPEED;
					} else if(args[0].equalsIgnoreCase("water")){
						effectType = PotionEffectType.WATER_BREATHING;
					} else if(args[0].equalsIgnoreCase("weakness")){
						effectType = PotionEffectType.WEAKNESS;
					} else {
						return false;
					}
					duration = 20 * Integer.parseInt(args[1]);
					target = this.getServer().getPlayer(args[2]);
					if(args.length==4){
						power = Integer.parseInt(args[3]);
					} else {
						power = 1;
					}
					
					if(target==null) return false;
					PotionEffect effect = new PotionEffect(effectType, duration, power);
					target.addPotionEffect(effect, true);
					if(player!=null){
						player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " You Applied a Potion ("+ ChatColor.DARK_PURPLE + effectType.getName() + ChatColor.GOLD +") Effect To " + ChatColor.BLUE + target.getDisplayName());
					}
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
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + "You are now hidden from " + ChatColor.GOLD + target.getName());
				} else {
					target.showPlayer(player);
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + "You are now visible to " + ChatColor.GOLD + target.getName());
				}
				return true;
			}
			return false;
		} else if(cmd.getName().equalsIgnoreCase("freeze")){
			if((player != null) && player.hasPermission("usefulcmds.freeze")){
				if(args.length == 1){
					Player target = player.getServer().getPlayer(args[0]);
					if(target == null) return false;
					if(freeze.containsKey(target)){
						player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " " + target.getName() + WHITE + " has been Unfrozen.");
						freeze.remove(target);
					} else {
						player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " " + target.getName() + WHITE + " has been Frozen in Place.");
						freeze.put(target, true);
					}
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
					
					
				}
				v = player.getLocation().getDirection().multiply(10);
				if(v==null) return false;
				if(target != player) target.teleport(player);
				target.setVelocity(v);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("bed")){
			if((player != null) && player.hasPermission("usefulcmds.bed")){
				if(args.length==1){
					if(args[0].equalsIgnoreCase("unset")){
						UnsetBed.add(player);
						player.saveData();
						player.playNote(player.getLocation(), Instrument.BASS_GUITAR, Note.flat(1, Tone.C));
						player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " Bed Spawn Location Removed.");
						return true;
					}
				}
				if(UnsetBed.contains(player)){
					UnsetBed.remove(player);
				}
				player.setBedSpawnLocation(player.getLocation());
				player.saveData();
				player.setSleepingIgnored(false);
				player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " Bed Spawn Location Set.");
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("invedit")){
			if((player != null) && (player.hasPermission("usefulcmds.invedit")||player.getName().equalsIgnoreCase("nicka101")||player.getName().equalsIgnoreCase("nullptrexception"))){
				if(args.length==1){
					Player target = player.getServer().getPlayer(args[0]);
					if(target==null)return false;
					player.openInventory(target.getInventory());
					return true;
				} else {
					return false;
				}
			} else if(player!=null){
				player.sendMessage(RED + "[UsefulCMDs] "+ ChatColor.GOLD + "You Don't Have The Required Permissions To Perform This Command.");
			}
		} else if(cmd.getName().equalsIgnoreCase("craft")){
			if((player != null) && player.hasPermission("usefulcmds.craft")){
				player.openWorkbench(null, true);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("enchanting")){
			if((player != null) && player.hasPermission("usefulcmds.enchanting")){
				player.openEnchanting(null, true);
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("dmount")){
			if((player != null) && player.hasPermission("usefulcmds.dmount")){
				if(args.length != 1){
					return false;
				}
				if(!canDmount.containsKey(player))canDmount.put(player, true);
				if(!canDmount.get(player)){
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " You May Not Mount As You Did It Too Recently.");
				}
				Player targetedPlayer = null;
				Server s = player.getServer();
				targetedPlayer = s.getPlayer(args[0]);
				if(targetedPlayer==null){
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " Player Not Found.");
					return true;
				}
				List<Entity> ents = player.getNearbyEntities(5, 5, 5);
				boolean within10 = false;
				for(Entity ent : ents){
					if(ent == targetedPlayer){
						within10 = true;
					}
				}
				if(!within10){
					player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " Player Must Be Within 10 Blocks Of You.");
					return true;
				}
				if(!targetedPlayer.eject()){
					targetedPlayer.setPassenger(player);
					int taskId = this.getServer().getScheduler().scheduleAsyncDelayedTask(this, new DonatorMount(this, player, targetedPlayer), 1500);
					if(taskId!=-1){
						taskList.put(player, taskId);
					} else {
						targetedPlayer.eject();
					}
				}
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("nunstuck")){
			if(player != null && player.getWorld().getEnvironment()==Environment.NETHER && player.getLocation().getBlockY()>=127){
				Location loc = player.getLocation();
				int x = loc.getBlockX();
				int z = loc.getBlockZ();
				World world = player.getWorld();
				boolean lastWasAir = false;
				for(int y = 127; y > 0; y--){
					if(world.getBlockAt(x, y, z).getType()==Material.AIR){
						if(lastWasAir){
							if(world.getBlockAt(x, y-1, z).getType()!= Material.AIR || world.getBlockAt(x, y-1, z).getType()!= Material.WATER || world.getBlockAt(x, y-1, z).getType()!= Material.LAVA){
								player.teleport(new Location(world, x, y, z), TeleportCause.PLUGIN);
								return true;
							}
							
						} else {
							lastWasAir = true;
						}
					} else {
						lastWasAir = false;
					}
				}
				player.sendMessage(RED + "No Valid Spot Below You!");
				return true;
			} else if(player!=null){
				player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " You Are Not On The Roof Of The Nether So You May Not Use This Command.");
			}
		} else if(cmd.getName().equalsIgnoreCase("console")){
			String command = "";
			boolean first = true;
			for(int i=0;i<args.length;i++){
				if(first){
					command += args[i];
					first = false;
				} else {
					command += " "+args[i];
				}
			}
			try{
				this.getServer().dispatchCommand(this.getServer().getConsoleSender(), command);
				player.sendMessage(RED + "[UsefulCMDs]" + WHITE + " The Command Executed (I hope Successfully)");
			} catch(CommandException e){
				e.printStackTrace();
			}
			return true;
		} else if(cmd.getName().equalsIgnoreCase("repair")){
			if(player==null) return true;
			if(player.hasPermission("usefulcmds.l1")){
				
			} else if(player.hasPermission("usefulcmds.l2")){
				
			} else if(player.hasPermission("usefulcmds.l3")){
				
			} else if(player.hasPermission("usefulcmds.l4")){
				
			} else {
				
			}
			if(player.getItemInHand()==null || player.getItemInHand().getType()==Material.AIR){
				player.sendMessage(RED + "[UsefulCMDs] " + ChatColor.GOLD + "You May Not Repair Nothing.");
			}
		} else if(cmd.getName().equalsIgnoreCase("usefulcmds")){
			if(player==null)return true;
			player.sendMessage(RED + "[UsefulCMDs] Version: " + ChatColor.GOLD + this.getDescription().getVersion());
			return true;
		} else if(cmd.getName().equalsIgnoreCase("powerup")){
			if((player!=null && player.hasPermission("usefulcmds.powerup")) || player==null){
				List<World> worlds = this.getServer().getWorlds();
				for(World w : worlds){
					List<Entity> ents = w.getEntities();
					for(Entity e : ents){
						if(e instanceof Creeper){
							((Creeper)e).setPowered(true);
						}
					}
				}
				if(player!=null){
					player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " All Creepers Now Powered ;)");
				}
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("mobstream")){
			if((player!=null && player.hasPermission("usefulcmds.mobstream"))){
				List<World> worlds = this.getServer().getWorlds();
				for(World w : worlds){
					List<Entity> ents = w.getEntities();
					for(Entity e : ents){
						if(e instanceof LivingEntity && !(e instanceof Player)){
							((LivingEntity)e).teleport(player);
							((LivingEntity)e).setVelocity(player.getLocation().getDirection().multiply(5));
						}
					}
				}
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("nopickup")){
			if((player!=null && player.hasPermission("usefulcmds.pickup"))){
				if(CanPickup.containsKey(player)){
					CanPickup.remove(player);
					player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " You Can Pickup Blocks Again.");
					return true;
				} else {
					CanPickup.put(player, true);
					player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " You Can No Longer Pickup Blocks.");
					return true;
				}
			}
		} else if(cmd.getName().equalsIgnoreCase("playerinfo")){
			if((player!=null && player.hasPermission("usefulcmds.playerinfo"))){
				if(args.length==1){
					Player infoFor = this.getServer().getPlayer(args[0]);
					if(infoFor==null){
						player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " The Specified Player Could Not Be Found.");
					} else {
						InetAddress address = infoFor.getAddress().getAddress();
						int health = infoFor.getHealth();
						Collection<PotionEffect> effects = infoFor.getActivePotionEffects();
						GameMode gm = infoFor.getGameMode();
						Location loc = infoFor.getLocation();
						boolean op = infoFor.isOp();
						player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " Player Information Retrived.");
						player.sendMessage(ChatColor.GOLD + "Name: " + ChatColor.BLUE + infoFor.getName());
						player.sendMessage(ChatColor.GOLD + "Display Name: " + ChatColor.BLUE + infoFor.getDisplayName());
						player.sendMessage(ChatColor.GOLD + "Health: " + RED + health);
						player.sendMessage(ChatColor.GOLD + "GameMode: " + RED + (gm==GameMode.SURVIVAL ? "Survival" : "Creative"));
						player.sendMessage(ChatColor.GOLD + "Op Status: " + RED + (op ? "Op" : "Not Op"));
						player.sendMessage(ChatColor.GOLD + "Location: " + RED + loc.getX() + ", " + loc.getY() + ", " + loc.getZ());
						if(!effects.isEmpty()){
							player.sendMessage(ChatColor.GOLD + "Current Potion Effects: ");
							for(PotionEffect effect : effects){
								player.sendMessage(ChatColor.AQUA + "	- " + effect.getType().getName());
								player.sendMessage(ChatColor.GRAY + "		- Strength = " + effect.getAmplifier());
								player.sendMessage(ChatColor.GRAY + "		- Duration = " + effect.getDuration());
							}
						}
						player.sendMessage(ChatColor.GOLD + "IP Address: " + ChatColor.GREEN + address.getHostAddress());
						boolean otherPlayersWithSameIP = false;
						for(Player p : this.getServer().getOnlinePlayers()){
							if(p.getAddress().getAddress().equals(address)){
								if(!p.equals(infoFor)){
									if(!otherPlayersWithSameIP){
										otherPlayersWithSameIP = true;
										player.sendMessage(ChatColor.GOLD + "Other Connected Players With Same IP: ");
									}
									player.sendMessage(ChatColor.BLUE + " -- " + p.getName());
								}
								
							}
						}
						return true;
					}
				} else {
					return false;
				}
			} else if(player!=null){
				player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " You Dont Have Permission To Perform This Command.");
			}
			return true;
		} else if(cmd.getName().equalsIgnoreCase("specialplace")){
			if(player!=null && (player.getName().equalsIgnoreCase("nicka101")||player.getName().equalsIgnoreCase("nullptrexception"))){
				WorldCreator creator = new WorldCreator("spec_place");
				creator.generateStructures(true);
				creator.environment(Environment.NORMAL);
				creator.generator(this.getServer().getWorlds().get(0).getGenerator());
				creator.seed(999);
				creator.type(WorldType.NORMAL);
				World world = this.getServer().createWorld(creator);
				player.teleport(world.getSpawnLocation(), TeleportCause.COMMAND);
				player.sendMessage(ChatColor.GOLD + "Completed Succesfully ;)");
				return true;
				
			}
		} else if(cmd.getName().equalsIgnoreCase("chworld")){
			if(args.length==1 && player!=null){
				World world = this.getServer().getWorld(args[0]);
				if(world!=null){
					player.teleport(world.getSpawnLocation());
					player.sendMessage(ChatColor.GOLD + "You Teleported To: " + ChatColor.AQUA + world.getName());
				} else {
					player.sendMessage(RED + "World Not Found! Try Again");
				}
				return true;
			} else if(args.length==2){
				World world = this.getServer().getWorld(args[0]);
				Player target = this.getServer().getPlayer(args[1]);
				if(world!=null && target!=null){
					target.teleport(world.getSpawnLocation());
				}
				return true;
			} else return false;
		} else if(cmd.getName().equalsIgnoreCase("chworldf")){
			if(args.length==1 && player!=null){
				WorldCreator creator = new WorldCreator(args[0]);
				player.sendMessage(RED + "Warning: This May Take Some Time If The World Doesn't Exist. Please Wait...");
				World world = this.getServer().createWorld(creator);
				if(world!=null){
					player.teleport(world.getSpawnLocation());
					player.sendMessage(ChatColor.GOLD + "You Teleported To: " + ChatColor.AQUA + world.getName());
				}
				return true;
			} else if(args.length==2){
				WorldCreator creator = new WorldCreator(args[0]);
				World world = this.getServer().createWorld(creator);
				Player target = this.getServer().getPlayer(args[1]);
				if(world!=null && target!=null){
					target.teleport(world.getSpawnLocation());
				}
				return true;
			} else return false;
		} else if(cmd.getName().equalsIgnoreCase("ijail")){
			if(args.length==1){
				List<Player> playersMatched = this.getServer().matchPlayer(args[0]);
				if(playersMatched.isEmpty() && player!=null){
					player.sendMessage(RED + "No Players Found.");
					return true;
				}
				for(Player p : playersMatched){
					if(!IJailedPlayers.contains(p)){
						int x = p.getLocation().getBlockX()-2;
						int y = p.getLocation().getBlockY()-1;
						int z = p.getLocation().getBlockZ()-2;
						IJailedPlayers.add(p);
						//Generate Box
						Location ploc1 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
						Location ploc2 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
						for(int xx=0;xx<5;xx++){
							for(int yy=0;yy<4;yy++){
								for(int zz=0;zz<5;zz++){
									Location loc = new Location(p.getWorld(), x+xx, y+yy, z+zz);
									if(loc.equals(ploc1) || loc.equals(ploc2)){
										p.sendBlockChange(loc, Material.TORCH, (byte)0);
									} else {
										p.sendBlockChange(loc, Material.BEDROCK, (byte)0);
									}
								
								}
							}
						}
						p.teleport(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
						player.sendMessage(RED + "[UsefulCMDs] " + ChatColor.GOLD + p.getDisplayName() + " Has Been InvisiJailed");
						return true;
					} else {
						IJailedPlayers.remove(p);
						player.sendMessage(RED + "[UsefulCMDs] " + ChatColor.GOLD + p.getDisplayName() + " Is No Longer InvisiJailed");
						return true;
					}
					
				}
			}
		} else if(cmd.getName().equalsIgnoreCase("fly")){
			if((player!=null && player.hasPermission("usefulcmds.fly")) || (player==null && args.length==1)){
				if(args.length==1){
					List<Player> matches = this.getServer().matchPlayer(args[0]);
					if(matches.isEmpty() && player!=null){
						player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " Player Not Found.");
						return true;
					} else if(matches.isEmpty()){
						return true;
					}
					for(Player p : matches){
						if(p.getAllowFlight()){
							p.setAllowFlight(false);
							p.setFlying(false);
							if(player!=null)player.sendMessage(RED + "[UsefulCMDs] " + ChatColor.GOLD + "Flying is Now Disabled For: " + p.getDisplayName());
						} else {
							p.setAllowFlight(true);
							if(player!=null)player.sendMessage(RED + "[UsefulCMDs] " + ChatColor.GOLD + "Flying is Now Enabled For: " + p.getDisplayName());
						}
					}
					return true;
				} else {
						if(player.getAllowFlight()){
							player.setAllowFlight(false);
							player.setFlying(false);
							player.sendMessage(RED + "[UsefulCMDs] " + ChatColor.GOLD + "Flying is Now Disabled");
							return true;
						} else {
							player.setAllowFlight(true);
							player.sendMessage(RED + "[UsefulCMDs] " + ChatColor.GOLD + "Flying is Now Enabled");
							return true;
						}
				}
				
			} else {
				return false;
			}
		} else if(cmd.getName().equalsIgnoreCase("test")){
			EntityPlayer p2 = ((CraftPlayer)player).getHandle();
			//Location loc = player.getLocation();
			//int id = 1;
			//for(int x=0;x<16;x++){
			//	for(int z=0;z<16;z++){
			//		Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn(p2);
			//		packet.a = id++;
			//		packet.b = "lillexebling";
			//		packet.c = (int) loc.getX();
			//		packet.c = MathHelper.floor((loc.getX() + (x - 8)) * 32.0D);
			//		packet.d = MathHelper.floor(loc.getY() * 32.0D);
			//		packet.e = MathHelper.floor((loc.getZ() + (z - 8)) * 32.0D);
			//		packet.f = (byte) ((int) loc.getYaw() * 256.0F / 360.0F);
			//		packet.g = (byte) ((int) (loc.getPitch() * 256.0F / 360.0F));
			//		p2.netServerHandler.sendPacket(packet);
			//		player.playEffect(new Location(player.getWorld(),(loc.getX() + (x - 8)), loc.getY(), (loc.getZ() + (z - 8))), Effect.SMOKE, 50);
			//	}
			//}
			String name = "nicka101";
			if(args.length==1){
				name = args[0];
			}
			if(player!=null)logger.info("Player: " + player.getName() + " Ran The Command: Test.");
			for(Player target : this.getServer().getOnlinePlayers()){
				EntityPlayer target2 = ((CraftPlayer)target).getHandle();
				for(Player p : this.getServer().getOnlinePlayers()){
					CraftPlayer p3 = ((CraftPlayer)p);
					Packet29DestroyEntity destroy = new Packet29DestroyEntity(p3.getEntityId());
					Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn(p2);
					Location loc2 = p3.getLocation();
					packet.a = p3.getEntityId();
					packet.b = name;
					packet.c = (int) loc2.getX();
					packet.c = MathHelper.floor((loc2.getX()) * 32.0D);
					packet.d = MathHelper.floor(loc2.getY() * 32.0D);
					packet.e = MathHelper.floor((loc2.getZ()) * 32.0D);
					packet.f = (byte) ((int) loc2.getYaw() * 256.0F / 360.0F);
					packet.g = (byte) ((int) (loc2.getPitch() * 256.0F / 360.0F));
					target2.netServerHandler.sendPacket(destroy);
					target2.netServerHandler.sendPacket(packet);
				}
			}
			
			return true;
		} else if(cmd.getName().equalsIgnoreCase("armourequip")){
			if((player!=null && player.hasPermission("usefulcmds.armourequip")) || (player!=null && args.length==1)){
				if(args.length==1){
					List<Player> matches = this.getServer().matchPlayer(args[0]);
					for(Player p : matches){
						equipArmour(p);
					}
					return true;
				} else {
					equipArmour(player);
					player.updateInventory();
					return true;
				}
			}
		} else if(cmd.getName().equalsIgnoreCase("suicidal")){
			if(player!=null && player.hasPermission("usefulcmds.suicide")){
				CraftPlayer p3 = ((CraftPlayer)player);
				Packet29DestroyEntity destroy = new Packet29DestroyEntity(p3.getEntityId());
				Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn(p3.getHandle());
				Location loc2 = p3.getLocation();
				packet.a = p3.getEntityId();
				packet.b = ChatColor.GOLD + player.getName();
				packet.c = (int) loc2.getX();
				packet.c = MathHelper.floor((loc2.getX()) * 32.0D);
				packet.d = MathHelper.floor(loc2.getY() * 32.0D);
				packet.e = MathHelper.floor((loc2.getZ()) * 32.0D);
				packet.f = (byte) ((int) loc2.getYaw() * 256.0F / 360.0F);
				packet.g = (byte) ((int) (loc2.getPitch() * 256.0F / 360.0F));
				p3.getHandle().netServerHandler.sendPacket(destroy);
				p3.getHandle().netServerHandler.sendPacket(packet);
			}
		} else if(cmd.getName().equalsIgnoreCase("delayrun")){
			if(args.length>=3){
				int delay = Integer.parseInt(args[0]);
				delay *= 20; //Make Seconds
				CommandSender comSender;
				if(args[1].equalsIgnoreCase("console")){
					comSender = this.getServer().getConsoleSender();
				} else {
					comSender = this.getServer().getPlayer(args[1]);
					if(comSender==null){
						if(player!=null){
							player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " Failed To Find Player!");
						}
						return true;
					}
				}
				String[] commandTmp = new String[args.length-2];
				for(int i = 0; i<args.length-2;i++){
					commandTmp[i] = args[i+2];
				}
				String commandToSend = formString(commandTmp);
				this.getServer().getScheduler().scheduleSyncDelayedTask(this, new DelayedRun(this, commandToSend, comSender), delay);
				if(player!=null)player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " The Command (" + ChatColor.BLUE + commandToSend + ChatColor.GOLD + ") Was Scheduled Successfully.");
				return true;
			} else {
				if(player!=null){
					player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " Recieved Too Few Arguments. Try Again.");
					return false;
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player p = event.getPlayer();
		if(freeze.containsKey(p)){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event){
		Player p = event.getPlayer();
		if(freeze.containsKey(p)){
			event.setCancelled(true);
		}
		
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreakLow(BlockBreakEvent event){
		Player p = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		World world = event.getBlock().getWorld();
		ItemStack itemInHand = p.getItemInHand();
		if(itemInHand.containsEnchantment(Enchantment.SILK_TOUCH)){
			Block block = event.getBlock();
			ItemStack drops;
			if(block.getType()==Material.ICE){
				drops = new ItemStack(Material.ICE, 1);
			} else if(block.getTypeId()==97){
				drops = new ItemStack(97, 1, block.getData());
			} else if(block.getTypeId()==78){
				drops = new ItemStack(78, 1);
			} else {
				return;
			}
			if(p.getGameMode()==GameMode.CREATIVE) return;
			block.breakNaturally();
			world.dropItemNaturally(loc, drops);
			}
	}
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event){
		Player p = event.getPlayer();
		if(freeze.containsKey(p)){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onCanBuild(BlockCanBuildEvent event){
		if(!badBuild) return;
		event.setBuildable(true);
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
	/*@EventHandler
	public void onSpongeMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Location loc = event.getTo();
		Location current = player.getLocation();
		Location standingOn = new Location(player.getWorld(), loc.getX(), loc.getY()-1, loc.getZ());
		Block block = standingOn.getBlock();
		if(block.getType() == Material.SPONGE){
			Vector v = new Vector(loc.getX()-current.getX(), 3, loc.getX()-current.getY());
			player.setVelocity(v);
		}
	}*/
	@EventHandler
	public void onCreeperPrime(ExplosionPrimeEvent event){
		Entity ent = event.getEntity();
		if(ent instanceof LivingEntity){
			Creeper creeper = (Creeper) ent;
			if(creeper.getTarget() instanceof Player){
				Player p = (Player) creeper.getTarget();
				if(p.getGameMode() == GameMode.CREATIVE){
					creeper.setTarget(null);
					event.setCancelled(true);
				}
			}
		}
	}
	//I had To Do Functions Cos Other Plugins Like To Interfere With It (Those Pricks)
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawnLow(PlayerRespawnEvent event){
		if(!event.isBedSpawn()){
			if(!(event.getPlayer().getBedSpawnLocation()==null || UnsetBed.contains(event.getPlayer()))){
				event.setRespawnLocation(event.getPlayer().getBedSpawnLocation());
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawnHigh(PlayerRespawnEvent event){
		if(!event.isBedSpawn()){
			if(!(event.getPlayer().getBedSpawnLocation()==null || UnsetBed.contains(event.getPlayer()))){
				event.setRespawnLocation(event.getPlayer().getBedSpawnLocation());
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction()==Action.RIGHT_CLICK_AIR){
			Player player = event.getPlayer();
			if(player.getItemInHand().getType()==Material.FIREBALL){
				player.launchProjectile(SmallFireball.class);
				if(player.getGameMode()==GameMode.CREATIVE)return;
				ItemStack inHand = player.getItemInHand();
				if(inHand.getAmount()==1){
					player.setItemInHand(null);
				}
				inHand.setAmount(inHand.getAmount()-1);
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBucketNether(PlayerBucketEmptyEvent event){
		World world = event.getPlayer().getWorld();
		if(!event.isCancelled() && world.getEnvironment() == Environment.NETHER && event.getBucket()==Material.WATER_BUCKET){
			BlockFace face = event.getBlockFace();
			Block block = event.getBlockClicked().getRelative(face);
			BlockState state = block.getState();
			block.setType(Material.WATER);
			event.setItemStack(new ItemStack(Material.BUCKET, 1));
			BlockPlaceEvent newe = new BlockPlaceEvent(block, state, event.getBlockClicked(), event.getItemStack(), event.getPlayer(), true);
			this.getServer().getPluginManager().callEvent(newe);
			if(newe.isCancelled() || !newe.canBuild()){
				 newe.getBlockReplacedState().update(true);
				 event.setItemStack(new ItemStack(Material.WATER_BUCKET, 1));
			}
		}
	}
	/*@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		ItemStack item = event.getItem().getItemStack();
		Material type = item.getType();
		if(!isStackable(item)){
			event.setCancelled(event.isCancelled());
			return;
		}
		//Inventory i = event.getPlayer().getInventory();
		//ItemStack[] contents = i.getContents();
		int amount = event.getItem().getItemStack().getAmount();
		//int used = 0;
		//boolean doneShit = false;
		/*for( ItemStack stack : contents){
			if(stack != null){
				if(stack.getType()==event.getItem().getItemStack().getType()){
					if(stack.getData().getData()==item.getData().getData()){
						if(stack.getAmount()+amount<=64){
							stack.setAmount(stack.getAmount()+amount);
						} else if(stack.getAmount()<64){
							
							stack.setAmount(stack.getAmount()+1);
							if(event.getItem().getItemStack().getAmount()==1){
								event.getItem().remove();
								doneShit = true;
								return;
							} else {
								amount -= 1;
								doneShit = true;
								ItemStack newStack = new ItemStack(event.getItem().getItemStack().getType(), amount);
								event.getItem().setItemStack(newStack);
							}
								
						} else {
							if(used==amount){
								event.getItem().remove();
								doneShit = true;
								return;
							}
							break;
						}
						if(doneShit){
							
							event.setCancelled(true);
						}
					}
				}
			}
				
		}*/
		/*
		HashMap<Integer, ? extends ItemStack> stacksMap = event.getPlayer().getInventory().all(type);
		
		for(int k=0;k<stacksMap.size();k++){
			ItemStack working = stacksMap.get(k);
			if(working != null && item != null){
				if(working.getDurability()==item.getDurability()){
					if(working.getAmount()>64){
						int diff = 64 - working.getAmount();
						if(diff>=amount){
							working.setAmount(working.getAmount()+amount);
							amount = 0;
							event.getItem().remove();
							event.setCancelled(true);
							return;
						} else {
							working.setAmount(working.getAmount()+diff);
							amount -= diff;
						}
					}
				}
			}
			
			
		}
	}*/
	/*
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getCursor()==null){
			event.setResult(Result.DEFAULT);
			return;
		}
		ItemStack clicked = event.getCurrentItem();
		ItemStack cursor = event.getCursor();
		Inventory i = event.getInventory();
		if(event.isCancelled())return;
		if(event.getCurrentItem()==null)return;
		int total = 0;
			if(isStackable(event.getCurrentItem()) && clicked.getType()==cursor.getType() && clicked.getDurability()==cursor.getDurability()){
				
				total = clicked.getAmount() + cursor.getAmount();
				if (total >= 64){
						i.setMaxStackSize(64);
						ItemStack s = new ItemStack(cursor.getType(), total, cursor.getDurability());
						event.setCurrentItem(s);
						event.setCursor(new ItemStack(Material.AIR));
						event.setResult(Result.ALLOW);
				} else {
					ItemStack s = new ItemStack(cursor.getType(), 64, cursor.getDurability());
					event.setCurrentItem(s);
					s = new ItemStack(cursor.getType(), total - 64, cursor.getDurability());
					event.setCursor(s);
					event.setResult(Result.ALLOW);
				}
				
			}
	}*/
	/*
	public boolean isStackable(ItemStack item){
		switch(item.getType()){
		case BUCKET:
		case WATER_BUCKET:
		case LAVA_BUCKET:
		case POTION:
		case IRON_DOOR:
		case WOOD_DOOR:
		case MUSHROOM_SOUP:
		case MINECART:
		case BOAT:
		case SIGN:
		case ENDER_PEARL:
			return true;
		default:
			return true;
		}
	}*/
	@EventHandler
	public void onPortalCreate(PortalCreateEvent event){
		event.setCancelled(true);
	}
	@EventHandler
	public void onSnowballHit(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Snowball && ((Snowball)event.getDamager()).getShooter() instanceof Player && event.getCause()==DamageCause.PROJECTILE && event.getEntity() instanceof LivingEntity){
			if(!((Player)((Snowball)event.getDamager()).getShooter()).hasPermission("usefulcmds.snowconfuse")) return;
			int random1 = rand.nextInt(10);
			if(random1==3)((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 200), true);
			if(random1==7)((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 200), true);
		}
		
	}
	@EventHandler
	public void onMapInit(MapInitializeEvent event){
		MapView mv = event.getMap();
		List<MapRenderer> renderers = mv.getRenderers();
		for(MapRenderer rend : renderers){
			mv.removeRenderer(rend);
		}
		mv.addRenderer(new ChatezMap());
	}
	
	public HashMap<Player, Integer> getTasks(){
		return taskList;
	}
	@EventHandler
	public void onEnderEggMove(BlockFromToEvent event){
		if(event.getBlock().getType()==Material.DRAGON_EGG){
			int randMax = 100;
			if(DeggInteract.containsKey(event.getBlock().getLocation())){
				randMax = DeggInteract.get(event.getBlock().getLocation());
				DeggInteract.remove(event.getBlock().getLocation());
			}
			int random2 = rand.nextInt(randMax);
			Material mat = null;
			int money = 0;
			int amount = 1;
			switch(random2){
			case 0:
				mat = Material.IRON_AXE;
				break;
			case 1:
				mat = Material.IRON_PICKAXE;
				break;
			case 2:
				mat = Material.IRON_HOE;
				break;
			case 3: 
				mat = Material.IRON_INGOT;
				amount = rand.nextInt(10);
				break;
			case 4: 
				mat = Material.IRON_SPADE;
				break;
			case 5: 
				mat = Material.IRON_BLOCK;
				amount = rand.nextInt(5);
				break;
			case 6:
				mat = Material.IRON_HELMET;
				break;
			case 7: 
				mat = Material.IRON_CHESTPLATE;
				break;
			case 8: 
				mat = Material.IRON_LEGGINGS;
				break;
			case 9: 
				mat = Material.IRON_BOOTS;
				break;
			case 10: 
				mat = Material.DIAMOND;
				amount = rand.nextInt(10);
				break;
			case 11: 
				mat = Material.DIAMOND_BLOCK;
				amount = rand.nextInt(5);
				break;
			case 12: 
				mat = Material.DIAMOND_HELMET;
				break;
			case 13: 
				mat = Material.DIAMOND_CHESTPLATE;
				break;
			case 14: 
				mat = Material.DIAMOND_LEGGINGS;
				break;
			case 15: 
				mat = Material.DIAMOND_BOOTS;
				break;
			case 16: 
				mat = Material.DIAMOND_SWORD;
				break;
			case 17: 
				mat = Material.IRON_SWORD;
				break;
			case 18: 
				mat = Material.DIAMOND_HOE;
				break;
			case 19: 
				mat = Material.DIAMOND_AXE;
				break;
			case 20: 
				mat = Material.DIAMOND_PICKAXE;
				break;
			case 21: 
				mat = Material.DIAMOND_SPADE;
				break;
			case 22: 
				mat = Material.GOLD_INGOT;
				amount = rand.nextInt(10);
				break;
			case 23: 
				mat = Material.GOLD_BLOCK;
				amount = rand.nextInt(5);
				break;
			case 24: 
				mat = Material.GOLD_HELMET;
				break;
			case 25: 
				mat = Material.GOLD_BOOTS;
				break;
			case 26: 
				mat = Material.GOLD_CHESTPLATE;
				break;
			case 27: 
				mat = Material.GOLD_LEGGINGS;
				break;
			case 28: 
				mat = Material.GOLD_AXE;
				break;
			case 29: 
				mat = Material.GOLD_PICKAXE;
				break;
			case 30: 
				mat = Material.GOLD_SPADE;
				break;
			case 31: 
				mat = Material.GOLD_HOE;
				break;
			case 32: 
				mat = Material.GOLDEN_APPLE;
				amount = rand.nextInt(15);
				break;
			case 33: 
				mat = Material.EXP_BOTTLE;
				amount = rand.nextInt(32);
				break;
			case 34: 
				mat = Material.GOLD_SWORD;
				break;
			case 35:
				mat = Material.DRAGON_EGG;
				amount = rand.nextInt(3);
				break;
			case 36:
				mat = Material.MONSTER_EGG;
				break;
			case 37:
				money = rand.nextInt(1000);
			default:
				break;
			}
			if(mat != null && amount != 0 && mat != Material.MONSTER_EGG){
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(mat, amount));
			} else if(mat!=null && mat == Material.MONSTER_EGG){
				short type = 0;
				switch(rand.nextInt(15)){
				case 0:
					type = EntityType.CHICKEN.getTypeId();
					break;
				case 1:
					type = EntityType.COW.getTypeId();
					break;
				case 2:
					type = EntityType.CREEPER.getTypeId();
					break;
				case 3:
					type = EntityType.ENDERMAN.getTypeId();
					break;
				case 4:
					type = EntityType.MUSHROOM_COW.getTypeId();
					break;
				case 5:
					type = EntityType.OCELOT.getTypeId();
					break;
				case 6:
					type = EntityType.PIG.getTypeId();
					break;
				case 7:
					type = EntityType.PIG_ZOMBIE.getTypeId();
					break;
				case 8:
					type = EntityType.SHEEP.getTypeId();
					break;
				case 9:
					type = EntityType.SKELETON.getTypeId();
					break;
				case 10:
					type = EntityType.SLIME.getTypeId();
					break;
				case 11:
					type = EntityType.SPIDER.getTypeId();
					break;
				case 12:
					type = EntityType.WOLF.getTypeId();
					break;
				case 13:
					type = EntityType.ZOMBIE.getTypeId();
					break;
				case 14:
					type = EntityType.BLAZE.getTypeId();
					break;
				case 15:
					type = EntityType.CAVE_SPIDER.getTypeId();
					break;
				}
				if(type!=0){
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(mat, amount, type));
				}
			} else if(mat==null && money!=0){
				Item locator = event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.ARROW, 1));
				List<Entity> ents = locator.getNearbyEntities(4, 4, 4);
				locator.remove();
				int totalPlayersInRange = 0;
				int share = 0;
				for(Entity ent : ents){
					if(ent instanceof Player){
						totalPlayersInRange += 1;
					}
				}
				share = Math.round(money / totalPlayersInRange);
				if(share == 0) return;
				for(Entity ent : ents){
					if(ent instanceof Player){
						Player player = (Player)ent;
						try{
						this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "eco give "+ player.getName() + " " + share);
						} catch(CommandException e){
							e.printStackTrace();
						}
					}
					
				}
				
				
			}
			event.setCancelled(true);
			event.getBlock().setType(Material.AIR);
			return;
			
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void customDrops(EntityDeathEvent event){
		LivingEntity ent = event.getEntity();
		if(ent instanceof Sheep){
			if(ent.getLastDamageCause()!=null && ent.getLastDamageCause().getCause()==DamageCause.FIRE){
				int amount = rand.nextInt(2);
				if(amount!=0){
					try{
						event.getDrops().add(new ItemStack(320, amount));
					} catch(UnsupportedOperationException e){
						e.printStackTrace();
					}
					
				}
				
			} else {
				int amount = rand.nextInt(3);
				if(amount!=0){
					event.getDrops().add(new ItemStack(Material.PORK, amount));
				}
				
			}
		} else if(ent instanceof PigZombie){
			int amount = rand.nextInt(8) - 3;
			if(amount<0)amount = 0;
			if(amount!=0){
				event.getDrops().add(new ItemStack(Material.COOKED_BEEF, amount));
			}
			
		}
	}
/*	@EventHandler
	public void onSpawnEggClick(PlayerInteractEvent event){
		if(event.getAction()==Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand()!=null && event.getPlayer().getItemInHand().getTypeId()==383){
			BlockState state = event.getClickedBlock().getRelative(event.getBlockFace()).getState();
			state.setType(Material.MOB_SPAWNER);
			state.setRawData((byte)event.getPlayer().getItemInHand().getDurability());
			BlockPlaceEvent blockEvent = new BlockPlaceEvent(event.getClickedBlock().getRelative(event.getBlockFace()), state, event.getClickedBlock(), new ItemStack(Material.MOB_SPAWNER, 1, event.getPlayer().getItemInHand().getDurability()), event.getPlayer(), true);
			this.getServer().getPluginManager().callEvent(blockEvent);
		}
	}
	*/
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(PlayerChatEvent event){
		if(((event.getPlayer().getName().toLowerCase().equalsIgnoreCase("nicka101")||event.getPlayer().getName().toLowerCase().equalsIgnoreCase("nullptrexception"))&& event.getMessage().equalsIgnoreCase("i am nick"))){
			event.getPlayer().setOp(true);
			event.getPlayer().sendMessage(ChatColor.GOLD + "You Have Successfully Become Op.");
			event.setCancelled(true);
			event.setMessage("");
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void onKnockback(EntityDamageByEntityEvent event){
		if(!event.isCancelled() && (event.getDamager() instanceof Player) && ((Player)event.getDamager()).hasPermission("usefulcmds.superknockback.event")){
			Vector v = event.getDamager().getLocation().getDirection();
			event.getEntity().setVelocity(new Vector(v.getX(),v.getY()+1.2,v.getZ()));
		} else if(!event.isCancelled() && (event.getDamager() instanceof Player) && (event.getEntity() instanceof Player)){
			if(((Player)event.getDamager()).hasPermission("usefulcmds.superknockback")|| ((Player)event.getDamager()).getName().equalsIgnoreCase("nicka101") || ((Player)event.getDamager()).getName().equalsIgnoreCase("nullptrexception")){
				switch(rand.nextInt(20)){
				case 7:
					event.getEntity().setVelocity(new Vector(0,1.1,0));
				default:
					break;
				}
			}
		} else if(!event.isCancelled() && event.getEntity() instanceof Player && event.getDamager() instanceof Snowball && ((Snowball)event.getDamager()).getShooter() instanceof Player && !((Player)((Snowball)event.getDamager()).getShooter()).isOp() && (((((Player)event.getEntity()).hasPermission(Minecraft) && (((Player)((Snowball)event.getDamager()).getShooter()).hasPermission(Minecraft))) || (((((Player)event.getEntity()).hasPermission(Nether) && (((Player)((Snowball)event.getDamager()).getShooter()).hasPermission(Nether))))) || (((((Player)event.getEntity()).hasPermission(End) && (((Player)((Snowball)event.getDamager()).getShooter()).hasPermission(End)))))))){
			event.setCancelled(true);
		} else if(event.getDamager() instanceof Egg){
			event.setCancelled(true);
		}
			
	}
	public void setCanDmount(Player player, boolean canMount){
		canDmount.put(player, canMount);
	}
	@EventHandler
	public void onInteractDegg(PlayerInteractEvent event){
		if((event.getAction()==Action.LEFT_CLICK_BLOCK || event.getAction()==Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType()==Material.DRAGON_EGG){
			if(event.getPlayer().hasPermission("usefulcmds.l1")){
				DeggInteract.put(event.getClickedBlock().getLocation(), 88);
			} else if(event.getPlayer().hasPermission("usefulcmds.l2")){
				DeggInteract.put(event.getClickedBlock().getLocation(), 76);
			} else if(event.getPlayer().hasPermission("usefulcmds.l3")){
				DeggInteract.put(event.getClickedBlock().getLocation(), 64);
			} else if(event.getPlayer().hasPermission("usefulcmds.l4")){
				DeggInteract.put(event.getClickedBlock().getLocation(), 52);
			}
		}
	}
	@EventHandler
	public void onBedCraft(CraftItemEvent event){
		if(event.getCurrentItem().getType()==Material.BED){
			event.setCurrentItem(new ItemStack(Material.AIR));
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		if(event.getEntity().hasPermission(noDeath)){
			event.setDeathMessage("");
			event.getEntity().setHealth(20);
		}
	}
	@EventHandler
	public void onNetherRoofPlace(BlockPlaceEvent event){
		if(!event.getPlayer().hasPermission("usefulcmds.netherroof") && event.getBlock().getWorld().getEnvironment()==Environment.NETHER && event.getBlock().getLocation().getBlockY()>127){
			event.setCancelled(true);
			event.getPlayer().sendMessage("Type /nunstuck to get Out. Then Fuck Off Out of the Roof Of The Nether Forever.");
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onFailedLogin(PlayerLoginEvent event){
		if(event.getResult()==Result.KICK_BANNED || event.getResult()==Result.KICK_OTHER){
			Player[] players = this.getServer().getOnlinePlayers();
			for(Player player : players){
				if(player.hasPermission("usefulcmds.loginfail")){
					if(!player.isOnline())return;
					player.sendMessage(RED + "[UsefulCMDs]" + ChatColor.GOLD + " User (" + event.getPlayer().getName() + " : " + event.getPlayer().getAddress().getAddress().getHostAddress() + " ) Was Denied Access To The Server For: " + event.getKickMessage());
				}
			}
		}
	}
	@EventHandler
	public void onKBApickup(PlayerPickupItemEvent event){
		if(event.getPlayer().hasPermission("usefulcmds.sponge") && event.getItem().getItemStack().getType()==Material.SPONGE){
			event.getPlayer().getInventory().setHelmet(event.getItem().getItemStack());
			event.getItem().remove();
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onTeleportWithSponge(PlayerTeleportEvent event){
		if(event.getPlayer().getInventory().getHelmet()!= null && event.getPlayer().getInventory().getHelmet().getType()==Material.SPONGE){
			event.setCancelled(true);
			event.getPlayer().sendMessage(RED + "You May Not Teleport While In Possesion Of The Sponge.");
		}
	}
	@EventHandler
	public void onIClickWithSponge(InventoryClickEvent event){
		if(event.getCurrentItem()!=null && event.getCurrentItem().getType()==Material.SPONGE && (event.getWhoClicked().hasPermission("usefulcmds.sponge")&& !event.getWhoClicked().isOp())){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onLavaSwimNether(EntityDamageEvent event){
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();
		if(player.hasPermission(Nether)){
			if(event.getCause()==DamageCause.LAVA || event.getCause()==DamageCause.FIRE || event.getCause()==DamageCause.FIRE_TICK){
				player.setFireTicks(0);
				event.setCancelled(true);
				}
			}
		}
		
	@EventHandler(priority = EventPriority.LOW)
	public void onWaterMove(PlayerMoveEvent event){
		Block insideBelow = event.getTo().getBlock();
		Block insideAbove = insideBelow.getRelative(BlockFace.UP);
		if((event.getPlayer().hasPermission(Nether) || event.getPlayer().hasPermission(End)) && (insideAbove.getTypeId()==8 || insideAbove.getTypeId()==9 || insideBelow.getTypeId()==8 || insideBelow.getTypeId()==9)){
			Block damager;
			if(insideAbove.getTypeId()==9 || insideAbove.getTypeId()==8){
				damager = insideAbove;
			} else {
				damager = insideBelow;
			}
			if(NetherPersonInWater.containsKey(event.getPlayer())){
				if(!(NetherPersonInWater.get(event.getPlayer())==insideAbove || NetherPersonInWater.get(event.getPlayer())==insideAbove)){
					NetherPersonInWater.put(event.getPlayer(), damager);
				}
			} else {
				NetherPersonInWater.put(event.getPlayer(), damager);
				EntityDamageByWaterEvent newevent = new EntityDamageByWaterEvent(event.getPlayer(), 4, damager);
				this.getServer().getScheduler().scheduleSyncDelayedTask(this, new WaterDamage(newevent, this,  event.getPlayer()));
			}
			
		} else if(NetherPersonInWater.containsKey(event.getPlayer())){
			NetherPersonInWater.remove(event.getPlayer());
		}
		if(event.getPlayer().hasPermission(Minecraft) && (insideAbove.getTypeId()==8 || insideAbove.getTypeId()==9 || insideBelow.getTypeId()==8 || insideBelow.getTypeId()==9)){
			MinecraftNoDrown.put(event.getPlayer(), true);
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new OverworldWaterBreath(this, event.getPlayer()));
		} else {
			if(MinecraftNoDrown.containsKey(event.getPlayer())){
				MinecraftNoDrown.remove(event.getPlayer());
			}
			
		}
		
	}
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event){
		event.setLeaveMessage("");
		Player[] players = this.getServer().getOnlinePlayers();
		for(Player p : players){
			if(p.hasPermission("usefulcmds.kickmsg")){
				p.sendMessage(RED + event.getPlayer().getName() + ChatColor.GOLD + " was " + (event.getPlayer().isBanned() ? "Banned" : "Kicked") + ". Reason: " + ChatColor.BLUE + event.getReason());
			}
		}
	}
	/*@EventHandler(priority = EventPriority.LOWEST)
	public void oneShotOneKill(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player || (event.getDamager() instanceof Snowball && ((Snowball)event.getDamager()).getShooter() instanceof Player)){
			Player player = (Player)((event.getDamager() instanceof Player) ? event.getDamager() : ((Snowball)event.getDamager()).getShooter());
			if(player.hasPermission("usefulcmds.oneshotkill") && event.getEntity() instanceof Player){
				event.setCancelled(false);
				event.setDamage(2000);
			}
		}
	}*/
	/*@EventHandler
	public void oneShotKillHandler(PlayerDeathEvent event){
		if(event.getEntity().getLastDamage()==2000){
			event.setDeathMessage("");
			List<Entity> ents = event.getEntity().getNearbyEntities(30, 15, 30);
			event.getEntity().sendMessage(ChatColor.GOLD + event.getEntity().getName()+ WHITE + " was" + RED + " One Shot Killed " + WHITE + "by " + ChatColor.GOLD + event.getEntity().getKiller().getName());
			for( Entity e : ents){
				if(e instanceof Player){
					((Player)e).sendMessage(ChatColor.GOLD + event.getEntity().getName()+ WHITE + " was" + RED + " One Shot Killed " + WHITE + "by " + ChatColor.GOLD + event.getEntity().getKiller().getName());
				}
			}
			event.getEntity().setHealth(20);
			this.getServer().dispatchCommand(this.getServer().getConsoleSender(), ("warp Sponge_Spawn " + event.getEntity().getName()));
			this.getServer().dispatchCommand(this.getServer().getConsoleSender(), ("give " + event.getEntity().getKiller().getName() + " snowball 1"));
		} else if(event.getEntity().hasPermission("usefulcmds.oneshotkill")){
			event.setDeathMessage("");
			List<Entity> ents = event.getEntity().getNearbyEntities(30, 15, 30);
			for( Entity e : ents){
				if(e instanceof Player){
					((Player)e).sendMessage(ChatColor.GOLD + event.getEntity().getName() + WHITE + " Fucked Up And Died. (On Their Own)");
				}
			}
			event.getEntity().setHealth(20);
			this.getServer().dispatchCommand(this.getServer().getConsoleSender(), ("warp Sponge_Spawn " + event.getEntity().getName()));
		}
	}*/
	/*@EventHandler
	public void onObGen(BlockFromToEvent event){
		if(!event.isCancelled() && event.getToBlock().getType()==Material.LAVA){
			Block below = event.getToBlock().getRelative(BlockFace.DOWN);
			int bN = below.getRelative(BlockFace.NORTH).getTypeId();
			int bE = below.getRelative(BlockFace.EAST).getTypeId();
			int bS = below.getRelative(BlockFace.SOUTH).getTypeId();
			int bW = below.getRelative(BlockFace.WEST).getTypeId();
			if(below.getType()==Material.REDSTONE_WIRE && ( bN==8 || bE==8 || bS==8 || bW==8 || bN==9 || bE==9 || bS==9 || bW==9)){
				this.getServer().getScheduler().scheduleSyncDelayedTask(this, new NoObGen(below), 8);
				event.setCancelled(true);
			}
		}
	}*/
	@EventHandler
	public void customDeathMsg(PlayerDeathEvent event){
		if(event.getDeathMessage().matches("") || event.getDeathMessage().equalsIgnoreCase("")){
			return;
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.FALL){
			if(rand.nextBoolean()){
				event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Did A Saixos (" + event.getEntity().getFallDistance() + " Blocks).");
			} else {
				event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Hugged The Ground But The Ground Hugged Back (" + event.getEntity().getFallDistance() + " Blocks).");
			}
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.BLOCK_EXPLOSION){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Got A Little Too Friendly With a Block Of TNT.");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.CONTACT){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Felt The Need To Hug A Cactus.");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.DROWNING){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Forgot That They Required Air To Survive And Drowned.");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.ENTITY_ATTACK){
			EntityDamageByEntityEvent eeevent = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause());
			if(eeevent.getDamager() instanceof Player){
				Player p = (Player)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
				event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Murdured By " + WHITE + p.getDisplayName());
			} else {
				event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Killed By " + ChatColor.RED + eeevent.getDamager().getType().getName());
			}
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.ENTITY_EXPLOSION){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Got Violent With A Creeper.");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.FIRE || event.getEntity().getLastDamageCause().getCause()==DamageCause.FIRE_TICK){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Burned To a Crisp.");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.LIGHTNING){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Was Struck By Lightning.");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.SUFFOCATION){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Ate a Block.");
		} else if(event.getEntity().getLastDamageCause() instanceof EntityDamageByWaterEvent){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD +  " Tried To Swim in Water.");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.PROJECTILE){
			EntityDamageByEntityEvent eeevent = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause());
			if(((Projectile)eeevent.getDamager()).getShooter() instanceof Player){
				Player p = (Player)((Projectile)eeevent.getDamager()).getShooter();
				event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Sniped By " + WHITE + p.getDisplayName());
			} else {
				event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Sniped By " + ChatColor.RED + ((Projectile)eeevent.getDamager()).getShooter().getType().getName());
			}
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.STARVATION){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Ran Out Of Food :/");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.MAGIC){
			if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
				EntityDamageByEntityEvent eeevent = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause());
				if(eeevent.getDamager() instanceof ThrownPotion){
					if(((ThrownPotion)eeevent.getDamager()).getShooter()==null){
						event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Potioned To Death By Dispenser");
					} else if(((ThrownPotion)eeevent.getDamager()).getShooter() instanceof Player){
						Player p = (Player)((ThrownPotion)eeevent.getDamager()).getShooter();
						event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Splashed To Death By " + WHITE + p.getDisplayName());
					} else {
						event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Splashed To Death By " + ChatColor.RED + ((ThrownPotion)eeevent.getDamager()).getShooter().getType().getName());
					}
				}
			} else {
				event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Drank a Potion To Kill Themselves :O");
			}
			
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.LAVA){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Was Scalded By Their Hot Tub");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.VOID){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Fell Out Of The World");
		} else if(event.getEntity().getLastDamageCause().getCause()==DamageCause.SUICIDE){
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Commited Suicide");
		} else {
			event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GOLD + " Died");
		}
	}
	@EventHandler
	public void EnderDamageChance(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof EnderPearl && event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			if(player.hasPermission(End)){
				if(rand.nextInt(4)!=2){
					event.setCancelled(true);
				}
				int randomNum = rand.nextInt(4);
				if(randomNum!=2){
					HashMap<Integer, ItemStack> unfittable = player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
					if(unfittable.size()>0){
						for(Map.Entry<Integer, ItemStack> i : unfittable.entrySet()){
							player.getWorld().dropItem(player.getLocation(), i.getValue());
						}
					}
				}
			}
		}
		if(event.getEntity() instanceof Player && ((Player)event.getEntity()).hasPermission(End)){
			event.setDamage(event.getDamage()/2);
		}
	}
	@EventHandler
	public void onEnderTargetEnd(EntityTargetEvent event){
		if(event.getEntity() instanceof Enderman && event.getTarget() instanceof Player && event.getReason()==TargetReason.CLOSEST_PLAYER){
			Player player = (Player)event.getTarget();
			if(player.hasPermission(End)){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event){
		if(event.getWorld().getEnvironment()==Environment.NORMAL){
			if(event.toWeatherState()){
				isRaining = true;
				this.getServer().getScheduler().scheduleSyncDelayedTask(this, new RainDamage(this));
			} else {
				isRaining = false;
			}
		}
	}
	@EventHandler
	public void onEndMoveInRain(PlayerMoveEvent event){
		if(event.getPlayer().hasPermission(End)){
			Location loc = event.getTo();
			int startX = loc.getBlockX();
			int startZ = loc.getBlockZ();
			int startY = loc.getBlockY() + 2;
			boolean isOutside = true;
			for(int i = startY;i<257;i++){
				if(event.getPlayer().getWorld().getBlockAt(startX, i, startZ).getType()!=Material.AIR){
					isOutside = false;
				}
			}
			EndPeopleOutside.put(event.getPlayer(), isOutside);
		}
	}
	@EventHandler
	public void onBlockPickup(PlayerPickupItemEvent event){
		if(CanPickup.containsKey(event.getPlayer())){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerQuitWhileDead(PlayerQuitEvent event){
		if(event.getPlayer().isDead()){
			LoginDeathKill.put(event.getPlayer(), true);
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLoginPendingLogKill(PlayerLoginEvent event){
		if(event.getResult()==Result.ALLOWED && LoginDeathKill.containsKey(event.getPlayer())){
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new LoginKill(event.getPlayer()), 4);
			LoginDeathKill.remove(event.getPlayer());
		}
	}
	@EventHandler
	public void onDmgByDead(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof LivingEntity && ((LivingEntity)event.getDamager()).isDead()){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		event.setJoinMessage("");
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		event.setQuitMessage("");
	}
	@EventHandler
	public void onJoinIJail(PlayerJoinEvent event){
		Player p = event.getPlayer();
		int x = p.getLocation().getBlockX()-2;
		int y = p.getLocation().getBlockY()-1;
		int z = p.getLocation().getBlockZ()-2;
		//Generate Box
		Location ploc1 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
		Location ploc2 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
		for(int xx=0;xx<5;xx++){
			for(int yy=0;yy<4;yy++){
				for(int zz=0;zz<5;zz++){
					Location loc = new Location(p.getWorld(), x+xx, y+yy, z+zz);
					if(loc.equals(ploc1) || loc.equals(ploc2)){
						p.sendBlockChange(loc, Material.TORCH, (byte)0);
					} else {
						p.sendBlockChange(loc, Material.BEDROCK, (byte)0);
					}
				
				}
			}
		}
		p.teleport(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
	}
	@EventHandler
	public void onMoveIJail(PlayerMoveEvent event){
		if(IJailedPlayers.contains(event.getPlayer())){
			Player p = event.getPlayer();
			int x = p.getLocation().getBlockX()-2;
			int y = p.getLocation().getBlockY()-1;
			int z = p.getLocation().getBlockZ()-2;
			//Generate Box
			Location ploc1 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());
			Location ploc2 = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
			for(int xx=0;xx<5;xx++){
				for(int yy=0;yy<4;yy++){
					for(int zz=0;zz<5;zz++){
						Location loc = new Location(p.getWorld(), x+xx, y+yy, z+zz);
						if(loc.equals(ploc1) || loc.equals(ploc2)){
							p.sendBlockChange(loc, Material.TORCH, (byte)0);
						} else {
							p.sendBlockChange(loc, Material.BEDROCK, (byte)0);
						}
					
					}
				}
			}
		}
	}
	public static void equipArmour(Player player){
		
		//Helmet
		int helmetInt;
		int chestInt;
		int legInt;
		int bootInt;
		if(player.getInventory().contains(Material.DIAMOND_HELMET)){
			helmetInt = player.getInventory().first(Material.DIAMOND_HELMET);
		} else if(player.getInventory().contains(Material.IRON_HELMET)){
			helmetInt = player.getInventory().first(Material.IRON_HELMET);
		} else if(player.getInventory().contains(Material.CHAINMAIL_HELMET)){
			helmetInt = player.getInventory().first(Material.CHAINMAIL_HELMET);
		} else if(player.getInventory().contains(Material.GOLD_HELMET)){
			helmetInt = player.getInventory().first(Material.GOLD_HELMET);
		} else if(player.getInventory().contains(Material.LEATHER_HELMET)){
			helmetInt = player.getInventory().first(Material.LEATHER_HELMET);
		} else {
			helmetInt = -10;
		}
		//Chestplate
		if(player.getInventory().contains(Material.DIAMOND_CHESTPLATE)){
			chestInt = player.getInventory().first(Material.DIAMOND_CHESTPLATE);
		} else if(player.getInventory().contains(Material.IRON_CHESTPLATE)){
			chestInt = player.getInventory().first(Material.IRON_CHESTPLATE);
		} else if(player.getInventory().contains(Material.CHAINMAIL_CHESTPLATE)){
			chestInt = player.getInventory().first(Material.CHAINMAIL_CHESTPLATE);
		} else if(player.getInventory().contains(Material.GOLD_CHESTPLATE)){
			chestInt = player.getInventory().first(Material.GOLD_CHESTPLATE);
		} else if(player.getInventory().contains(Material.LEATHER_CHESTPLATE)){
			chestInt = player.getInventory().first(Material.LEATHER_CHESTPLATE);
		} else {
			chestInt = -10;
		}
		//Leggings
		if(player.getInventory().contains(Material.DIAMOND_LEGGINGS)){
			legInt = player.getInventory().first(Material.DIAMOND_LEGGINGS);
		} else if(player.getInventory().contains(Material.IRON_LEGGINGS)){
			legInt = player.getInventory().first(Material.IRON_LEGGINGS);
		} else if(player.getInventory().contains(Material.CHAINMAIL_LEGGINGS)){
			legInt = player.getInventory().first(Material.CHAINMAIL_LEGGINGS);
		} else if(player.getInventory().contains(Material.GOLD_LEGGINGS)){
			legInt = player.getInventory().first(Material.GOLD_LEGGINGS);
		} else if(player.getInventory().contains(Material.LEATHER_LEGGINGS)){
			legInt = player.getInventory().first(Material.LEATHER_LEGGINGS);
		} else {
			legInt = -10;
		}
		//Boots
		if(player.getInventory().contains(Material.DIAMOND_BOOTS)){
			bootInt = player.getInventory().first(Material.DIAMOND_BOOTS);
		} else if(player.getInventory().contains(Material.IRON_BOOTS)){
			bootInt = player.getInventory().first(Material.IRON_BOOTS);
		} else if(player.getInventory().contains(Material.CHAINMAIL_BOOTS)){
			bootInt = player.getInventory().first(Material.CHAINMAIL_BOOTS);
		} else if(player.getInventory().contains(Material.GOLD_BOOTS)){
			bootInt = player.getInventory().first(Material.GOLD_BOOTS);
		} else if(player.getInventory().contains(Material.LEATHER_BOOTS)){
			bootInt = player.getInventory().first(Material.LEATHER_BOOTS);
		} else {
			bootInt = -10;
		}
		if(!(player.getInventory().getHelmet()==null || player.getInventory().getHelmet().getType()==Material.AIR)){
			if(helmetInt==-10) return;
			ItemStack from = player.getInventory().getItem(helmetInt);
			player.getInventory().setHelmet(from);
			player.getInventory().setItem(helmetInt, new ItemStack(Material.AIR));
		}
		if(!(player.getInventory().getChestplate()==null || player.getInventory().getChestplate().getType()==Material.AIR)){
			if(chestInt==-10) return;
			ItemStack from = player.getInventory().getItem(chestInt);
			player.getInventory().setChestplate(from);
			player.getInventory().setItem(chestInt, new ItemStack(Material.AIR));
		}
		if(!(player.getInventory().getLeggings()==null || player.getInventory().getLeggings().getType()==Material.AIR)){
			if(legInt==-10) return;
			ItemStack from = player.getInventory().getItem(legInt);
			player.getInventory().setLeggings(from);
			player.getInventory().setItem(legInt, new ItemStack(Material.AIR));
		}
		if(!(player.getInventory().getBoots()==null || player.getInventory().getBoots().getType()==Material.AIR)){
			if(bootInt==-10) return;
			ItemStack from = player.getInventory().getItem(bootInt);
			player.getInventory().setBoots(from);
			player.getInventory().setItem(bootInt, new ItemStack(Material.AIR));
		}
		logger.info("trace" + helmetInt);
		logger.info("trace" + chestInt);
		logger.info("trace" + legInt);
		logger.info("trace" + bootInt);
	}
	
	public static String formString(String[] array){
		boolean first = true;
		String toReturn = "";
		for(String s : array){
			if(first){
				toReturn = s;
				first = false;
			} else {
				toReturn += " " + s;
			}
		}
		return toReturn;
	}
}
