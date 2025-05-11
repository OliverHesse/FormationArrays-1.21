package net.Lucent.ArrayFormations.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.custom.FormationCoreBlock;
import net.Lucent.ArrayFormations.gui.controls.ImageButton;
import net.Lucent.ArrayFormations.gui.util.TextureBlitData;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.Lucent.ArrayFormations.network.custom.SyncFormationCoreStatePayload;
import net.minecraft.client.gui.GuiGraphics;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ISystemReportExtender;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class FormationCoreScreen extends AbstractContainerScreen<FormationCoreMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "textures/gui/formation_core/formation_core_1_slot.png");
    private static final ResourceLocation SPRITE_TEXTURES =
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "textures/gui/formation_core/formation_core_button_sprites.png");

    private static final ResourceLocation BUTTON_TEXTURES  =
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/sprites/formation_core_gui_rotate_btn.png");
    private final TextureSubSection BUTTON_UNFOCUSED =
            new TextureSubSection(0,0,18,18);
    private final TextureSubSection BUTTON_HOVER =
            new TextureSubSection(18,0,18,18);
    private final TextureSubSection BUTTON_PRESSED  =
            new TextureSubSection(0,18,18,18);

    private static final ResourceLocation STATE_BUTTON_TEXTURES =
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/sprites/formation_core_change_state_btn_sprites.png");
    private final TextureSubSection STATE_BUTTON_UNFOCUSED =
            new TextureSubSection(0,0,48,13);
    private final TextureSubSection STATE_BUTTON_HOVER =
            new TextureSubSection(0,13,48,13);
    private final TextureSubSection STATE_BUTTON_PRESSED  =
            new TextureSubSection(0,25,48,13);

    public final int BUTTON_PRESS_TIME_TICKS = 10;
    public Map<Integer, Integer> buttonsClickTime = new HashMap<Integer, Integer>();
    public Map<Integer, Boolean> buttonStates = new HashMap<Integer, Boolean>();
    public Map<Integer, ImageButton> rotationButtons = new HashMap<Integer, ImageButton>();

    public ImageButton stateButton = null;
    public Integer stateButtonClickTime = 0;
    public Boolean stateButtonState = true;


    public FormationCoreScreen(FormationCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        for(int i=0;i<menu.formationCore.MAX_ARRAY_BLUEPRINTS();i++){
            buttonStates.put(i,false);


        }
    }




    public void renderInventorySlots(GuiGraphics guiGraphics,int x, int y){
        int maxSlots = menu.formationCore.MAX_ARRAY_BLUEPRINTS();
        int xOffset = 18;

        for(int i = 1; i<maxSlots; i++){
            guiGraphics.blit(SPRITE_TEXTURES,x+61+xOffset*i, y+34, 19, 22,18, 18,64,64);
        }
    }
    //true = pressed false = not pressed
    public void renderInventorySlotButtons(GuiGraphics guiGraphics,int mouseX, int mouseY,float partialTick,int x,int y){
        if(rotationButtons.size() != menu.formationCore.MAX_ARRAY_BLUEPRINTS()){
            for(int i = 0; i <  menu.formationCore.MAX_ARRAY_BLUEPRINTS();i++){
                int xOffset = 18;
                rotationButtons.put(i, ImageButton.createImageButton(
                        BUTTON_TEXTURES,
                        BUTTON_UNFOCUSED,
                        BUTTON_HOVER,
                        BUTTON_PRESSED,
                        x+61+xOffset*i,
                        y+54,
                        48,
                        48,
                        18,
                        18,
                        button ->{
                            System.out.println("button clicked to rotate slot");
                        }
                ));

            }
        }
        for(int i = 0; i < rotationButtons.size(); i++){
            int xOffset = 18;

            if(rotationButtons.get(i).isFocused()){
                buttonsClickTime.put(i,1+buttonsClickTime.getOrDefault(i,0));
                if(buttonsClickTime.get(i) >= BUTTON_PRESS_TIME_TICKS){
                    buttonsClickTime.put(i,0);
                    rotationButtons.get(i).setFocused(false);

                }
            }

            rotationButtons.get(i).render(guiGraphics,mouseX,mouseY,partialTick);

        }

    }


    //TODO change to just be a solid texture with baked in text or no text
    public void renderChangeActiveStateButton(GuiGraphics guiGraphics,int mouseX,int mouseY,float partialTick,int x, int y){
        if(stateButton == null){
            System.out.println("CREATING STATE BTN");
            stateButton = ImageButton.createImageButton(
                    STATE_BUTTON_TEXTURES,
                    STATE_BUTTON_UNFOCUSED,
                    STATE_BUTTON_HOVER,
                    STATE_BUTTON_PRESSED,
                    x+123,
                    y+5 ,
                    48,
                    48,
                    48,
                    13,
                    button ->{

                        System.out.println("changing button state");
                        AbstractFormationCoreBlockEntity block = menu.formationCore;
                        PacketDistributor.sendToServer(new SyncFormationCoreStatePayload(
                                !block.getBlockState().getValue(FormationCoreBlock.FORMATION_CORE_STATE),
                                block.getBlockPos()
                        ));

                    }
            );
        }

        if(stateButton.isFocused()){

            stateButtonClickTime += 1;
            if(stateButtonClickTime > BUTTON_PRESS_TIME_TICKS){
                stateButtonClickTime = 0;
                stateButton.setFocused(false);
            }
        }
        stateButton.render(guiGraphics,mouseX,mouseY,partialTick);

        if(menu.formationCore.getBlockState().getValue(FormationCoreBlock.FORMATION_CORE_STATE)){
            guiGraphics.drawString(this.font,"Active",x+123+7,y+8,0,false);
        } else{
            guiGraphics.drawString(this.font,"Inactive",x+123+5,y+8,0,false);
        }



    }

    public void renderQiBar(){}

    public void renderFlagRotations(){}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderInventorySlotButtons(guiGraphics,pMouseX,pMouseY,pPartialTick,x,y);
        renderInventorySlots(guiGraphics,x,y);

        renderChangeActiveStateButton(guiGraphics,pMouseX,pMouseY,pPartialTick,x,y);
        renderFlagRotations();
        renderQiBar();

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        System.out.println("just why man");
        for(ImageButton btn : rotationButtons.values()){
            btn.mouseClicked(mouseX,mouseY,button);
        }
        stateButton.mouseClicked(mouseX,mouseY,button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

}
