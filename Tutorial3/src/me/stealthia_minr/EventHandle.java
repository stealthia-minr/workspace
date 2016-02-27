package me.stealthia_minr;

import org.bukkit.plugin.java.JavaPlugin;

public class EventHandle extends JavaPlugin {

	@Override
	public void onEnable() {
		new playerListener(this);
	}
	@Override
	public void onDisable() {
		
	}
	
	
	
}
