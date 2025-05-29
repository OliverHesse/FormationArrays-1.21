package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.elements;

import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.*;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.TextureImage;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Cloneable;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers.EditorGuiContainer;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers.RootGUIContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ArrayElement extends TextureImage implements Draggable, Clickable, Hoverable, Cloneable<ArrayElement> {

    List<DataChannelSocket> connections;

    String name;

    public ArrayElement(ResourceLocation texture, int x, int y, int textureWidth, int textureHeight,EditorGuiContainer parent,List<DataChannelSocket>connections,String name) {
        super(texture, x, y, textureWidth, textureHeight,parent);
        for(DataChannelSocket connection : connections){
            connection.parent = this;
        }
        this.connections = connections;
        this.renderables.addAll(connections);
        this.name = name;
    }


    @Override
    public boolean isHeld() {
        return false;
    }

    @Override
    public boolean isWithinBounds(double mouseX, double mouseY) {
        return false;
    }

    @Override
    public boolean click(double mouseX, double mouseY) {
        for(DataChannelSocket connection: connections){
            if(connection.isMouseOver(mouseX,mouseY)){
                ((EditorGuiContainer) parent).dataChannelSocketClicked(connection);
                return true;
            }

        }
        ((EditorGuiContainer) parent).setHeldArray(this);
        return true;
    }


    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        super.render(guiGraphics, mouseX, mouseY, partialTick, canInteract);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x,y,0);
        boolean tooltipShown = false;
        for(DataChannelSocket socket:connections){
            socket.render(guiGraphics,mouseX,mouseY,partialTick,canInteract);

            if(socket.isMouseOver(mouseX,mouseY)){

                ((RootGUIContainer) parent.parent).setTooltipToRender(socket.socketConnection);

                tooltipShown = true;
            }
        }
        guiGraphics.pose().popPose();
        if(!tooltipShown && isMouseOver(mouseX,mouseY)){
            ((RootGUIContainer) parent.parent).setTooltipToRender(name);

        }


    }

    @Override
    public ArrayElement clone() {
        List<DataChannelSocket> clonedSockets = new ArrayList<>();
        for(DataChannelSocket socket : connections){
            clonedSockets.add(socket.clone());
        }

        return new ArrayElement(texture,x,y,textureWidth,textureHeight,(EditorGuiContainer) parent,clonedSockets,name);
    }


}
