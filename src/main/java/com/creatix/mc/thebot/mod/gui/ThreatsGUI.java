package com.creatix.mc.thebot.mod.gui;

import java.awt.Color;

import com.creatix.mc.thebot.bot.Subject;
import com.creatix.mc.thebot.bot.UserUtils;
import com.creatix.mc.thebot.mod.utils.EventHadler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class ThreatsGUI extends Gui{
	public Minecraft mc;
	public int width, height;
	
	public ThreatsGUI(Minecraft mc, Subject s) {
		this.mc = mc;
		ScaledResolution r = new ScaledResolution(mc);
		this.width = r.getScaledWidth();
		this.height = r.getScaledHeight();
		String f = s.gmt.tick();
		String[] lines = f.split("\n");
		drawRect(0, (height/2)-12, 90, (height/2)+(10*lines.length), new Color(0, 0, 0, 150).getRGB());
		drawString(mc.fontRendererObj, "Current Threats:", 2, (height / 2)-10, Integer.parseInt("FF0000", 16));
		for(int i = 0; i < lines.length; i++) {
			drawString(mc.fontRendererObj, lines[i], 2, (height / 2)+(10 * i), Integer.parseInt("FF0000", 16));
		}
		
	}

}
