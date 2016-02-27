package me.stealthia_minr;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class playerListener implements Listener {

	public playerListener(EventHandle plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onThrow(PlayerEggThrowEvent e) {
		
		Player player = e.getPlayer();
		
		player.teleport(e.getEgg());
		 
	}
	
	@EventHandler
	public void onEggHitBlock(ProjectileHitEvent event){
        if(event.getEntity() instanceof Egg){
            Entity egg = event.getEntity();
            Location location = egg.getLocation();
            World world = event.getEntity().getWorld();
            world.createExplosion(location, 3F);
        }
    }
}
