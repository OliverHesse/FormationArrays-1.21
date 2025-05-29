package net.Lucent.ArrayFormations.screen.custom.testingScreen;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.GUIContainer;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.ImageGuiContainer;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.TextureImage;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers.EditorGuiContainer;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers.RootGUIContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class TestingScreenScreen extends AbstractContainerScreen<TestingScreenMenu> {

    RootGUIContainer container = new RootGUIContainer(1920,1080,0,0);

    List<GUIContainer> containers = new ArrayList<>();

    public TestingScreenScreen(TestingScreenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);


    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return container.click(mouseX,mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected void init() {
        super.init();
        container.init(width,height);

    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        container.init(width,height);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        container.renderScaled(guiGraphics,mouseX,mouseY,partialTick,true);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }
}
