package net.Lucent.ArrayFormations.gui.controls.programmableArrayControls;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DataChannelSocket {

    public int x;
    public int y;
    public int relativeX;
    public int relativeY;
    public boolean isInputNode = true;

    public ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/programmable_gui/arrays/node_connection_buttons.png");
    public TextureSubSection INPUT_NODE_NO_HOVER = new TextureSubSection(0,0,8,8);
    public TextureSubSection INPUT_NODE_HOVER = new TextureSubSection(8,0,8,8);
    public TextureSubSection OUTPUT_NODE_NO_HOVER = new TextureSubSection(0,8,8,8);
    public TextureSubSection OUTPUT_NODE_HOVER = new TextureSubSection(8,8,8,8);


    public int textureWidth = 16;
    public int textureHeight = 16;

    public String connection;

    public boolean isConnecting;

    public List<DataChannelSocket> connections;


    public List<DataChannelSocket> connectedNodes = new ArrayList<>();

    public void addConnectionNode(DataChannelSocket socket){
        connectedNodes.add(socket);
    }
    public void removeConnectedNode(DataChannelSocket socket){
        connectedNodes.remove(socket);
    }

    public DataChannelSocket(int relativeX,int relativeY,boolean isInputNode,String connection){
        x = relativeX;
        y = relativeY;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.isInputNode = isInputNode;
        this.connection = connection;
    }
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if(isConnecting){

            guiGraphics.vLine(this.x+4,this.y+4,mouseY,-14877488);
            guiGraphics.hLine(this.x+4,mouseX,mouseY,-14877488);


        }
        for(DataChannelSocket connectionNode:connectedNodes){
            guiGraphics.vLine(this.x+4,this.y+4,connectionNode.y,-14877488);
            guiGraphics.hLine(this.x+4,connectionNode.x,connectionNode.y,-14877488);


        }

        if(isInputNode){
            TextureSubSection section = INPUT_NODE_HOVER;
            if(!isMouseOver(mouseX,mouseY)){
                section = INPUT_NODE_NO_HOVER;
            }
            guiGraphics.blit(TEXTURE,x,y,section.u,section.v,section.width,section.height,16,16);
        }else{
            TextureSubSection section = OUTPUT_NODE_HOVER;
            if(!isMouseOver(mouseX,mouseY)){
                section = OUTPUT_NODE_NO_HOVER;
            }
            guiGraphics.blit(TEXTURE,x,y,section.u,section.v,section.width,section.height,16,16);

        }
    }

    public void setGlobalCords(int xOffset,int yOffset){
        x = relativeX+ xOffset;
        y = relativeY + yOffset;

    }



    public void renderToolTip(GuiGraphics guiGraphics,int mouseX,int mouseY){
        guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(connection),mouseX,mouseY);

    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + 7 && mouseY >= y && mouseY < y + 7;
    }


}
