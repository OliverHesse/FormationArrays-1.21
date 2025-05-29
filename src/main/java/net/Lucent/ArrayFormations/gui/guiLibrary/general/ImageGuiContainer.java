package net.Lucent.ArrayFormations.gui.guiLibrary.general;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ImageGuiContainer extends  GUIContainer{

    private final ResourceLocation backgroundTexture;
    public ImageGuiContainer(ResourceLocation backgroundTexture, int rawWidth, int rawHeight, int x, int y) {
        this(backgroundTexture,rawWidth,rawHeight,x,y,1,null);
    }
    public ImageGuiContainer(ResourceLocation backgroundTexture, int rawWidth, int rawHeight, int x, int y,double defaultScale,GUIContainer parent  ) {
        super(rawWidth, rawHeight, x, y,defaultScale,parent);
        this.backgroundTexture = backgroundTexture;
    }


    @Override
    public double getScaledX() {
        return x;
    }

    @Override
    public double getScaledY() {
        return y;
    }

    @Override
    public void init(int screenWidth, int screenHeight) {
        super.init(screenWidth, screenHeight);


        //only really important for when the screen is resized
        x = (int) (((float) rawX/1920)*screenWidth - rawWidth*getTotalScaling()/(scalar*2));
        y = (int) (((float) rawY/1070)*screenHeight- rawHeight*getTotalScaling()/(scalar*2));

    }

    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        guiGraphics.blit(backgroundTexture,0,0,0,0,rawWidth,rawHeight,rawWidth,rawHeight);
        super.render(guiGraphics,mouseX,mouseY,partialTick,canInteract);
    }
}
