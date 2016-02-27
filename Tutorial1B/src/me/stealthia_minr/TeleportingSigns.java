package me.stealthia_minr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;

public class TeleportingSigns  extends JavaPlugin implements Listener{
	/*
	final String FILE_LOCATION = "world/TeleportingSigns.dat";
	HashMap<Location, Location> tpMap; //a dictionary of teleport pairs.  The first location is the start point, the second location is the destination.
	boolean readyToClick = false;
	Location tpTarget = null;
	
	@Override
	public void onEnable() {
		tpMap = new HashMap<Location, Location>();
		readyToClick = false;
		tpTarget = null;
		
		getLogger().info("Hello Logan! onEnable is a butt!");
		getLogger().info("(Loading TeleportingSigns ...)");
		Bukkit.getServer().getPluginManager().registerEvents( this, this);
		
		//load teleport pairs from file:
		try
		{
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_LOCATION)));//again, make sure you have a file object
		    String l;
		    int x,y,z; //we'll use these to load the coordinates of both locations
		    while((l=br.readLine()) != null)
		    {
		        //look for data lines formatted: [sign X,Y,Z] + ":" + [destination X,Y,Z]
		    	//example: "1,2,3|103,102,101"
		    	l=l.trim(); //this takes off the newline character on the end of the string, which we don't need.
		    	
		    	//This next line uses 'regular expressions' (aka Regex), which are a pain in the butt even for advanced programmers.  Don't worry if you can't read it.
		    	if(! l.matches("[0-9]+,[0-9]+,[0-9]+:[0-9]+,[0-9]+,[0-9]+")) {//If the string doesn't match our format (see above).
		    		getLogger().warning("Malformed string in TeleportingSigns.dat: [" + l + "]");
		    		continue; //this skips the while loop to the next line, and doesn't run the below code
		    	}
		    	//split up the string, first by "|", then by commas
		    	String firstHalf = l.split(":")[0];//0 is the first one - computers count 0,1,2...
		    	String backHalf = l.split(":")[1];
		    	String[] firstHalfArray = firstHalf.split(",");
		    	String[] backHalfArray = backHalf.split(",");
		    	
		    	//convert sign location from string to bukkit Location
		    	x = Integer.parseInt(firstHalfArray[0]);
		    	y = Integer.parseInt(firstHalfArray[1]);
		    	z = Integer.parseInt(firstHalfArray[2]);
		    	Location signLocation = new Location(null, x,y,z);

		    	//convert destination location from string to bukkit Location
		    	x = Integer.parseInt(backHalfArray[0]);
		    	y = Integer.parseInt(backHalfArray[1]);
		    	z = Integer.parseInt(backHalfArray[2]);
		    	Location destLocation = new Location(null, x,y,z);
		    	
		    	//add this line's data to our tpMap
		    	tpMap.put(signLocation, destLocation);
		    }
		    br.close();
		} catch (Exception e){
			getLogger().info(e.getMessage());
		}
	}
	
	@Override
	public void onDisable() {
		//Save tpMap to file
		try
		{
		    BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_LOCATION)); //use FileWriter(file, true) to prevent overriding
		    for(Location signLocation in tpMap.keySet())
		    {
		    	//TODO write this, and fix the for loop (that's not Java, whoops)
		    }
		    bw.close();
		}
		catch(IOException ioe)
		{
			getLogger().info(ioe.getMessage());
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {
		if (cmd.getName().equalsIgnoreCase("tpstart") && sender instanceof Player) {
			readyToClick = true;

			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("tptarget") && sender instanceof Player) {
			Player player = (Player) sender;
			//player.sendMessage("Target is: " + tpTarget.toString());
			//player.getLocation()
			player.teleport(tpTarget);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("tpcancel") && sender instanceof Player) {
			readyToClick = false;
			tpTarget = null;
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block clickedBlock = event.getClickedBlock();
			if(readyToClick){//If we just got the "/settp" command, then the next right click saves the location to tpTarget
				tpTarget = event.getClickedBlock().getLocation().add(.5,0,.5);//UNTESTED - does the .5/.5 offset bring us to the middle of the CORRECT square?
				readyToClick = false;
			} else if (event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) {
				//If we right click a sign, try to teleport to where it points.
				if(tpMap.containsKey(clickedBlock.getLocation())) {//If we have this sign in our Map
					event.getPlayer().teleport(tpMap.get(clickedBlock.getLocation()));
				}else{
					//Do we do anything when they right-click a sign we don't know about?
					event.getPlayer().sendMessage("Unknown tp sign");
				}
			}
			
			
		}
					
	}
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        BlockState state = event.getBlock().getState();
        if(state instanceof Sign && tpTarget != null) {
            Sign sign = (Sign) state;
    
            tpMap.put(sign.getLocation(),tpTarget);
            tpTarget = null;
            //player.sendMessage("It works");
            //player.sendMessage(ChatColor.AQUA + "Sign Loc: " + ChatColor.RED + sign.getLocation());
        }
    }
	*/
}
