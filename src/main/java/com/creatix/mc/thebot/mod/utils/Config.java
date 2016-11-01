package com.creatix.mc.thebot.mod.utils;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config extends Configuration{
	
	public static int godmodeRad;
	
	public Config(File file) {
		super(file);
		init();
	}
	
	private void init() {
		this.load();
		godmodeRad = this.getInt("godmodeRad", "TheBot", 10, 10, 20, "//**//");
		this.save();
	}
	
}
