package com.creatix.mc.thebot.bot;

import java.io.IOException;

import com.creatix.mc.thebot.mod.ModCore;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class UserHandler {
	
    /**@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e){
		if(ModCore.isBotActive) {
			try {
				if(UserUtils.getUser(e.player.getUniqueID().toString()) == null)
					UserUtils.reloadUser(UserUtils.initUser(e.player, e.player.getServer()));
				} catch (JsonIOException | JsonSyntaxException | IOException e1) {
					e1.printStackTrace();
				}
		}
	}**/

}
