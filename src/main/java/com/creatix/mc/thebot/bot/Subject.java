package com.creatix.mc.thebot.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import jline.internal.InputStreamReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class Subject {

	public EntityPlayer player;
	public MinecraftServer server;
	private Classification clazz;
	public String uuid;
	public File info;
	public Subject(EntityPlayer player, MinecraftServer server, File f) {
		this.player = player;
		this.server = server;
		this.uuid = player.getUniqueID().toString();
		this.info = f;
	}
	
	public JsonObject getJSON() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return new JsonParser().parse(new InputStreamReader(new FileInputStream(info))).getAsJsonObject();
	}
	
	public Classification getClassification() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return UserUtils.getClassification(getJSON().get("class").getAsString());
	}
	
	public String getUIN() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return getJSON().get("uin").getAsString();
	}
	
	public int getRelations() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return getJSON().get("relation").getAsInt();
	}
	
	public List<JsonArray> getTags() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return Arrays.asList(getJSON().get("tags").getAsJsonArray());
	}
	
	public String getConclusion() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return getJSON().get("conclusion").getAsString();
	}
	
	public String getStatus() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return getJSON().get("status").getAsString();
	}
}
