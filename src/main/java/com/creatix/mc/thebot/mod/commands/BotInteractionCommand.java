package com.creatix.mc.thebot.mod.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

import com.creatix.mc.thebot.bot.CommandUtils;
import com.creatix.mc.thebot.bot.Subject;
import com.creatix.mc.thebot.bot.UserUtils;
import com.creatix.mc.thebot.bot.objects.Command;
import com.creatix.mc.thebot.mod.ModCore;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class BotInteractionCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "!";
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("!", "botinterface", "interact", "botinteract", "interface");
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/! <command name> <args...>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 1) {
			sender.addChatMessage(new TextComponentTranslation("Invalid argumants. Use /help !"));
			return;
		}
		if(!ModCore.isBotActive){
			sender.addChatMessage(new TextComponentTranslation("Bot not working. Use /bot activate"));
			return;
		}
		if(!(sender instanceof EntityPlayer)){
			sender.addChatMessage(new TextComponentTranslation("You cannot be use commands in command blocks."));
			return;
		}
		Command c = CommandUtils.getCommand(args[0]);
		if(c == null) {
			sender.addChatMessage(new TextComponentTranslation("Command %s not found. Use /! help to get all commands", args[0]));
			return;
		}
		EntityPlayer se = (EntityPlayer) sender;
		Subject s = UserUtils.getUser(se.getUniqueID().toString());
		if(s == null) {
			sender.addChatMessage(new TextComponentTranslation("User %s is invalid."));
			try {
				UserUtils.reloadUser(UserUtils.initUser(se, server));
			} catch (JsonIOException | JsonSyntaxException | IOException e) {
				e.printStackTrace();
				return;
			}
			return;
		}
		System.out.println(s.clazz.accessLevel+" "+c.accessLevel);
		if(s.clazz.accessLevel < c.accessLevel) {
			sender.addChatMessage(new TextComponentTranslation("Permition denied"));
			return;
		}
		String[] cArgs = ArrayUtils.removeElement(args, args[0]);
		System.out.printf("Executing %s... Args {%s}. By %s - %s", c.name, Arrays.toString(cArgs), s.player.getDisplayNameString(), s.clazz.name);
		Thread t = new Thread(() -> {
			c.act.exec(sender, cArgs, server);
		});
		t.start();
	}
}
