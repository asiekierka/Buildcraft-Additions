package buildcraftAdditions.items.Tools;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.core.EnumColor;
import buildcraft.api.transport.IPipeTile;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemPipeColoringTool extends Item {

	public static final String[] names = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemPipeColoringTool() {
		setMaxStackSize(1);
		setUnlocalizedName("pipeColoringTool");
		setHasSubtypes(true);
		setCreativeTab(BuildcraftAdditions.bcadditions);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking())
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.PIPE_COLORING_TOOL.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("SortMode", Constants.NBT.TAG_BYTE) && stack.stackTagCompound.getBoolean("SortMode")) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof IPipeTile) {
				return setColor(stack.getItemDamage(), (IPipeTile) tile);
			}
		} else {
			return world.getBlock(x, y, z).recolourBlock(world, x, y, z, ForgeDirection.UNKNOWN, stack.getItemDamage());
		}
		return false;
	}

	//TODO: Find something better for this!
	protected boolean setColor(int color, IPipeTile pipeTile) {
		try {
			Method setColor = pipeTile.getPipe().getClass().getMethod("setColor", EnumColor.class);
			setColor.invoke(pipeTile.getPipe(), EnumColor.values()[15 - color]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("SortMode", Constants.NBT.TAG_BYTE) && !stack.stackTagCompound.getBoolean("SortMode") && entity instanceof EntitySheep) {
			EntitySheep sheep = (EntitySheep) entity;
			if (!sheep.getSheared() && sheep.getFleeceColor() != stack.getItemDamage()) {
				sheep.setFleeceColor(stack.getItemDamage());
				player.swingItem();
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SortMode") && stack.getTagCompound().getBoolean("SortMode"))
			list.add(Utils.localize("tooltip.colorSortingMode"));
		else
			list.add(Utils.localize("tooltip.colorNormalMode"));
		list.add(Utils.localize("gui.color." + Utils.COLOR_NAMES[stack.getItemDamage()]));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		icons = new IIcon[names.length];
		for (int i = 0; i < names.length; i++)
			icons[i] = register.registerIcon("buildcraft:triggers/color_" + names[i]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return icons[meta];
	}
}
