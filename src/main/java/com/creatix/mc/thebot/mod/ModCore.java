package com.creatix.mc.thebot.mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Logger;

import com.creatix.mc.thebot.bot.TheBot;
import com.creatix.mc.thebot.mod.proxies.CommonProxy;
import com.creatix.mc.thebot.mod.utils.Config;
import com.creatix.mc.thebot.mod.utils.Constants;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.MOD_VER, dependencies = Constants.MOD_DEPS)
public class ModCore {
	
	@Instance(Constants.MOD_ID)
	public static ModCore instance;
	
	@SidedProxy(serverSide = "com.creatix.mc.thebot.mod.proxies.CommonProxy", clientSide = "com.creatix.mc.thebot.mod.proxies.ClientProxy", modId = Constants.MOD_ID)
	public static CommonProxy proxy;
	
	public static Logger log;
	public static Config config;
	public static TheBot bot;
	public static boolean isBotActive;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		log = e.getModLog();
		config = new Config(e.getSuggestedConfigurationFile());
		proxy.preInit(e);
	}
	@EventHandler
	public void Init(FMLInitializationEvent e){
		proxy.Init(e);
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		proxy.postInit(e);
	}
	@EventHandler
	public void serverStarting(FMLServerStartingEvent e){
		proxy.serverStarting(e);
	}	
}
