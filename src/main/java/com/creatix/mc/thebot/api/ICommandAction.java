package com.creatix.mc.thebot.api;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public interface ICommandAction {

	void exec(ICommandSender sender, String[] args, MinecraftServer server);
	
}
