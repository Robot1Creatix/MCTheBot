package com.creatix.mc.thebot.mod.registry;

import com.creatix.mc.thebot.mod.blocks.PS4;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {
	
	public static Block ps4;
	
	public static void init() {
		GameRegistry.registerBlock(ps4 = new PS4());
	}

}
