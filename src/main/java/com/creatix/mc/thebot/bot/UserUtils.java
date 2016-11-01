package com.creatix.mc.thebot.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.creatix.mc.thebot.bot.Classification.ClassType;
import com.creatix.mc.thebot.mod.ModCore;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class UserUtils {
	
	public static final Classification IRRELEVANT = new Classification("Irrelevant", "IRR",0.1F, ClassType.IRRELEVANT);
	public static final Classification ASSET = new Classification("Asset", "ASST",0.5F, ClassType.CONTINGENCY);
	public static final Classification ADMIN = new Classification("Admin", "ADM",1.0F, ClassType.CONTINGENCY);
	public static final Classification ANALOG_INTERFACE = new Classification("Analog Interface", "AN",0.9F, ClassType.ANALOGUEINTERFACE);
	public static final Classification THREAT = new Classification("Threat", "THR",0.0F, ClassType.IRRELEVANT);
	public static final Classification PRIMARY_THREAT = new Classification("Primary Threat", "PRTHR",0.0F, ClassType.ENEMYAGENT);
	
	public static File usersDir;
	
	public static HashSet<Subject> users;
	
	/**public static void init(TheBot bot) throws IOException {
		usersDir = new File(bot.host.getDataDirectory().getAbsolutePath().substring(0, bot.host.getDataDirectory().getAbsolutePath().length()-2), "bot/users/");
		System.out.println(usersDir.getAbsolutePath() + " "+bot.host.getDataDirectory().getAbsolutePath());
		if(!usersDir.exists())
			usersDir.mkdirs();
		for(EntityPlayer p : bot.host.getPlayerList().getPlayerList()) {
			Subject s = initUser(p, bot.host);
			users.add(s);
			System.out.println(s);
			bot.sendChatMessage("Initialized %s:%s as %s",
					s.player.getDisplayNameString(),
					s.player.getUniqueID(),
					s.clazz.name); 
		}
	}
	
	public static Subject initUser(EntityPlayer player, MinecraftServer s) throws JsonIOException, JsonSyntaxException, IOException {
		if(users == null)
			users = new ArrayList<>();
		File f = new File(usersDir, player.getUniqueID()+".user");
		if(f.exists()) {
			JsonObject o = new JsonParser().parse(new InputStreamReader(new FileInputStream(f))).getAsJsonObject();
			EntityPlayer p = s.getPlayerList().getPlayerByUUID(UUID.fromString(o.get("uuid").getAsString()));
			Classification cl = getClassification(o.get("class").getAsString());
			Subject user = new Subject(p, p.getServer(), cl, o);
			return user;
		}else {
			JsonObject o = new JsonObject();
			o.addProperty("uuid", player.getUniqueID().toString());
			o.addProperty("uin", "xxx-xx-xxxx");
			o.addProperty("class", "IRR");
			o.addProperty("relation", 0);
			o.addProperty("nearby", false);
			o.add("tags", new JsonArray());
			o.addProperty("conclusion", "Ignore");
			o.addProperty("status", "Unknown");
			f.createNewFile();
			FileWriter w = new FileWriter(f);
			w.write(new GsonBuilder().setPrettyPrinting().create().toJson(o));
			w.close();
			return initUser(player, s);
		}
	}
	
	public static void reloadUser(Subject s) {
		users.remove(s);
		users.add(s);
	}**/
	
	public static void init(TheBot bot) {
		usersDir = new File(bot.host.getDataDirectory().getAbsolutePath().substring(0, bot.host.getDataDirectory().getAbsolutePath().length()-2), "bot/users/");
		if(!usersDir.exists())
			usersDir.mkdirs();
		users = new HashSet<Subject>();
	}
	
	public static void registerAvaiableUsers(MinecraftServer server)
	{
		for(EntityPlayer p : server.getPlayerList().getPlayerList()) {
			File f = new File(usersDir, p.getUniqueID()+".user");
			if(f.exists()){
				loadUser(p, f);
			}else {
				registerUser(p, IRRELEVANT, f);
			}
		}
	}
	
	public static void registerUser(EntityPlayer player, Classification clazz, File f) {
		try{
			JsonObject o = new JsonObject();
			o.addProperty("uuid", player.getUniqueID().toString());
			o.addProperty("uin", "xxx-xx-xxxx");
			o.addProperty("class", clazz.id);
			o.addProperty("relation", 0);
			o.addProperty("nearby", false);
			o.add("tags", new JsonArray());
			o.addProperty("conclusion", "Ignore");
			o.addProperty("status", "Unknown");
			FileWriter wr = new FileWriter(f);
			wr.write(new GsonBuilder().setPrettyPrinting().create().toJson(o));
			wr.close();
			loadUser(player, f);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void setClass(EntityPlayer p, Classification cl) {
		try {
			File f = new File(usersDir, p.getUniqueID()+".user");
			if(f.exists()){
				JsonObject o = new JsonParser().parse(new InputStreamReader(new FileInputStream(f))).getAsJsonObject();
				o.addProperty("class", cl.id);
				FileWriter w = new FileWriter(f);
				w.write(new GsonBuilder().setPrettyPrinting().create().toJson(o));
				w.close();
			}else {
				registerUser(p, cl, f);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadUser(EntityPlayer player, File f) {
		try {
			JsonObject o = new JsonParser().parse(new InputStreamReader(new FileInputStream(f))).getAsJsonObject();
			Subject s = new Subject(player, player.getServer(), f);
			if(!isRegistered(s))
				addUser(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addUser(Subject s) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		ModCore.bot.sendChatMessage("Registered %s user as %s", s.player.getDisplayNameString(), s.getClassification().name);
		users.add(s);
	}
	
	public static Subject getUser(EntityPlayer pl) {
		for(Subject u : users) {
			if(u.player.getUniqueID().toString().equals(pl.getUniqueID().toString()))
				return u;
		}
		return null;
	}
	
	public static Subject getUser(String uuid) {
		for(Subject u : users) {
			if(u.player.getUniqueID().toString().equals(uuid))
				return u;
		}
		return null;
	}
	
	public static Classification getClassification(String id) {
		id = id.toUpperCase();
		switch (id) {
			case "IRR": return IRRELEVANT;
			case "ASST": return ASSET;
			case "ADM": return ADMIN;
			case "AN": return ANALOG_INTERFACE;
			case "THR": return THREAT;
			case "PRTHR": return PRIMARY_THREAT;
			default: return IRRELEVANT;
		}
	}
	
	public static boolean isRegistered(Subject s) {
		for(Subject b : users) {
			if(b.player.getUniqueID().toString().equals(s.player.getUniqueID().toString()))
				return true;
		}
		return false;
	}
}
