package com.creatix.mc.thebot.mod.commands;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.creatix.mc.thebot.bot.TheBot;
import com.creatix.mc.thebot.mod.ModCore;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class BotAdministrationCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "bot";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/bot <option> <args...>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		List<String> possible_args = Arrays.asList("activate", "on", "deactivate", "off", "reset-user-data");
		if(args.length == 0 || (args.length > 0 && !possible_args.contains(args[0]))) {
			sender.addChatMessage(new TextComponentString("Arguments is invalid.\nPossible args is:\n-Activate, -On;\n-Deactivate, -Off;\n-Reset-User-Data"));
			return;
		}
		if(!(sender instanceof EntityPlayer)) {
			return;
		}
		switch(args[0].toLowerCase()) {
			case "activate": case "on": {
				if(!ModCore.isBotActive) {
					ModCore.bot = new TheBot(server,(EntityPlayer) sender);
					ModCore.bot.activate(ArrayUtils.removeElement(args, args[0]));
				}else if(ModCore.isBotActive) {
					ModCore.bot.sendChatMessage("Bot already active");
				}else {
					throw new IllegalStateException("Ваш буллеан сломан. И это не лечится!");
				}
				break;
			}
			case "deactivate": case "off": {
				if(!ModCore.isBotActive) {
					ModCore.bot.sendChatMessage("Bot not working");
				}else if(ModCore.isBotActive) {
					if(!(((EntityPlayer) sender).getUniqueID().equals(ModCore.bot.admin.getUniqueID()))){
						sender.addChatMessage(new TextComponentString("You not admin to stop thebot!"));
						return;
					}
					ModCore.bot.onStop(sender);
					ModCore.bot.thread.interrupt();
				}else {
					throw new IllegalStateException("Ваш буллеан сломан. И это не лечится!");
				} 
				break;
			}
			case "reset-user-data": {
				break;
			}
			default: {
				sender.addChatMessage(new TextComponentString("Arguments is invalid.\nPossible args is:\n-Activate, -On;\n-Deactivate, -Off;\n-Reset-User-Data"));
			}
		}
	}
}
