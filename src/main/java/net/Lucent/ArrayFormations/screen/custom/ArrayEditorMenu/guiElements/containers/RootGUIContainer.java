package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.ContainerRenderable;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.GUIContainer;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.ToolTipRenderable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class RootGUIContainer extends GUIContainer implements Clickable, Hoverable, ToolTipRenderable {

    ArrayHolderGUIContainer arrayHolder = new ArrayHolderGUIContainer(
        320,
            432,
            (1920)/4,
            (1080)/2,
            1.5,
            this

    );

    EditorGuiContainer editorHolder =     new EditorGuiContainer(
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
                    "textures/gui/programmable_gui/menu_background/formation_plate_menu_background.png"),
            320,432,
            (1920)/2,(1080)/2,
            1.5,this

    );

    @Override
    public void init(int screenWidth, int screenHeight) {
        super.init(screenWidth,screenHeight);
        arrayHolder.init(screenWidth,screenHeight);
        editorHolder.init(screenWidth,screenHeight);
    }


    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        //guiGraphics.disableScissor();
        editorHolder.renderScaled(guiGraphics,mouseX,mouseY,partialTick,canInteract);
        arrayHolder.renderScaled(guiGraphics,mouseX,mouseY,partialTick,canInteract);
        renderToolTip(guiGraphics, ( int) mouseX,(int) mouseY,partialTick);
        guiGraphics.pose().popPose();
    }

    public RootGUIContainer(int rawWidth, int rawHeight, int x, int y) {
        super(rawWidth,rawHeight,x,y);

        this.scaled = false;
    }

    public void editorRenderableClicked(ContainerRenderable clicked){

    }

    public void  addArrayElement(String name){
            editorHolder.addArrayElement(name);
    }

    @Override
    public boolean click(double mouseX, double mouseY) {
        if(arrayHolder.isMouseOver(mouseX,mouseY)) return arrayHolder.click(mouseX,mouseY);
        if(editorHolder.isMouseOver(mouseX,mouseY)) return editorHolder.click(mouseX,mouseY);
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX>this.x && mouseX < this.x+rawWidth*getTotalScaling()/scalar && mouseY > this.y && mouseY < this.y+rawHeight*getTotalScaling()/scalar;
    }

    public String tooltipToRender = null;

    public void setTooltipToRender(String tooltipToRender) {
        this.tooltipToRender = tooltipToRender;
    }

    @Override
    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(tooltipToRender == null) return;
        guiGraphics.renderTooltip(Minecraft.getInstance().font,Component.literal(tooltipToRender) ,mouseX,mouseY);
        tooltipToRender = null;
    }
}
