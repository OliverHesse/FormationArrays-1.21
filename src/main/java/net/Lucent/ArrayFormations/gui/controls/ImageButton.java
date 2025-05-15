package net.Lucent.ArrayFormations.gui.controls;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.util.TextureBlitData;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.function.IntConsumer;


public class ImageButton extends ButtonBase{
    public TextureBlitData textureBlitData;
    public ImageButton(TextureBlitData textureBlitData, int x, int y, int width, int height, IntConsumer onClick) {
        super(x, y, width, height,onClick);
        this.textureBlitData = textureBlitData;

    }


    private TextureSubSection getActiveTexture(){
        if(this.isFocused) return textureBlitData.pressedTexture;
        if(this.isHovered) return textureBlitData.hoverTexture;
        return textureBlitData.unFocusedTexture;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        TextureSubSection texture = getActiveTexture();

        guiGraphics.blit(textureBlitData.textureLoc,x,y,texture.u,texture.v,texture.width,texture.height,textureBlitData.textureWidth,textureBlitData.textureHeight);

    }

    public static ImageButton createImageButton(ResourceLocation data, TextureSubSection unf, TextureSubSection hov, TextureSubSection f,
                                                int x, int y, int tWidth, int tHeight, int width, int height,IntConsumer onClick){
        TextureBlitData textureData = new TextureBlitData(
                data,
                tWidth,
                tHeight,
                unf,
                hov,
                f

        );
        return new ImageButton(
                textureData,
                x,
                y,
                width,
                height,
                onClick
        );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (!isMouseOver(mouseX, mouseY)) {
            return false;
        }
        onClick.accept(button);
        setFocused(!isFocused);
        return true;
    }
    @Override
    public NarrationPriority narrationPriority() {
        return null;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }
}
