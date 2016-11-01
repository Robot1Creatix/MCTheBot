package com.creatix.mc.thebot.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import com.creatix.mc.thebot.api.ICommandAction;
import com.creatix.mc.thebot.bot.objects.Command;
import com.creatix.mc.thebot.bot.utils.GodModeThread;
import com.creatix.mc.thebot.mod.ModCore;

public class CommandUtils {
	
	public static List<Command> commands;
	
	public static void init() {
		commands = new ArrayList<>();
		register("monitor", "mon", 0.5F, (sender, args, server) -> {
			if(args.length < 1) {
				sender.addChatMessage(new TextComponentString("Invalid args"));
				return;
			}
			EntityPlayer p = server.getPlayerList().getPlayerByUsername(args[0]);
			if(p == null)
				p = server.getPlayerList().getPlayerByUUID(UUID.fromString(args[0]));
			if(p == null) {
				sender.addChatMessage(new TextComponentTranslation("User %s not found.", args[0]));
				return;
			}
			Subject s = UserUtils.getUser(p.getUniqueID().toString());
			if(s == null) {
				sender.addChatMessage(new TextComponentTranslation("Subject %s is invalid.", args[0]));
				return;
			}
			String ret = "";
			try{
				ret = "Monitoring subject [ "+s.player.getDisplayNameString()+" ]\n";
				ret += "---------------------------------\n";
				ret += "Alias:         "+s.player.getDisplayNameString()+"\n";
				ret += "---------------------------------\n";
				ret += "UserUID:       "+s.uuid+"\n";
				ret += "UIN:           "+s.getUIN()+"\n";
				ret += "Projection:    "+s.getClassification().name+"\n";
				ret += "Conclusion:    "+s.getConclusion()+"\n\n";
				ret += "AccessLevel:   "+s.getClassification().accessLevel+"\n";
				ret += "Relations:     "+s.getRelations()+"\n";
				ret += "Status:        "+s.getStatus()+"\n";
			}catch(Exception e) {
				e.printStackTrace();
			}
			sender.addChatMessage(new TextComponentString(ret));
		});
		register("godmode", "gm", 1.0F, (sender, args, server) -> {
			try{
				EntityPlayer player = (EntityPlayer) sender;
				if(UserUtils.getUser(player) == null) {
					sender.addChatMessage(new TextComponentString("User is invalid"));
					return;
				}
				Subject s = UserUtils.getUser(player);	
				if(s.getClassification() == UserUtils.ADMIN || s.getClassification() == UserUtils.ANALOG_INTERFACE) {
					if(!s.containsTag("gm")) {
						s.gmt.start();
						ModCore.bot.sendMessage("Can You Hear Me?" ,s.player);
					}else{
						ModCore.bot.sendMessage("You already in godmode" ,s.player);
					}
				}else{
					s.decreaseRelations(2);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void register(String name, String sName, float accessLevel, ICommandAction act) {
		commands.add(new Command(name, sName, accessLevel, act));
		System.out.printf("Command %s registered with %f level", name, accessLevel);
	}
	
	public static Command getCommand(String name) {
		for(Command c : commands) {
			if(c.name.equals(name) || c.sName.equals(name))
				return c;
		}
		return null;
	}
}
