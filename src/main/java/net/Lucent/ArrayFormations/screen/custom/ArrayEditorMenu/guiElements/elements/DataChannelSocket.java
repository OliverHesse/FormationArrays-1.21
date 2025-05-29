package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.elements;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.ContainerRenderable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Cloneable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;
import net.Lucent.ArrayFormations.gui.guiLibrary.controls.TextureImageButton;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.ToolTipRenderable;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers.EditorGuiContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DataChannelSocket extends TextureImageButton implements Clickable, Hoverable, Cloneable<DataChannelSocket>, ToolTipRenderable {


    //the socket this is for
    public String socketConnection;

    public boolean isInput = false;


    public DataChannelSocket(ResourceLocation texture, int x, int y, int textureWidth, int textureHeight,
                             TextureSubSection defaultSection,
                             TextureSubSection hoverSection,
                             TextureSubSection pressedSection,
                             ContainerRenderable parent,
                             String socketConnection,
                             boolean isInput) {
        super(texture, x, y, textureWidth, textureHeight, defaultSection, hoverSection, pressedSection,parent);
        this.isInput = isInput;
        this.socketConnection= socketConnection;

    }

    public static DataChannelSocket createX16(int x, int y, ContainerRenderable parent, String socketConnection, Boolean isInput){
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/programmable_gui/array_images/array_buttons/data_channel_socket_button_16.png");
        TextureSubSection defaultSection = new TextureSubSection(0,16*(isInput ? 0 : 1),16,16);
        TextureSubSection hoverSection = new TextureSubSection(16,16*(isInput ? 0 : 1),16,16);
        return new DataChannelSocket(texture,x,y,32,32,defaultSection,hoverSection,defaultSection,parent,socketConnection,isInput);
    }
    public static DataChannelSocket createX8(int x, int y, ContainerRenderable parent, String socketConnection, Boolean isInput){
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/programmable_gui/array_images/array_buttons/data_channel_socket_button_8.png");
        TextureSubSection defaultSection = new TextureSubSection(0,8*(isInput ? 0 :1),8,8);
        TextureSubSection hoverSection = new TextureSubSection(8,8*(isInput ? 0 :1),8,8);
        return new DataChannelSocket(texture,x,y,16,16,defaultSection,hoverSection,defaultSection,parent,socketConnection,isInput);
    }
    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        TextureSubSection active = getActiveSubSection(mouseX,mouseY);
        guiGraphics.blit(texture,x,y,active.u,active.v,active.width,active.height,textureWidth,textureHeight);

    }


    @Override
    public boolean click(double mouseX, double mouseY) {
        ((EditorGuiContainer) parent).dataChannelSocketClicked(this);
        return false;
    }


    @Override
    public DataChannelSocket clone() {

        return new DataChannelSocket(
                texture,
                x,
                y,
                textureWidth,
                textureHeight,
                defaultSection,
                hoverSection,
                pressedSection,
                parent,
                socketConnection,
                isInput
        );
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        System.out.println("x: " + getGlobalX() + " y: " + getGlobalY());
        System.out.println("w: "+ getScaledWidth() + " h: "+getScaledHeight());
        return super.isMouseOver(mouseX, mouseY);
    }

    @Override
    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(socketConnection),mouseX,mouseY);
    }
}
