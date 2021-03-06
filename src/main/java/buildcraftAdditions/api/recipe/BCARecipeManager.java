package buildcraftAdditions.api.recipe;

import buildcraftAdditions.api.recipe.duster.IDusterRecipeManager;
import buildcraftAdditions.api.recipe.refinery.ICoolingTowerRecipeManager;
import buildcraftAdditions.api.recipe.refinery.IRefineryRecipeManager;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public final class BCARecipeManager {

	public static IDusterRecipeManager duster;

	public static IRefineryRecipeManager refinery;

	public static ICoolingTowerRecipeManager cooling;

	private BCARecipeManager() {
	}

}
