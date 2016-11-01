package com.creatix.mc.thebot.mod.utils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.creatix.mc.thebot.bot.UserUtils;
import com.creatix.mc.thebot.mod.ModCore;
import com.creatix.mc.thebot.mod.gui.ThreatsGUI;

public class EventHadler {
	
	
	/** GUI **/
	public static ThreatsGUI threatsGUI;
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event)
	{
		if (event.getType() != ElementType.EXPERIENCE) return;
		if(!ModCore.isBotActive) return;
		if(UserUtils.getUser(Minecraft.getMinecraft().thePlayer) != null){
			if(UserUtils.getUser(Minecraft.getMinecraft().thePlayer).gmt.isAlive())
				threatsGUI = new ThreatsGUI(Minecraft.getMinecraft(), UserUtils.getUser(Minecraft.getMinecraft().thePlayer));
		}
	}
}
