package buildcraftAdditions.client.gui.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.client.gui.containers.ContainerKEB;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.SpecialListMananger;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiKEB extends GuiContainer {
	public ResourceLocation texture = new ResourceLocation("bcadditions","textures/gui/KineticEnergyBuffer.png");
	private TileKineticEnergyBufferBase keb;
	private GuiButton north, east, south, west, up, down;
	private boolean primed, yellow, green;
	private EntityPlayer player;
	private int teller;


	public GuiKEB(TileKineticEnergyBufferBase keb, EntityPlayer player) {
		super(new ContainerKEB(keb, player));
		this.keb = keb;
		primed = false;
		this.player = player;
		teller = 30;
		green = SpecialListMananger.greenButtonList.contains(player.getDisplayName());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		long percent = ((long) keb.energy * 248) / keb.maxEnergy;

		drawTexturedModalRect(guiLeft + 67, guiTop + 30, 176, 162, 47, 47);
		if (primed)
			teller--;
		if (teller <= 0) {
			teller = 30;
			yellow = !yellow;
		}

		if (yellow) {
			if (!green)
			drawTexturedModalRect(guiLeft + 67, guiTop + 30, 176, 115, 47, 47);
			else
				drawTexturedModalRect(guiLeft + 67, guiTop + 30, 176, 209, 47, 47);
		}

		int temp = (int) percent;
		if (temp > 36)
			temp = 36;
		drawTexturedModalRect(guiLeft + 90, guiTop + 17, 215, 42, temp, 11);
		percent -= 36;
		if (percent <= 0)
			return;
		temp =(int) percent;
		if (temp > 62)
			temp = 62;
		drawTexturedModalRect(guiLeft + 115, guiTop + 28, 241, 53, 11, temp);
		percent -= 62;
		if (percent <= 0)
			return;
		temp = (int) percent;
		if (temp > 61)
			temp = 61;
		drawTexturedModalRect(guiLeft + 115 - temp, guiTop + 79, 241 - temp, 104, temp , 11);
		percent -= 61;
		if (percent <= 0)
			return;
		temp = (int) percent;
		if (percent > 62)
			temp = 62;
		drawTexturedModalRect(guiLeft + 54, guiTop + 79 - temp, 180, 104 - temp, 11, temp);
		percent -=62;
		if (percent <= 0)
			return;
		drawTexturedModalRect(guiLeft + 65, guiTop + 17, 191, 42, (int) percent, 11);


	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		north = new GuiButton(1, guiLeft + 38, guiTop + 92, 50, 20, "");
		east = new GuiButton(1, guiLeft + 38, guiTop + 115, 50, 20, "");
		south = new GuiButton(1, guiLeft + 38, guiTop + 138, 50, 20, "");
		west = new GuiButton(1, guiLeft + 120, guiTop + 92, 50, 20, "");
		up = new GuiButton(1, guiLeft + 120, guiTop + 115, 50, 20, "");
		down = new GuiButton(1, guiLeft + 120, guiTop + 138, 50, 20, "");
		buttonList.add(north);
		buttonList.add(east);
		buttonList.add(south);
		buttonList.add(west);
		buttonList.add(up);
		buttonList.add(down);
		updateStrings();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRendererObj.drawString(Utils.localize("tile.blockKEBT" + keb.tier + ".name"), 5, 6, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.north") + ": ", 5, 97, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.east") + ": ", 5, 120, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.south") + ": ", 5, 143, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.west") + ": ", 93, 97, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.up") + ": ", 93, 120, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.down") + ": ", 93, 143, 0x404040);
		if (shouldDrawEnergyNumber(mouseX - guiLeft, mouseY - guiTop)) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(keb.energy + " / " + keb.maxEnergy + " RF");
			this.drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
		}
		if (shouldDrawWarning(mouseX - guiLeft, mouseY - guiTop)) {
			ArrayList<String> list = new ArrayList<String>();
			if (!primed) {
				if (SpecialListMananger.specialTexts.containsKey(player.getDisplayName())){
					list.add(SpecialListMananger.specialTexts.get(player.getDisplayName()) + "?");
				} else {
					list.add(Utils.localize("gui.keb.dangerousButton"));
					list.add(Utils.localize("gui.keb.noPushing"));
				}
			} else {
				if (SpecialListMananger.specialTexts.containsKey(player.getDisplayName())){
					list.add(SpecialListMananger.specialTexts.get(player.getDisplayName()) + "!!!");
				} else {
					list.add(Utils.localize("gui.keb.pressForBoom"));
				}
			}
			drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
		}
	}

	private boolean shouldDrawEnergyNumber(int mouseX, int mouseY) {
		if (mouseX > 54 && mouseX < 125 && mouseY > 13 && mouseY < 27)
			return true;
		if (mouseX > 54 && mouseX < 65 && mouseY > 13 && mouseY < 90)
			return true;
		if (mouseX > 54 && mouseX < 125 && mouseY > 75 && mouseY < 90)
			return true;
		return mouseX > 110 && mouseX < 125 && mouseY > 13 && mouseY < 90;
	}

	private boolean shouldDrawWarning(int mouseX, int mouseY) {
		return mouseX > 66 && mouseX < 114 && mouseY > 28 && mouseY < 78;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		ForgeDirection direction = ForgeDirection.UNKNOWN;
		if (button == north) {
			direction = ForgeDirection.NORTH;
		} else if (button == east) {
			direction = ForgeDirection.EAST;
		} else if (button == south) {
			direction = ForgeDirection.SOUTH;
		} else if (button == west) {
			direction = ForgeDirection.WEST;
		} else if (button == up) {
			direction = ForgeDirection.UP;
		} else if (button == down) {
			direction = ForgeDirection.DOWN;
		}
		keb.changeStatus(direction);
		updateStrings();
		keb.sendConfigurationToSever();
	}

	private void updateStrings() {
		north.displayString = keb.getStatus(ForgeDirection.NORTH).getText();
		east.displayString = keb.getStatus(ForgeDirection.EAST).getText();
		south.displayString = keb.getStatus(ForgeDirection.SOUTH).getText();
		west.displayString = keb.getStatus(ForgeDirection.WEST).getText();
		up.displayString = keb.getStatus(ForgeDirection.UP).getText();
		down.displayString = keb.getStatus(ForgeDirection.DOWN).getText();
	}

	@Override
	protected void mouseClicked(int x, int y, int state) {
		super.mouseClicked(x, y, state);
		if (!player.getDisplayName().equals(keb.owner))
			return;
		if (x > 191 && x < 239 && y > 66 && y < 115) {
			if (primed)
				keb.activateSelfDestruct();
			else
				primed = true;
		}
	}

}
