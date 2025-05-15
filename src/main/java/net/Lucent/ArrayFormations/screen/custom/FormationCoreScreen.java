package net.Lucent.ArrayFormations.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.custom.BaseFormationCoreBlock;
import net.Lucent.ArrayFormations.gui.controls.ImageButton;
import net.Lucent.ArrayFormations.gui.controls.RotateItemButton;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.Lucent.ArrayFormations.network.custom.serverbound.SyncFormationCoreStatePayload;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.client.gui.GuiGraphics;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;

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

    private static final ResourceLocation QI_AMOUNT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/sprites/formation_core_qi_basic_display.png");


    public class SlotDisplayData{
        public int buttonClickTime = 0;
        public ImageButton rotatedButton = null;


    }


    public final int BUTTON_PRESS_TIME_TICKS = 10;

    public Map<Integer,SlotDisplayData> SlotDisplayDataHolder = new HashMap<>();

    public ImageButton stateButton = null;
    public Integer stateButtonClickTime = 0;

    public FormationCoreScreen(FormationCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        for(int i=0;i<menu.formationCore.inventory.getSlots();i++){

            SlotDisplayDataHolder.put(i,new SlotDisplayData());

        }
    }


    public void renderQiAmount(GuiGraphics guiGraphics, int x, int y,int mouseX,int mouseY){


        //i could definitely make this prettier

        int img_height = ((int) Math.floor((53 * (((double) menu.getCurrentQiPercent()) / 100))));

        int locx = x + 9;
        int locy = y + 18+(53-img_height);
        int img_width = 13;

        guiGraphics.blit(QI_AMOUNT_TEXTURE,locx,locy,0,0,img_width,img_height,13,53);
        if(mouseX> locx && mouseX < locx+img_width &&  mouseY>y+18 && mouseY<y+70){
            guiGraphics.renderTooltip(this.font,Component.literal(menu.formationCore.qiContainer.getQi().toString()),mouseX,mouseY);
        }
    }


    public void renderInventorySlots(GuiGraphics guiGraphics,int x, int y){
        int maxSlots = menu.formationCore.inventory.getSlots();
        int xOffset = 18;

        for(int i = 1; i<maxSlots; i++){
            guiGraphics.blit(SPRITE_TEXTURES,x+61+xOffset*i, y+34, 19, 22,18, 18,64,64);
        }
    }
    //true = pressed false = not pressed
    public void renderInventorySlotButtons(GuiGraphics guiGraphics,int mouseX, int mouseY,float partialTick,int x,int y){
        if(SlotDisplayDataHolder.get(0).rotatedButton == null){
            for(int i = 0; i <  menu.formationCore.inventory.getSlots();i++){
                int xOffset = 18;
                SlotDisplayDataHolder.get(i).rotatedButton= RotateItemButton.createRotateButton(
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
                        i,
                        menu.formationCore
                );

            }
        }
        for(int i = 0; i < SlotDisplayDataHolder.size(); i++){
            int xOffset = 18;

            SlotDisplayData slotData = SlotDisplayDataHolder.get(i);
            if(slotData.rotatedButton.isFocused()){
                slotData.buttonClickTime = 1+ slotData.buttonClickTime;
                if( slotData.buttonClickTime  >= BUTTON_PRESS_TIME_TICKS){
                    slotData.buttonClickTime = 0;
                    slotData.rotatedButton.setFocused(false);

                }
            }

            slotData.rotatedButton.render(guiGraphics,mouseX,mouseY,partialTick);

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
                                !block.getBlockState().getValue(BaseFormationCoreBlock.FORMATION_CORE_STATE),
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

        if(menu.formationCore.getBlockState().getValue(BaseFormationCoreBlock.FORMATION_CORE_STATE)){
            guiGraphics.drawString(this.font,"Active",x+123+7,y+8,0,false);
        } else{
            guiGraphics.drawString(this.font,"Inactive",x+123+5,y+8,0,false);
        }



    }



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

        renderQiAmount(guiGraphics,x,y,pMouseX,pMouseY);

    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {

        ItemStack itemStack = slot.getItem();
        if(!itemStack.is(ModTags.Items.ARRAY_BLUEPRINT)){
            super.renderSlot(guiGraphics, slot);
            return;
            }
        if(slot.index < menu.TE_INVENTORY_FIRST_SLOT_INDEX) {
            super.renderSlot(guiGraphics, slot);
            return;
        }
        int offset = slot.index-menu.TE_INVENTORY_FIRST_SLOT_INDEX;


        int rotation = menu.formationCore.blueprintSlotData.get(offset).rotation;


        PoseStack poseStack = guiGraphics.pose();
        float angleDegrees = 90.0f*rotation;
        Quaternionf qRotation = Axis.ZP.rotationDegrees(angleDegrees);
        Quaternionf qRotation2 = Axis.ZP.rotationDegrees(-angleDegrees);
        poseStack.rotateAround(qRotation,slot.x+8,slot.y+8,0);

        guiGraphics.renderItem(itemStack,slot.x,slot.y);

        poseStack.rotateAround(qRotation2,slot.x+8,slot.y+8,0);

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        for(SlotDisplayData btn : SlotDisplayDataHolder.values()){
            btn.rotatedButton.mouseClicked(mouseX,mouseY,button);
        }
        stateButton.mouseClicked(mouseX,mouseY,button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

}
