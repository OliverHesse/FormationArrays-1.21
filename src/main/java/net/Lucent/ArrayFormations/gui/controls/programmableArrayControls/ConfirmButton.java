package net.Lucent.ArrayFormations.gui.controls.programmableArrayControls;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.arrayNodes.ArrayNode;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ConfirmButton {

    public ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/programmable_gui/arrays/confirm_button.png");
    public TextureSubSection DEFAULT = new TextureSubSection(0,0,34,9);
    public TextureSubSection HOVER = new TextureSubSection(0,9,34,9);
    public TextureSubSection PRESSED = new TextureSubSection(0,18,34,9);

    public int PRESS_TIME = 10;

    public boolean isPressed = false;
    public int timeSincePressed = 0;

    public int x;
    public int y;
    public int relativeX;
    public int relativeY;

    public ProgrammableFormationEditorScreen screen;

    public ConfirmButton(int x, int y, ProgrammableFormationEditorScreen screen) {
        this.x = x;
        this.y = y;
        this.relativeX = x;
        this.relativeY = y;
        this.screen = screen;


    }

    public void setPos(int x,int y){
        this.x = x + relativeX;
        this.y = y + relativeY;
    }

    public void mouseClicked(){
        screen.resolveInputData();
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY){
        if(isPressed){
            guiGraphics.blit(TEXTURE,x,y,PRESSED.u,PRESSED.v,PRESSED.width,PRESSED.height,34,27);
            timeSincePressed += 1;
            if(timeSincePressed > PRESS_TIME){
                isPressed = false;
                timeSincePressed = 0;
            }
            return;
        }
        if(isMouseOver(mouseX,mouseY)){
            guiGraphics.blit(TEXTURE,x,y,HOVER.u,HOVER.v,HOVER.width,HOVER.height,34,27);
            return;
        }
        guiGraphics.blit(TEXTURE,x,y,DEFAULT.u,DEFAULT.v,DEFAULT.width,DEFAULT.height,34,27);


    }




    //buttons are circular
    public boolean isMouseOver(double mouseX,double mouseY){
        return ((mouseX > this.x) && mouseX < this.x+34) && (mouseY>this.y && mouseY< this.y+9);


    }
}
