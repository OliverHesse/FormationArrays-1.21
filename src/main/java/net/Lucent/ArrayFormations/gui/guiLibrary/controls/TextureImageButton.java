package net.Lucent.ArrayFormations.gui.guiLibrary.controls;

import net.Lucent.ArrayFormations.gui.guiLibrary.general.ContainerRenderable;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.TextureImage;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.minecraft.resources.ResourceLocation;

public abstract class TextureImageButton extends TextureImage implements Clickable, Hoverable {


    public boolean pressed;
    public int PRESS_TIME = 10;
    public int timeSincePress = 0;

    public TextureSubSection defaultSection;
    public TextureSubSection hoverSection;
    public TextureSubSection pressedSection;

    public TextureImageButton(ResourceLocation texture, int x, int y, int textureWidth, int textureHeight,
                              TextureSubSection defaultSection, TextureSubSection hoverSection, TextureSubSection pressedSection, ContainerRenderable parent){
        super(texture,x,y,textureWidth,textureHeight,parent);
        this.defaultSection = defaultSection;
        this.hoverSection = hoverSection;
        this.pressedSection = pressedSection;
        this.rawWidth = defaultSection.width;
        this.rawHeight = defaultSection.height;

    }

    public TextureSubSection getActiveSubSection(double mouseX,double mouseY){
        if(pressed){
            timeSincePress += 1;
            if(timeSincePress > PRESS_TIME){
                pressed = false;
                timeSincePress = 0;
            }
            return pressedSection;
        }
        if(isMouseOver(mouseX,mouseY)) return hoverSection;
        return defaultSection;
    }


    public TextureImageButton(ResourceLocation texture, int x, int y, int textureWidth, int textureHeight,ContainerRenderable parent) {

        this(texture, x, y, textureWidth, textureHeight, new TextureSubSection(0,0,textureWidth,textureHeight),
                new TextureSubSection(0,0,textureWidth,textureHeight),
                new TextureSubSection(0,0,textureWidth,textureHeight),parent);
    }


}