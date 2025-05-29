package net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.*;
import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.arrayNodes.ArrayNode;
import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.arrayNodes.DataArrayNode;
import net.Lucent.ArrayFormations.gui.util.ArrayClickResult;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.conditionalNodes.ListInputContainsCondition;
import net.Lucent.ArrayFormations.programmableArrays.constantNodes.ConstantInputNode;
import net.Lucent.ArrayFormations.programmableArrays.coreNodes.GenericBarrierCoreFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.sensoryNodes.EntityDetectionNode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgrammableFormationEditorScreen  extends AbstractContainerScreen<ProgrammableFormationEditorMenu> {
    public List<AddArrayNodeButton> addButtons;
    public ArrayClickResult lastClickResult = null;
    public List<DataChannel> dataChannels = new ArrayList<>();
    public ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
            "textures/gui/programmable_gui/background.png");

    public ResourceLocation INPUT_MENU = ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
            "textures/gui/programmable_gui/arrays/input_data_area.png"
            );

    public String menuMode = "normal";

    public ConfirmButton confirmButton = null;
    public InputArea inputArea = null;


    public Map<ArrayNode, AbstractFormationNode> nodeToNode = new HashMap<>();


    public ProgrammableFormationEditorScreen(ProgrammableFormationEditorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    public List<ArrayNode> nodes = null;

    public void renderDataChannels(GuiGraphics guiGraphics,int x, int y,int mouseX,int mouseY, float partialTick){
        for(DataChannel channel:dataChannels){
            channel.render(guiGraphics,mouseX,mouseY,partialTick);
        }
    }

    public void renderNodes(GuiGraphics guiGraphics,int x, int y,int mouseX,int mouseY, float partialTick){
        /*
        if(nodes == null){
            nodes = List.of(
                    new ArrayNode(
                            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
                                    "textures/gui/programmable_gui/arrays/core_array.png"),
                          x+320,
                            y+190,
                            64,
                            64,
                            64,
                            64,
                            List.of(
                                    new DataChannelSocket(
                                            11,
                                            8,
                                            true,
                                            "canRun"
                                    )
                            ),
                            x+128,
                            y+1,
                            399,
                            421,
                            "5 Element Barrier Formation",this

                    ),
                    new DataArrayNode(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
                            "textures/gui/programmable_gui/arrays/simple_array.png"),
                            x+250,
                            y+120,
                            32,
                            32,
                            32,
                            32,
                                    x+128,
                                    y+1,
                                    399,
                                    421,
                                    "Heavens Constant",this

                    )
            );
        }
        */
        for(ArrayNode node: nodeToNode.keySet()){
            node.render(guiGraphics,mouseX,mouseY,partialTick);
        }
        /*
        for(ArrayNode node:nodes){
            node.render(guiGraphics,mouseX,mouseY,partialTick);
        }*/
    }

    public void renderInputMenu(GuiGraphics guiGraphics,int x, int y,int mouseX,int mouseY, float partialTick){
        if(confirmButton == null){
            confirmButton = new ConfirmButton(width/2-32+15,height/2-16+19,this);
        }
        if(inputArea == null){
            inputArea = new InputArea(width/2-32,height/2-16);
            inputArea.setValue(getNodeValue(selectedNode));
        }
        guiGraphics.fill(0,0,width,height,2013265920);
        guiGraphics.blit(INPUT_MENU,width/2 - 32,height/2-16,0,0,64,32,64,32);
        inputArea.render(guiGraphics,mouseX,mouseY,partialTick);
        confirmButton.render(guiGraphics,mouseX,mouseY);
    }

    public void renderArrayOptions(GuiGraphics guiGraphics,int x,int y,int mouseX,int mouseY, float partialTick){

        for(int i =0;i<addButtons.size();i++){
            addButtons.get(i).render(guiGraphics,mouseX,mouseY,i);
        }

    }

    @Override
    protected void init() {

        int x = (width - 528) / 2;
        int y = (height - 516) / 2;

        addButtons = List.of(
                new AddArrayNodeButton(x,y,this,
                        new GenericBarrierCoreFormationNode(),
                        new ArrayNode(  ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
                                "textures/gui/programmable_gui/arrays/core_array.png"),
                                x+320,
                                y+190,
                                64,
                                64,
                                64,
                                64,
                                List.of(
                                        new DataChannelSocket(
                                                11,
                                                8,
                                                true,
                                                "canRun"
                                        ),

                                        new DataChannelSocket(
                                                50,
                                                5,
                                                true,
                                                "barrierRadius"
                                        )

                                ),
                                x+128,
                                y+1,
                                399,
                                421,
                                "5 Element Barrier Formation",this)),
                new AddArrayNodeButton(x,y,this,
                        new ConstantInputNode<>(),
                        new DataArrayNode(
                                ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
                                        "textures/gui/programmable_gui/arrays/simple_array.png"),
                                        x+250,
                                        y+120,
                                        32,
                                        32,
                                        32,
                                        32,
                                        x+128,
                                        y+1,
                                        399,
                                        421,
                                        "Heavens Constant",this


                        )),
                new AddArrayNodeButton(x,y,this,
                        new ListInputContainsCondition(),
                        new ArrayNode(
                                ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
                                        "textures/gui/programmable_gui/arrays/simple_array.png"),
                                x+320,
                                y+190,
                                32,
                                32,
                                32,
                                32,
                                List.of(
                                        new DataChannelSocket(
                                                2,
                                                2,
                                                true,
                                                "input1"
                                        ),
                                        new DataChannelSocket(
                                                22,
                                                2,
                                                true,
                                                "input2"
                                        ),
                                        new DataChannelSocket(
                                                12,
                                                28,
                                                false,
                                                "evaluate"
                                        )
                                ),
                                x+128,
                                y+1,
                                399,
                                421,
                                "Heavenly Scrying Formation",this
                        )),
                new AddArrayNodeButton(x,y,this,
                        new EntityDetectionNode(),
                        new ArrayNode(
                                ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,
                                        "textures/gui/programmable_gui/arrays/simple_array.png"),
                                x+320,
                                y+190,
                                32,
                                32,
                                32,
                                32,
                                List.of(
                                        new DataChannelSocket(
                                                12,
                                                28,
                                                false,
                                                "nearbyEntities"
                                        )

                                ),
                                x+128,
                                y+1,
                                399,
                                421,
                                "Heavenly Detection Formation",this
                        ))
        );
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        int x = (width - 528) / 2;
        int y = (height - 516) / 2;


        guiGraphics.blit(BACKGROUND_TEXTURE,x,y,0,0,528,516,528,516);
        renderNodes(guiGraphics,x,y,mouseX,mouseY,partialTick);
        renderDataChannels(guiGraphics,x,y,mouseX,mouseY,partialTick);
        renderArrayOptions(guiGraphics,x,y,mouseX,mouseY,partialTick);
        if(menuMode.equals("input")){
            renderInputMenu(guiGraphics,x,y,mouseX,mouseY,partialTick);
        }
    }

    public boolean doesChannelAlreadyExist(DataChannelSocket socket1,DataChannelSocket socket2){
        for(DataChannel channel: dataChannels){
            if(channel.isDataChannelWithNodes(socket1,socket2)) return true;
        }
        return false;
    }


    public void addArrayNode(ArrayNode guiNode,AbstractFormationNode node){
        ArrayNode newGUINode = guiNode.copy();
        AbstractFormationNode newNode = node.copy();

        int x = (width - 528) / 2;
        int y = (height - 516) / 2;
        newGUINode.setPos(x+320,y+190);
        nodeToNode.put(newGUINode,newNode);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(menuMode.equals("normal")) {
            for(int i =0;i<addButtons.size();i++){
                if(addButtons.get(i).isMouseOver(mouseX,mouseY,i)){
                    addButtons.get(i).clicked();
                }
            }
            //ordering is based of most recent one added
            for (ArrayNode node : nodeToNode.keySet()) {
                ArrayClickResult result = node.mouseClicked(mouseX, mouseY);
                if (result.result == ArrayClickResult.Result.NOTHING) {
                    continue;
                }
                if (lastClickResult == null) {
                    lastClickResult = result;
                    return super.mouseClicked(mouseX, mouseY, button);
                }
                if(lastClickResult.result == ArrayClickResult.Result.OTHER){
                    lastClickResult = result;
                    return super.mouseClicked(mouseX, mouseY, button);
                }
                if(lastClickResult.result == ArrayClickResult.Result.ARRAY_CLICKED){
                    lastClickResult = result;
                    return super.mouseClicked(mouseX, mouseY, button);
                }
                if (lastClickResult.result == ArrayClickResult.Result.CHANNEL_CLICKED && result.result == ArrayClickResult.Result.CHANNEL_CLICKED) {
                    System.out.println("Adding connection");
                    boolean socket1Type = lastClickResult.socket.isInputNode;
                    boolean socket2Type = result.socket.isInputNode;
                    System.out.println("socket 1 type: " + socket1Type);
                    System.out.println("socket 2 type: " + socket2Type);

                    if (!((socket1Type && socket2Type) || (!socket1Type && !socket2Type))) {
                        if (!doesChannelAlreadyExist(lastClickResult.socket, result.socket)) {
                            if(socket1Type){
                                nodeToNode.get(lastClickResult.node).addNodeConnection(
                                        lastClickResult.socket.connection,
                                        nodeToNode.get(result.node),
                                        nodeToNode.get(lastClickResult.node),
                                        result.socket.connection);
                            }else{
                                nodeToNode.get(result.node).addNodeConnection(
                                        result.socket.connection,
                                        nodeToNode.get(lastClickResult.node),
                                        nodeToNode.get(result.node),
                                        lastClickResult.socket.connection);
                            }

                            dataChannels.add(new DataChannel(
                                    lastClickResult.socket,
                                    result.socket,
                                    this
                            ));
                        }
                    }
                    lastClickResult.socket.isConnecting = false;
                    result.socket.isConnecting = false;
                    lastClickResult = null;
                }
            }

        }else{
            if(confirmButton.isMouseOver(mouseX,mouseY)){
                confirmButton.mouseClicked();
                return  super.mouseClicked(mouseX, mouseY, button);
            }
            if(inputArea.editBox.isMouseOver(mouseX,mouseY)){
                inputArea.setEditable(true);
                inputArea.setFocused(true);
                inputArea.editBox.active = true;


            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    ArrayNode selectedNode;

    public void startGetInputData(ArrayNode node){
        menuMode = "input";
        selectedNode = node;

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        System.out.println("key pressed");
        if(menuMode.equals("input") && inputArea.editBox.isFocused()){
            return inputArea.keyPressed(keyCode,scanCode,modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    public String getNodeValue(ArrayNode node){
        ConstantInputNode<?> value =  ((ConstantInputNode<?>) nodeToNode.get(selectedNode));
        if(value.data != null) return value.data.toString();
        return null;
    }


    public void resolveInputData(){

        ((ConstantInputNode) nodeToNode.get(selectedNode)).setData(inputArea.getValue());

        menuMode = "normal";
        selectedNode = null;
        confirmButton = null;
        inputArea = null;

    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if(menuMode.equals("input") && inputArea.editBox.isFocused()){
            inputArea.editBox.charTyped(codePoint,modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }
}

