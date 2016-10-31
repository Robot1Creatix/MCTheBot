package com.creatix.mc.thebot.mod.proxies;

import com.creatix.mc.thebot.bot.UserHandler;
import com.creatix.mc.thebot.mod.commands.BotAdministrationCommand;
import com.creatix.mc.thebot.mod.commands.BotInteractionCommand;
import com.creatix.mc.thebot.mod.commands.TestCommand;
import com.creatix.mc.thebot.mod.registry.BlockRegistry;
import com.creatix.mc.thebot.mod.registry.ItemRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		MinecraftForge.EVENT_BUS.register(new UserHandler());
		BlockRegistry.init();
		ItemRegistry.init();
	}
	
	public void Init(FMLInitializationEvent e){
		
	}
	
	public void postInit(FMLPostInitializationEvent e){
		
	}
	
	public void serverStarting(FMLServerStartingEvent e){
		e.getServer().setOnlineMode(false);
		e.registerServerCommand(new BotAdministrationCommand());
		e.registerServerCommand(new BotInteractionCommand());
		e.registerServerCommand(new TestCommand());
	}
}
