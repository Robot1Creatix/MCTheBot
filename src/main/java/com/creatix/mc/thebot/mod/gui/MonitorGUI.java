package com.creatix.mc.thebot.mod.gui;

import org.fusesource.jansi.Ansi.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import com.creatix.mc.thebot.bot.Subject;

public class MonitorGUI extends GuiScreen{

	public Subject subj;
	public Minecraft mc;
	
	public MonitorGUI(Subject s) 
	{
		this.subj = s;
		this.mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc);
		int  width = res.getScaledWidth();
		int height = res.getScaledHeight();
		drawString(mc.fontRendererObj, "Monitoring subject...", width/2, height/2, java.awt.Color.WHITE.getRGB());
		drawHorizontalLine((width/2)-50,(width/2)+50 ,(height/2)+10 , java.awt.Color.WHITE.getRGB());
	}
	
}
