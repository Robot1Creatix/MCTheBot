package com.creatix.mc.thebot.bot.objects;

import com.creatix.mc.thebot.api.ICommandAction;

public class Command{
	public String name, sName;
	public float accessLevel;
	public ICommandAction act;
	public Command(String name, String sName, float accessLevel, ICommandAction act){ 
		this.name = name;
		this.sName = sName;
		this.accessLevel = accessLevel;
		this.act = act;
	}
}
