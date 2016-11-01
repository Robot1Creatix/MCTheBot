package com.creatix.mc.thebot.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jline.internal.InputStreamReader;

import com.creatix.mc.thebot.bot.utils.GodModeThread;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class Subject {

	public EntityPlayer player;
	public MinecraftServer server;
	public String uuid;
	public File info;
	public GodModeThread gmt;
	public Subject(EntityPlayer player, MinecraftServer server, File f) {
		this.player = player;
		this.server = server;
		this.uuid = player.getUniqueID().toString();
		this.info = f;
		this.gmt = new GodModeThread(this, server);
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
	
	public JsonArray getTags() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return getJSON().get("tags").getAsJsonArray();
	}
	
	public String getConclusion() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return getJSON().get("conclusion").getAsString();
	}
	
	public String getStatus() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return getJSON().get("status").getAsString();
	}
	public void increaseRelations(int c) throws JsonIOException, JsonSyntaxException, IOException {
		JsonObject o = getJSON();
		o.addProperty("relation", getRelations()+c);
		updateJson(o);
	}
	public void decreaseRelations(int c) throws JsonIOException, JsonSyntaxException, IOException {
		JsonObject o = getJSON();
		o.addProperty("relation", getRelations()-c);
		updateJson(o);
	}
	
	public void addTags(String ...tags) throws JsonIOException, JsonSyntaxException, IOException {
		JsonObject o = getJSON();
		JsonArray arr = getTags();
		for(String s : tags){
			arr.add(new JsonPrimitive(s));
		}
		o.add("tags", arr);
		updateJson(o);
	}
	public void setStatus(String status) throws JsonIOException, JsonSyntaxException, IOException{
		JsonObject o = getJSON();
		o.addProperty("status", status);
		updateJson(o);
	}
	public void setConclusion(String conc) throws JsonIOException, JsonSyntaxException, IOException {
		JsonObject o = getJSON();
		o.addProperty("conclusion", conc);
		updateJson(o);
	}
	public void setUIN(String uin) throws JsonIOException, JsonSyntaxException, IOException {
		JsonObject o = getJSON();
		o.addProperty("uin", uin);
		updateJson(o);
	}
	public boolean containsTag(String tag) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonArray arr = getTags();
		for(JsonElement e : arr) {
			if(e.getAsString().equals(tag))
				return true;
		}
		return false;
	}
	protected void updateJson(JsonObject o) throws IOException {
		FileWriter w = new FileWriter(info);
		w.write(new GsonBuilder().setPrettyPrinting().create().toJson(o));
		w.close();
	}
}
