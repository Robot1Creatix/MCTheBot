package com.creatix.mc.thebot.mod.proxies;

import com.creatix.mc.thebot.mod.registry.BlockRegistry;
import com.creatix.mc.thebot.mod.utils.Constants;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}
	
	@Override
	public void Init(FMLInitializationEvent e) {
		super.Init(e);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(BlockRegistry.ps4), 0,  new ModelResourceLocation(Constants.MOD_ID+":ps4", "inventory") );
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
}
