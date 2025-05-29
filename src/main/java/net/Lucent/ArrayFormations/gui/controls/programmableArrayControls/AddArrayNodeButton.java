package net.Lucent.ArrayFormations.gui.controls.programmableArrayControls;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.arrayNodes.ArrayNode;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorMenu;
import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class AddArrayNodeButton {

    public ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/programmable_gui/arrays/array_option_button.png");
    public TextureSubSection DEFAULT = new TextureSubSection(0,0,126,32);
    public TextureSubSection HOVER = new TextureSubSection(0,32,126,32);
    public TextureSubSection PRESSED = new TextureSubSection(0,64,126,32);
    public int PRESS_TIME = 10;

    public boolean isPressed = false;
    public int timeSincePressed = 0;

    public int x;
    public int y;
    public ProgrammableFormationEditorScreen screen;
    public AbstractFormationNode nodeTemplate;
    public ArrayNode guiNodeTemplate;

    public AddArrayNodeButton(int x, int y, ProgrammableFormationEditorScreen screen, AbstractFormationNode nodeTemplate, ArrayNode guiNodeTemplate){
        this.x  = x;
        this.y = y;
        this.screen = screen;
        this.nodeTemplate =nodeTemplate;
        this.guiNodeTemplate = guiNodeTemplate;

    }
    public boolean isMouseOver(double mouseX,double mouseY,int slot){
        return ((mouseX > this.x) && mouseX < this.x+126) && (mouseY>this.y+32*slot && mouseY< this.y+32+32*slot);


    }
    public void drawString(GuiGraphics guiGraphics,int offset){
        PoseStack poseStack = guiGraphics.pose();

        poseStack.pushPose();
        // Move to where you want to draw text
        poseStack.scale(0.5F, 0.5F, 1.0F);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, nodeTemplate.getDisplayName(),(x+73)*2,(y+16+32*offset)*2,0);
        poseStack.popPose();
    }
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY,int offset){
        if(isPressed){
            guiGraphics.blit(TEXTURE,x,y+32*offset,PRESSED.u,PRESSED.v,PRESSED.width,PRESSED.height,126,96);
            drawString(guiGraphics,offset);
            timeSincePressed += 1;
            if(timeSincePressed > PRESS_TIME){
                isPressed = false;
                timeSincePressed = 0;
            }
            return;
        }
        if(isMouseOver(mouseX,mouseY,offset)){
            guiGraphics.blit(TEXTURE,x,y+32*offset,HOVER.u,HOVER.v,HOVER.width,HOVER.height,126,96);
            drawString(guiGraphics,offset);
            return;
        }
        guiGraphics.blit(TEXTURE,x,y+32*offset,DEFAULT.u,DEFAULT.v,DEFAULT.width,DEFAULT.height,126,96);
        drawString(guiGraphics,offset);

    }

    public void clicked(){
        screen.addArrayNode(guiNodeTemplate,nodeTemplate);
    }
}
