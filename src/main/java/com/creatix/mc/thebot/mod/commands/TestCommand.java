package com.creatix.mc.thebot.mod.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class TestCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender,String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) sender;
		List<EntityMob> entities =
				player.getEntityWorld().
				getEntitiesWithinAABB(EntityMob.class, 
						player.getCollisionBoundingBox().expandXyz(10));
		if(entities.isEmpty())
		{
			sender.addChatMessage(new TextComponentString("All Clear"));
		}else {
			for(EntityMob m : entities) {
				sender.addChatMessage(new TextComponentString(m.getName()));
			}
		}
	}

}
