package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu;

import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers.RootGUIContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArrayEditorScreen extends AbstractContainerScreen<ArrayEditorMenu> {

    RootGUIContainer rootGUIContainer;



    public ArrayEditorScreen(ArrayEditorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }



}
