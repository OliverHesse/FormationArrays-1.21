package net.Lucent.ArrayFormations.gui.util;

import net.minecraft.resources.ResourceLocation;

public class TextureBlitData {
    public ResourceLocation textureLoc;
    public int x;
    public int y;
    public int textureWidth;
    public int textureHeight;

    public  TextureSubSection unFocusedTexture;
    public TextureSubSection hoverTexture;
    public TextureSubSection pressedTexture;
    public TextureBlitData(ResourceLocation textureLoc,int textureWidth, int textureHeight,TextureSubSection unFocusedTexture,TextureSubSection hoverTexture,TextureSubSection pressedTexture){
        this.textureLoc = textureLoc;
        this.unFocusedTexture = unFocusedTexture;
        this.hoverTexture = hoverTexture;
        this.pressedTexture = pressedTexture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }




}
