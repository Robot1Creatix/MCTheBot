package com.creatix.mc.thebot.bot.utils;

import java.util.List;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;

import com.creatix.mc.thebot.bot.Subject;
import com.creatix.mc.thebot.mod.utils.Config;

public class GodModeThread extends Thread{
	
	public Subject sender;
	public MinecraftServer server;
	
	public GodModeThread(Subject s, MinecraftServer ser){
		this.server = ser;
		this.sender = s;
		this.setName("Godmode-"+s.player.getDisplayNameString());
	}
	
	@Override
	public void run() {
		while(isAlive()) {
			try{
				tick();
				Thread.currentThread().sleep(500);
			}catch(Throwable t){
				t.printStackTrace();
			}
		}
	}
	
	public String tick() {
		EntityPlayer player = sender.player;		
		List<EntityMob> entities =
				player.getEntityWorld().
				getEntitiesWithinAABB(EntityMob.class, 
					new AxisAlignedBB(player.posX-Config.godmodeRad, player.posY-Config.godmodeRad, player.posZ-Config.godmodeRad, player.posX+Config.godmodeRad, player.posY+Config.godmodeRad, player.posZ+Config.godmodeRad));
		if(entities.isEmpty())
		{
			return "None.";
		}else {
			entities.stream().sequential().sorted((e1, e2) -> Integer.compare((int) e1.getDistanceToEntity(player),(int) e2.getDistanceToEntity(player)));
			String ret = "";
			for(EntityMob m : entities) {
				ret += m.getName()+" "+(int) m.getDistanceToEntity(player)+"m\n";
			}
			return ret;
		}
	}
}
