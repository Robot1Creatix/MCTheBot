package com.creatix.mc.thebot.mod.utils;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config extends Configuration{
	
	
	public Config(File file) {
		super(file);
	}
	
	private void init() {
		this.load();
		this.save();
	}
	
}
