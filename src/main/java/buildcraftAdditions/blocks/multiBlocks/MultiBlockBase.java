package buildcraftAdditions.blocks.multiBlocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class MultiBlockBase extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon icon[];
	public char identifier;
	public MultiBlockPatern patern;
	public String textureName, secondTexture;

	public MultiBlockBase(char identifier, MultiBlockPatern patern, String textureName) {
		this(identifier, patern, textureName, "multiBlockSeeInvisible");
	}

	public MultiBlockBase(char identifier, MultiBlockPatern patern, String textureName, String secondTexture) {
		super(Material.iron);
		setHardness(4f);
		setHarvestLevel(null, 0);
		this.identifier = identifier;
		this.patern = patern;
		this.textureName = textureName;
		this.secondTexture = secondTexture;
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		icon = new IIcon[2];
		icon[0] = RenderUtils.registerIcon(register, textureName);
		icon[1] = RenderUtils.registerIcon(register, secondTexture);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icon[meta];
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, 80);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.getBlockMetadata(x, y, z) == 0) {
			patern.checkPatern(world, x, y, z);
			world.scheduleBlockUpdate(x, y, z, this, 80);
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}


	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMultiBlockTile) {
			return ((IMultiBlockTile) entity).onBlockActivated(player);
		}
		return false;
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMultiBlockTile)
			((IMultiBlockTile) entity).invalidateMultiblock();
	}

	@Override
	public abstract TileEntity createNewTileEntity(World world, int meta);

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
