package com.creatix.mc.thebot.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class PS4 extends Block{

	public PS4() {
		super(Material.IRON);
		this.setCreativeTab(CreativeTabs.MISC);
		this.setHardness(1F);
		this.setRegistryName("ps4");
		this.setUnlocalizedName("ps4");
	}
	
	
	
}
