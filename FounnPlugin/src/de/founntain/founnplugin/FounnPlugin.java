package de.founntain.founnplugin;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.founntain.founnplugin.classes.*;
import de.founntain.founnplugin.commands.*;
import de.founntain.founnplugin.events.*;
import de.founntain.founnplugin.recipes.*;

public class FounnPlugin extends JavaPlugin {
	
	private Server server;
	private String consolePrefix =  ChatColor.WHITE + "[" + ChatColor.BLUE +"FounnPlugin" + ChatColor.WHITE + "] ";
	public static UUID founntainUUID = UUID.fromString("1f146f64-96fb-400c-971a-8d68e7d96b69");
	public static PlayerStatsManager playerStatsManager;
	
	public FounnPlugin() {
		this.server = this.getServer();
		
		playerStatsManager = new PlayerStatsManager();
		DeathCoord.deathCoords = new HashMap<UUID, DeathCoord>();
		DeathItems.items = new HashMap<UUID, ItemStack[]>();
		BedMap.usersInBeds = new HashMap<UUID, Boolean>();
		PlayerTeleportCoords.playerTeleportCoords = new HashMap<UUID, Location>();
	}
	
	@Override
	public void onEnable() {		
		//Registering Events
		this.sendConsoleMessage(ChatColor.YELLOW + "registering events");
		
		this.registerEvents();
		
		this.sendConsoleMessage(ChatColor.GREEN + "finished registering events");
		
		//Registering Commands
		this.sendConsoleMessage(ChatColor.YELLOW + "registering commands");
		
		this.registerCommands();
		
		this.sendConsoleMessage(ChatColor.GREEN +  "finished registering commands");
	}
	
	@Override
	public void onDisable() { }	
	
	@Override
	public void onLoad() {
		//Registering Recipes
		this.sendConsoleMessage(ChatColor.YELLOW + "registering recipes");
		
		this.registerRecipies();
		
		this.sendConsoleMessage(ChatColor.GREEN +  "finished registering recipes");
		
		//Loading custom worlds
		this.sendConsoleMessage(ChatColor.YELLOW + "loading created worlds");
		
		this.loadingCustomWorlds();
		
		this.sendConsoleMessage(ChatColor.GREEN +  "finished loading worlds");
	}
	
	private void sendConsoleMessage(String msg) {
		this.server.getConsoleSender().sendMessage(this.consolePrefix + msg);
	}
	
	private void registerEvents() {
		//Async Events
		this.registerEvent(new OnAsyncPlayerChatEvent());
		
		//Player Events
		this.registerEvent(new OnPlayerDeathEvent());
		this.registerEvent(new OnPlayerJoinEvent());
		this.registerEvent(new OnPlayerQuitEvent());
		this.registerEvent(new OnPlayerChangedWorldEvent());
		this.registerEvent(new OnPlayerBedEnterEvent());
		this.registerEvent(new OnPlayerItemBreakEvent());
		this.registerEvent(new OnPlayerTeleportEvent());
		this.registerEvent(new OnPlayerInteractEvent(this));
		
		//Entity Event
		this.registerEvent(new OnEntityDamageByEntityEvent());
		this.registerEvent(new OnEntityDeathEvent());
		
		//Inventory Events
		this.registerEvent(new OnInventoryClickEvent());
		this.registerEvent(new OnInventoryCloseEvent());
		
		//Craft Events
		this.registerEvent(new OnCraftItemEvent());
		
		//Block Events
		this.registerEvent(new OnBlockBreakEvent());
		this.registerEvent(new OnBlockPlaceEvent());
		
		//InventoryClick Events
		this.registerEvent(new OnCustomInventoryClickEvent());
	}
	
	private void registerEvent(Listener listener) {
		this.server.getPluginManager().registerEvents(listener, this);
	}
	
	private void registerCommand(String command, CommandExecutor commandExecutor) {
		this.getCommand(command).setExecutor(commandExecutor);
	}
	
	private void registerCommands() {
		this.registerCommand("menu", new MenuCommand());
		this.registerCommand("coords", new CoordCommand());
		this.registerCommand("deathCoord", new DeathCoordCommand());
		this.registerCommand("back", new BackCommand());
		this.registerCommand("sendItem", new SendItemCommand());
		this.registerCommand("spawnCustomMob", new SpawnCustomMobCommand(this));
		this.registerCommand("deathBox", new DeathBoxCommand());
		this.registerCommand("clearInv", new ClearInventoryCommand());
		this.registerCommand("day", new DayCommand());
		this.registerCommand("spawn", new SpawnCommand());
		this.registerCommand("test", new TestCommand());
		this.registerCommand("w", new WeatherCommand());
		this.registerCommand("chunkScan", new ChunkScanCommand());
		this.registerCommand("world", new WorldCommand());
	}
	
	private void registerRecipies(){
		new WhiteDyeRecipe(this);
		new ConcreteRecipe(this);
		new GrassBlockRecipe(this);
		new DeathBoxRecipe(this);
		new StairsToMaterialRecipe(this);
		new SlapsToMaterialRecipe(this);
		new TntPickaxeRecipe(this);
	}
	
	private void loadingCustomWorlds() {
		for(String worldname : WorldGenerator.getWorldnamesFromJson()) {
			this.server.getConsoleSender().sendMessage(Utilities.getCustomPrefix(ChatColor.DARK_PURPLE, "W") + "loading world " + ChatColor.GOLD + worldname);
			Bukkit.createWorld(new WorldCreator(worldname));
		}
	}
}