package me.stealthia_minr;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WandOfX extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		
		getLogger().info("Loading WandOfX...");
		Bukkit.getServer().getPluginManager().registerEvents( this, this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		
		Action act = event.getAction();
		if (act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK){
			if (p.getInventory().getItemInHand().getType() == Material.STICK){
				p.sendMessage("Fire!");
				p.launchProjectile(Fireball.class, p.getEyeLocation().getDirection().multiply(2));
			}
		}
		
		
					
	}
}
