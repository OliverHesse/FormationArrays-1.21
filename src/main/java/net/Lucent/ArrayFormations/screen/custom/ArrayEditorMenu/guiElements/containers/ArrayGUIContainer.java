package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers;

import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.GUIContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;

public class ArrayGUIContainer extends GUIContainer implements Clickable, Hoverable {
    public ResourceLocation texture;
    public int textureWidth;
    public int textureHeight;
    public String name;
    public String flavour;
    public String description;


    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        if(isMouseOver(mouseX,mouseY)){
            guiGraphics.fill(-2,-2,rawWidth+2,rawHeight+2,-1);
            guiGraphics.fill(0,0,rawWidth,rawHeight,-4025762);
        }
        guiGraphics.blit(texture,0,0,0,0,textureWidth,textureHeight,textureWidth,textureHeight);

        guiGraphics.drawString(Minecraft.getInstance().font,name,(rawWidth/4)+40,10,-16777216,false);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(((float) rawWidth /4)+40,30,0);
        guiGraphics.pose().scale(0.9f,0.9f,0);
        guiGraphics.drawWordWrap(Minecraft.getInstance().font, (FormattedText) Component.literal(flavour+"\n"+description), 0,0,rawWidth*3/4-20 ,-16777216);
        guiGraphics.pose().popPose();
    }


    public ArrayGUIContainer(ResourceLocation texture,int width,int height,int textureWidth,int textureHeight,String name,String flavour,String description,int x,int y,ArrayHolderGUIContainer parent){
        super(width,height,x,y,1,parent);
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight =textureHeight;
        this.name = name;
        this.flavour = flavour;
        this.description = description;
        this.scaled = false;


    }

    @Override
    public boolean click(double mouseX, double mouseY) {
        ((ArrayHolderGUIContainer) parent).ArrayContainerClicked(this);
        return true;
    }

}
