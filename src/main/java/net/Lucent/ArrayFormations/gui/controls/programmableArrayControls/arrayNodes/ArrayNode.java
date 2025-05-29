package net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.arrayNodes;

import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.DataChannelSocket;
import net.Lucent.ArrayFormations.gui.util.ArrayClickResult;
import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class ArrayNode implements Renderable, GuiEventListener {




    public ResourceLocation texture;

    public Boolean isHeld = false;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Font font;
    protected int textureWidth;
    protected int textureHeight;
    protected List<DataChannelSocket> dataChannelSockets;

    int containerX;
    int containerY;
    int containerWidth;
    int containerHeight;

    public ProgrammableFormationEditorScreen screen;
    String name;

    public ArrayNode(ResourceLocation texture, int x, int y, int width, int height,int textureWidth,int textureHeight, List<DataChannelSocket> dataChannelButtons
            ,int containerX,int containerY,int containerWidth,int containerHeight,String name,ProgrammableFormationEditorScreen screen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.texture = texture;
        this.dataChannelSockets = dataChannelButtons;
        this.containerHeight = containerHeight;
        this.containerWidth = containerWidth;
        this.containerX = containerX;
        this.containerY = containerY;
        this.name = name;
        this.screen = screen;
        for(DataChannelSocket socket:dataChannelButtons){
            socket.setGlobalCords(x,y);
        }

    }
    public void setPos(int x,int y){
        this.x = x;
        this.y = y;

    }
    public boolean withinContainer(int mouseX,int mouseY){
         return (mouseX+width/2 < containerX+containerWidth && mouseX-width/2 > containerX) &&
                 (mouseY+height/2 < containerY+containerHeight && mouseY-height/2 > containerY);
    }

    public ArrayNode copy(){
        return new ArrayNode(texture,x,y,width,height,textureWidth,textureHeight,dataChannelSockets,containerX,containerY,containerWidth,containerHeight,name,screen);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if(isHeld && withinContainer(mouseX,mouseY) && screen.menuMode.equals("normal")){
            this.x = mouseX-this.width/2;
            this.y = mouseY-this.height/2;
        }

        guiGraphics.blit(texture,x,y,0,0,width,height,textureWidth,textureHeight);
        boolean tooltipShown = false;
        for(DataChannelSocket socket : dataChannelSockets){
            socket.setGlobalCords(x,y);
            socket.render(guiGraphics,mouseX,mouseY,partialTick);
            if(socket.isMouseOver(mouseX,mouseY) && screen.menuMode.equals("normal")){
                socket.renderToolTip(guiGraphics,mouseX,mouseY);
                tooltipShown  = true;
            }
        }
        if(isMouseOver(mouseX,mouseY) && !tooltipShown && screen.menuMode.equals("normal")){
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(name),mouseX,mouseY);
        }

    }

    @Override
    public void setFocused(boolean focused) {

    }


    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }


    public ArrayClickResult mouseClicked(double mouseX, double mouseY){
        for(DataChannelSocket socket: dataChannelSockets){
            if(socket.isMouseOver(mouseX,mouseY)) {
                socket.isConnecting = !socket.isConnecting;
                System.out.println("socket status: "+ socket.isConnecting);
                return new ArrayClickResult(ArrayClickResult.Result.CHANNEL_CLICKED,this,socket);
            }
        }
        if (!isMouseOver(mouseX, mouseY)) {
            return new ArrayClickResult(ArrayClickResult.Result.NOTHING,this,null);       }
        isHeld = !isHeld;
        return new ArrayClickResult(ArrayClickResult.Result.ARRAY_CLICKED,this,null);
    }



    @Override
    public boolean isFocused() {
        return false;
    }



}
