package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.client.gui.containers.ContainerBasicCoil;
import buildcraftAdditions.client.gui.containers.ContainerChargingStation;
import buildcraftAdditions.client.gui.containers.ContainerCoolingTower;
import buildcraftAdditions.client.gui.containers.ContainerFluidicCompressor;
import buildcraftAdditions.client.gui.containers.ContainerHeatedFurnace;
import buildcraftAdditions.client.gui.containers.ContainerItemSorter;
import buildcraftAdditions.client.gui.containers.ContainerKEB;
import buildcraftAdditions.client.gui.containers.ContainerKineticMultiTool;
import buildcraftAdditions.client.gui.containers.ContainerKineticTool;
import buildcraftAdditions.client.gui.containers.ContainerMachineConfigurator;
import buildcraftAdditions.client.gui.containers.ContainerPipeColoringTool;
import buildcraftAdditions.client.gui.containers.ContainerPortableLaser;
import buildcraftAdditions.client.gui.containers.ContainerRefinery;
import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemKineticTool;
import buildcraftAdditions.items.Tools.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.items.Tools.ItemPoweredBase;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileCoolingTower;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {


		TileEntity tile = world.getTileEntity(x, y, z);

		switch (Variables.Gui.values()[ID]) {
			case FLUIDIC_COMPRESSOR:
				if (tile instanceof TileFluidicCompressor)
					return new GuiFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			case CHARGING_STATION:
				if (tile instanceof TileChargingStation)
					return new GuiChargingStation(player.inventory, (TileChargingStation) tile);
			case KINETIC_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemKineticTool) {
					ItemKineticTool tool = (ItemKineticTool) player.getCurrentEquippedItem().getItem();
					return new GuiKineticTool(player.inventory, tool, ItemPoweredBase.getInventory(player), player.getCurrentEquippedItem(), player);
				}
			case KINETIC_MULTI_TOOL:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemKineticMultiTool)
					return new GuiKineticMultiTool(player.inventory, new InventoryKineticMultiTool(player.getHeldItem()));
			case PORTABLE_LASER:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPortableLaser)
					return new GuiPortableLaser(player.inventory, new InventoryPortableLaser(player.getHeldItem()));
			case HEATED_FURNACE:
				if (tile instanceof TileHeatedFurnace)
					return new GuiHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
			case BASIC_COIL:
				if (tile instanceof TileBasicCoil)
					return new GuiBasicCoil(player.inventory, (TileBasicCoil) tile);
			case KEB:
				if (tile instanceof TileKineticEnergyBufferBase)
					return new GuiKEB((TileKineticEnergyBufferBase) tile, player);
			case MACHINE_CONFIGURATOR:
				if (tile instanceof IMultiBlockTile) {
					IMultiBlockTile multiblock = (IMultiBlockTile) tile;
					if (!multiblock.isPartOfMultiblock())
						return null;
					if (!multiblock.isMaster())
						tile = world.getTileEntity(multiblock.getMasterX(), multiblock.getMasterY(), multiblock.getMasterZ());
				}
				if (tile instanceof IConfigurableOutput || tile instanceof IUpgradableMachine)
					return new GuiMachineConfigurator(player.inventory, tile);
			case REFINERY:
				if (tile instanceof TileRefinery)
					return new GuiRefinery(player.inventory, (TileRefinery) tile);
			case COOLING_TOWER:
				if (tile instanceof TileCoolingTower)
					return new GuiCoolingTower(player.inventory, (TileCoolingTower) tile);
			case ITEM_SORTER:
				if (tile instanceof TileItemSorter)
					return new GuiItemSorter(player.inventory, (TileItemSorter) tile);
			case PIPE_COLORING_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPipeColoringTool)
					return new GuiPipeColoringTool(player.getCurrentEquippedItem());
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
	                                  int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);

		switch (Variables.Gui.values()[ID]) {
			case FLUIDIC_COMPRESSOR:
				if (tile instanceof TileFluidicCompressor)
					return new ContainerFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			case CHARGING_STATION:
				if (tile instanceof TileChargingStation)
					return new ContainerChargingStation(player.inventory, (TileChargingStation) tile);
			case KINETIC_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemKineticTool) {
					ItemKineticTool tool = (ItemKineticTool) player.getCurrentEquippedItem().getItem();
					return new ContainerKineticTool(player.inventory, tool, ItemPoweredBase.getInventory(player), player.getCurrentEquippedItem(), player);
				}
			case KINETIC_MULTI_TOOL:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemKineticMultiTool)
					return new ContainerKineticMultiTool(player.inventory, new InventoryKineticMultiTool(player.getHeldItem()));
			case PORTABLE_LASER:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPortableLaser)
					return new ContainerPortableLaser(player.inventory, new InventoryPortableLaser(player.getHeldItem()));
			case HEATED_FURNACE:
				if (tile instanceof TileHeatedFurnace)
					return new ContainerHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
			case BASIC_COIL:
				if (tile instanceof TileBasicCoil)
					return new ContainerBasicCoil(player.inventory, (TileBasicCoil) tile);
			case KEB:
				if (tile instanceof TileKineticEnergyBufferBase)
					return new ContainerKEB(player, (TileKineticEnergyBufferBase) tile);
			case MACHINE_CONFIGURATOR:
				return new ContainerMachineConfigurator(player.inventory, tile);
			case REFINERY:
				return new ContainerRefinery(player.inventory, (TileRefinery) tile);
			case COOLING_TOWER:
				return new ContainerCoolingTower(player.inventory, (TileCoolingTower) tile);
			case ITEM_SORTER:
				return new ContainerItemSorter(player.inventory, (TileItemSorter) tile);
			case PIPE_COLORING_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPipeColoringTool)
					return new ContainerPipeColoringTool();
		}
		return null;
	}

}
