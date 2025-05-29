package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers;

import com.mojang.datafixers.util.Pair;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.ContainerRenderable;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.ImageGuiContainer;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.coreNodes.GenericBarrierCoreFormationNode;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.elements.ArrayElement;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.elements.DataChannelRenderable;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.elements.DataChannelSocket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditorGuiContainer extends ImageGuiContainer implements Clickable, Hoverable {

    public boolean creatingDataChannel = false;
    public DataChannelSocket lastClickedSocket = null;
    public DataChannelRenderable currentDataChannel = null;

    public List<DataChannelRenderable> existingDataChannels = new ArrayList<>();

    public ArrayElement heldArrayElement;

    public Map<ArrayElement,AbstractFormationNode> formationNodeMap = new HashMap<>();

    //TODO improve to be completly constructed from json file
    public Map<String, Pair<ArrayElement, AbstractFormationNode>> nameToArrayElementMap = new HashMap<>(){{
        put("5 Element Barrier Array",new Pair<>(new ArrayElement(
                ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/gui/programmable_gui/array_images/5_element_barrier_array.png"),
                0,
                0,
                64,
                64,
                EditorGuiContainer.this,
                List.of(
                    DataChannelSocket.createX16(9,6,EditorGuiContainer.this,"canRun",true),
                    DataChannelSocket.createX16(39,6,EditorGuiContainer.this,"barrierRadius",true)
                ),
                "5 Element Barrier Array"
        ),new GenericBarrierCoreFormationNode()));
    }};

    public EditorGuiContainer(ResourceLocation backgroundTexture, int rawWidth, int rawHeight, int x, int y,double defaultScale,RootGUIContainer parent) {
        super(backgroundTexture, rawWidth, rawHeight, x, y,defaultScale,parent);
    }

    public boolean socketsOfSameType(DataChannelSocket socket1,DataChannelSocket socket2){
        return socket1.isInput == socket2.isInput;
    }



    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        if(heldArrayElement != null){
            heldArrayElement.x = getRelativeMouseX(mouseX);
            heldArrayElement.rawX = getRelativeMouseX(mouseX);
            heldArrayElement.y = getRelativeMouseY(mouseY);
            heldArrayElement.rawY = getRelativeMouseY(mouseY);
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick, canInteract);

    }

    public void addArrayElement(String name){
        ArrayElement element = nameToArrayElementMap.get(name).getFirst().clone();
        AbstractFormationNode node = nameToArrayElementMap.get(name).getSecond().copy();
        renderables.add(element);
        formationNodeMap.put(element,node);
        element.rawX = this.rawWidth*3/4;
        element.x = this.rawWidth*3/4;
        element.y = this.rawHeight*2/4;
        element.rawY = this.rawHeight*2/4;
        System.out.println(renderables);
        element.init(Minecraft.getInstance().screen.width,Minecraft.getInstance().screen.height);

    }

    public void dataChannelSocketClicked(DataChannelSocket socket){
        if(lastClickedSocket == null){
            lastClickedSocket = socket;
            creatingDataChannel = true;
            currentDataChannel = new DataChannelRenderable();
            currentDataChannel.element1 = lastClickedSocket;
            return;
        }
        //add it to existing if one is input and the other output
        if(!socketsOfSameType(lastClickedSocket,socket)) {
            currentDataChannel.element2 = socket;
            existingDataChannels.add(currentDataChannel);
        };

        currentDataChannel = null;
        creatingDataChannel = false;


    }

    @Override
    public boolean click(double mouseX, double mouseY) {
        boolean lastSocketClickStatus = creatingDataChannel;
        setHeldArray(null);
        for(ContainerRenderable renderable : renderables){
            if(!(renderable instanceof Clickable) && !(renderable instanceof Hoverable)) continue;

            if(((Hoverable) renderable).isMouseOver(mouseX,mouseY)){
                return ((Clickable) renderable).click(mouseX,mouseY);
            }

        }
        //creating data channel and after click it has not changed so cancel
        if(creatingDataChannel == lastSocketClickStatus && creatingDataChannel){
            creatingDataChannel = false;
            currentDataChannel = null;
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX>this.x && mouseX < this.x+rawWidth*getTotalScaling()/scalar && mouseY > this.y && mouseY < this.y+rawHeight*getTotalScaling()/scalar;
    }

    public void setHeldArray(ArrayElement arrayNodeElement){
        heldArrayElement = arrayNodeElement;
    }

    public void renderableClicked(ContainerRenderable clicked){

    }


}
