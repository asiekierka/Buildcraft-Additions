package buildcraftAdditions.items;

import net.minecraft.item.Item;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

public class ItemBase extends Item {

	private final String name;

	public ItemBase(String name) {
		this.name = name;
		setUnlocalizedName(name);
		setTextureName(Variables.MOD.ID + ":" + name);
		setCreativeTab(BuildcraftAdditions.bcadditions);
	}

	public String getName() {
		return name;
	}
}
