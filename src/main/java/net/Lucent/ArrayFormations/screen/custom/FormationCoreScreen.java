package net.Lucent.ArrayFormations.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.custom.FormationCoreBlock;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormationCoreScreen extends AbstractContainerScreen<FormationCoreMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "textures/gui/formation_core/formation_core_1_slot.png");
    private static final ResourceLocation SPRITE_TEXTURES =
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "textures/gui/formation_core/sprites/formation_core_button_sprites.png");

    public final int BUTTON_PRESS_TIME_TICKS = 20;
    public Map<Integer, Integer> buttonsClickTime = new HashMap<Integer, Integer>();
    public Map<Integer, Boolean> buttonStates = new HashMap<Integer, Boolean>();

    public FormationCoreScreen(FormationCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        for(int i=0;i<menu.formationCore.MAX_ARRAY_BLUEPRINTS();i++){
            buttonStates.put(i,false);

        }
    }


    public void renderInventorySlots(GuiGraphics guiGraphics){
        int maxSlots = menu.formationCore.MAX_ARRAY_BLUEPRINTS();
        int xOffset = 18;
        int yOffset = 18;
        for(int i = 1; i<maxSlots; i++){
            guiGraphics.blit(SPRITE_TEXTURES,62+xOffset*i, 35+yOffset*i, 19, 22,18, 18);
        }
    }
    //true = pressed false = not pressed
    public void renderInventorySlotButtons(GuiGraphics guiGraphics){

        for(int i = 0; i < buttonStates.size();i++){
            int xOffset = 18;
            int yOffset = 18;
            int uOffset = 1;
            int vOffset = 22;
            if(buttonStates.get(i)){
                vOffset = 40;
                buttonsClickTime.put(i,1+buttonsClickTime.getOrDefault(i,0));
            }

            guiGraphics.blit(SPRITE_TEXTURES,61+xOffset*i,54+yOffset*i,uOffset,vOffset,18,18);

        }

    }

    //true is being pressed
    //true is active
    public void renderChangeActiveStateButton(GuiGraphics guiGraphics,boolean isPressed,boolean state){
        int uOffset = 1;
        int vOffset = 1;
        if(isPressed){
            vOffset = 11;
        }
        guiGraphics.blit(SPRITE_TEXTURES,144,5,uOffset,vOffset,26,10);
    }

    public void renderQiBar(){}

    public void renderFlagRotations(){}

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderInventorySlots(guiGraphics);
        renderInventorySlotButtons(guiGraphics);
        renderChangeActiveStateButton(guiGraphics,false,false);
        renderFlagRotations();
        renderQiBar();
    }


}
