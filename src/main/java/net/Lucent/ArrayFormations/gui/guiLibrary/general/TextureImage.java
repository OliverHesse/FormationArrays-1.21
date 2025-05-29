package net.Lucent.ArrayFormations.gui.guiLibrary.general;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class TextureImage extends SquareRenderable{



    public ResourceLocation texture;
    public int textureWidth;
    public int textureHeight;

    public TextureImage(ResourceLocation texture,int x,int y,int textureWidth,int textureHeight,ContainerRenderable parent){
        this.x = x;
        this.y = y;
        this.rawX = x;
        this.rawY = y;
        this.parent = parent;
        this.texture =texture;
        this.textureHeight = textureHeight;
        this.textureWidth = textureWidth;
        this.rawHeight = textureHeight;
        this.rawWidth = textureWidth;

    }



    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(x,y,0);
        guiGraphics.blit(texture,0,0,0,0,textureWidth,textureHeight,textureWidth,textureHeight);

        pose.popPose();
    }




}
