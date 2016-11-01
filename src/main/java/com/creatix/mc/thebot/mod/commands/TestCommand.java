package com.creatix.mc.thebot.mod.commands;

import java.util.List;

import com.creatix.mc.thebot.mod.utils.Config;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
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
					new AxisAlignedBB(player.posX-Config.godmodeRad, player.posY-Config.godmodeRad, player.posZ-Config.godmodeRad, player.posX+Config.godmodeRad, player.posY+Config.godmodeRad, player.posZ+Config.godmodeRad));
		if(entities.isEmpty())
		{
			System.out.println(Config.godmodeRad);
			sender.addChatMessage(new TextComponentString("All Clear"));
		}else {
			entities.stream().sequential().sorted((e1, e2) -> Integer.compare((int) e2.getDistanceToEntity(player),(int) e1.getDistanceToEntity(player)));
			for(EntityMob m : entities) {
				sender.addChatMessage(new TextComponentString(m.getDistanceToEntity(player)+"-"+m.getName()));
			}
		}
	}

}
