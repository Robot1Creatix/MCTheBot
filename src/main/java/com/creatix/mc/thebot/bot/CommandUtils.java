package com.creatix.mc.thebot.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import com.creatix.mc.thebot.api.ICommandAction;
import com.creatix.mc.thebot.bot.objects.Command;

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
			String ret = "Monitoring subject [ "+s.player.getDisplayNameString()+" ]\n";
			ret += "---------------------------------\n";
			ret += "Alias:      "+s.player.getDisplayNameString()+"\n";
			ret += "---------------------------------\n";
			ret += "UserUID:    "+s.uuid+"\n";
			ret += "UIN:        "+s.getUIN()+"\n";
			ret += "Projection: "+s.clazz.name+"\n";
			ret += "Conclusion: "+s.getConclusion()+"\n\n";
			ret += "AccessLevel:"+s.clazz.accessLevel+"\n";
			ret += "Relations:  "+s.getRelations()+"\n";
			ret += "Status:     "+s.getStatus()+"\n";
			sender.addChatMessage(new TextComponentString(ret));
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
