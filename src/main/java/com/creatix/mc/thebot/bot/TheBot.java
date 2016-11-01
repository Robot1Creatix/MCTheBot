package com.creatix.mc.thebot.bot;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.creatix.mc.thebot.bot.neuralsystem.NeuralSystem;
import com.creatix.mc.thebot.mod.ModCore;
import com.mojang.authlib.GameProfile;


public class TheBot {
	
	public MinecraftServer host;
	public EntityPlayer admin;
	public Thread thread;
	public EntityPlayerMP player;
	
	
	public TheBot(MinecraftServer host, EntityPlayer admin) {
		this.host = host;
		this.admin = admin;
		this.thread = getThread();
		this.thread.setName("TheBot");
		//this.player = getPlayer();
		//this.player = getTest();

	}
	
	public boolean activate(String[] args) {
		if(ModCore.isBotActive){
			return false;
		}
		thread.start();
		return thread.isAlive();
	}
	
	public Thread getThread() {
		return new Thread(() -> {
			try{
				sendChatMessage("TheBot starting...", new Style().setBold(true).setColor(TextFormatting.WHITE));
				wait(2);
				sendChatMessage("Initializing...", new Style().setColor(TextFormatting.BLUE));
				UserUtils.init(this);
				wait(2);
				UserUtils.setClass(admin, UserUtils.ADMIN);
				sendChatMessage("Data acquisition...", new Style().setColor(TextFormatting.BLUE));
				UserUtils.registerAvaiableUsers(host);
				wait(3);
				sendChatMessage("Admin identified:    %s",new Style().setColor(TextFormatting.YELLOW).setBold(false), admin.getDisplayNameString());
				wait(2);
				sendChatMessage("Initializing heuristic core...", new Style().setColor(TextFormatting.BLUE));
				wait(5);
				CommandUtils.init();
				NeuralSystem.init(this);
				ModCore.isBotActive = thread.isAlive();
			}catch(Throwable t) {
				t.printStackTrace();
			}
		});
	}
	public void sendChatMessage(String str, Object ...objects) {
		host.getPlayerList().sendChatMsg(new TextComponentTranslation(str, objects));
	}
	public void sendChatMessage(String str, Style s, Object ...objects) {
		host.getPlayerList().sendChatMsg(new TextComponentTranslation(str, objects).setStyle(s));
	}
	
	public void sendAdmin(String str, Object ...objects) {
		admin.addChatComponentMessage(new TextComponentTranslation(str, objects));
	}
	public void sendAdmin(String str, Style s, Object ...objects) {
		admin.addChatComponentMessage(new TextComponentTranslation(str, objects).setStyle(s));
	}
	
	public void sendMessage(String str,ICommandSender sender ,Object ...objects) {
		sender.addChatMessage(new TextComponentTranslation(str, objects));
	}
	public void sendMessage(String str, Style s,ICommandSender sender ,Object ...objects) {
		sender.addChatMessage(new TextComponentTranslation(str, objects).setStyle(s));
	}
	
	public void wait(int secs) {
		try {
			TimeUnit.SECONDS.sleep(secs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void onStop(ICommandSender stopper) {
		
	}
}
