package me.stealthia_minr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WandOfX extends JavaPlugin implements Listener{
	HashMap<String, HashMap<String, Integer>> playerLevels;
	final String CLASS_SAVE_FILE = "playerClasses.dat";
	// key = player name, value = sub-hashmap (key = class name, value = level)
	
	@Override
	public void onEnable() {
		
		getLogger().info("Loading WandOfX...");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		playerLevels = new HashMap<String, HashMap<String, Integer>>();
		
		try
		{
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(CLASS_SAVE_FILE)));//again, make sure you have a file object
		    String line;

		    String playerName = "";
		    while((line=br.readLine()) != null)
		    {
		        //look for data lines formatted: 
		    	//Line 1: [player name]
		    	//Line 2: [class:level;class2:level; ...]
		    	//repeat
		    	
		    	line=line.trim(); //this takes off the newline character on the end of the string, which we don't need.
		    	
		    	if (playerName == ""){//The lines with playernames
		    		playerName = line;
		    		playerLevels.put(playerName,  new HashMap<String, Integer>());
		    		
		    	} else {//The lines with class/level data
		    		String[] pairs = line.split(";");
		    		for (int i=0;i < pairs.length; i++){
		    			String[] pairData = pairs[i].split(":");
		    			if (pairData.length == 2) playerLevels.get(playerName).put(pairData[0],Integer.parseInt(pairData[1]));
		    		}
		    		
		    		playerName = "";
		    	}
		    }
		    br.close();
		} catch (Exception e){
			getLogger().info(e.getMessage());
		}
	}
	
	@Override
	public void onDisable() {
		try
		{
		    BufferedWriter bw = new BufferedWriter(new FileWriter(CLASS_SAVE_FILE)); //use FileWriter(file, true) to prevent overriding
		    //save data lines formatted: 
	    	//Line 1: [player name]
	    	//Line 2: [class:level;class2:level; ...]
	    	//repeat
		    
		    for(String playerName : playerLevels.keySet()){
		    	bw.write(playerName);
		    	bw.newLine();
		    	
		    	//create the next line [class:level;class2:level; ...]
		    	
		    	for(String className : playerLevels.get(playerName).keySet()){
		    		bw.write(className);
		    		bw.write(":");
		    		bw.write(playerLevels.get(playerName).get(className).toString());
		    		bw.write(";");
		    	}
		    	bw.newLine();
		    }
		    bw.close();
		}
		catch(Exception e)
		{
			getLogger().info(e.getMessage());
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {
		if (cmd.getName().equalsIgnoreCase("givexp") && sender instanceof Player) {
			Player player = (Player) sender;
			String playerName = player.getName();
			if(args.length != 2){
				return false;
			}
			String className = args[0];
			int xp = Integer.parseInt(args[1]);
			if(! playerLevels.containsKey(playerName)){
				playerLevels.put(playerName, new HashMap<String, Integer>());
			}
			if(playerLevels.get(playerName).containsKey(className)){
				playerLevels.get(playerName).put(className, xp + playerLevels.get(playerName).get(className));
			}else{
				playerLevels.get(playerName).put(className, xp);
			}
			player.sendMessage("Gave " + xp + " in class " + className + ".");

			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("showxp") && sender instanceof Player) {
			Player player = (Player) sender;
			String playerName = player.getName();
			if(! playerLevels.containsKey(playerName)){
				player.sendMessage("You have no xp.");
				return true;
			}
			player.sendMessage("Your XP:");
			for(String className : playerLevels.get(playerName).keySet()){
				player.sendMessage(className + " - " + playerLevels.get(playerName).get(className));
			}
			return true;
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		
		Action act = event.getAction();
		if (act == Action.LEFT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK){
			if (p.getInventory().getItemInHand().getType() == Material.STICK){
				p.launchProjectile(Fireball.class, p.getEyeLocation().getDirection().multiply(2));
			}
		}
		
	    
					
	}
	@EventHandler
	public void OnPlayerAnimation(PlayerAnimationEvent event) {
		final double MAX_OFF_DIST = 1.5; //blocks of error in where you're looking
		final double MAX_RANGE = 30;
		Player p = event.getPlayer();
		Location pEyeLoc = p.getEyeLocation();
		
		if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
			List<Entity> nearbyE = p.getNearbyEntities(60,60,60);
			if (nearbyE.isEmpty()){
				p.sendMessage("no targets found");
				return; //exit the function
			}else{
				//p.sendMessage(nearbyE.size() + " targets found");
			}
			
			Damageable bestGuy = null;
			double bestDist = 100;
			boolean first = false;//for testing, prints out locations.
	        for (Entity e : nearbyE) {
	            if (e instanceof Damageable) {
	            	Damageable guy = (Damageable) e;
	                //How far away is Guy? [let's say 30 blocks]
	                Location guyLoc = guy.getLocation();
	                double guyDist = pEyeLoc.distance(guyLoc);
	                //Okay, now where am I looking, that's that far away? 
	                //[if I'm looking East, pLookLoc = '30 blocks East of me']
	                Vector pLookVector = pEyeLoc.getDirection();
	                pLookVector.multiply(guyDist/pLookVector.length());
	                Location pLookLoc = pEyeLoc.clone();
	                pLookLoc.add(pLookVector);
	                //How far is Guy from that location?
	                double errDist = pLookLoc.distance(guyLoc);
	                
	                if (/* p.hasLineOfSight(guy) && */ errDist < MAX_OFF_DIST && guyDist < MAX_RANGE){
	                	if(guyDist < bestDist){
	                		bestDist = guyDist;
	                		bestGuy = guy;
	                	}
	                }
	                if(first && guyDist < MAX_RANGE){
	                	p.sendMessage("guyLoc:" + guyLoc.toString());
	                	p.sendMessage("pEyeLoc:" + pEyeLoc.toString());
	                	p.sendMessage("guyDist:" + guyDist);
	                	p.sendMessage("errDist:" + errDist);
	    	            //first = false;
	                }
	            }
	        }
	        if (bestGuy != null){
	        	bestGuy.playEffect(EntityEffect.VILLAGER_HAPPY);
	        	Location guyLoc = bestGuy.getLocation();
	        	guyLoc.add(new Vector(0,3,0));
	        	bestGuy.teleport(guyLoc);
	        }
		}
	}
}
