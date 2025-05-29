package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers;

import com.google.gson.JsonObject;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;
import net.Lucent.ArrayFormations.gui.guiLibrary.general.ImageGuiContainer;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.ArrayDataReader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//used to hold the arrays that can be clicked to use
public class ArrayHolderGUIContainer extends ImageGuiContainer implements Clickable, Hoverable {




    public List<ArrayGUIContainer> containers = new ArrayList<>();

    public int offset = 0;

    public void ArrayContainerClicked(ArrayGUIContainer arrayGUIContainer){
        System.out.println("attempt to create array");
        ((RootGUIContainer) parent).addArrayElement(arrayGUIContainer.name);

    }


    @Override
    public void init(int screenWidth, int screenHeight) {
        super.init(screenWidth, screenHeight);
        System.out.println("x: " + this.x +" y: "+this.y);
        for(ArrayGUIContainer container:containers){
            container.init(screenWidth,screenHeight);
        }
    }

    //replace with the players unlocked arrays
    public List<String> availableArrays = List.of(
            "5 Element Barrier Array",
            "Heavenly Detection Array",
            "Heavens Constant",
            "Heavenly Scrying Formation",
            "Heavens Scales of Balance");


    @Override
    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick, boolean canInteract) {
        super.render(guiGraphics, mouseX, mouseY, partialTick, canInteract);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(40,55,0);
        for(int i = offset;i<Math.min(containers.size(),offset+3);i++){
            containers.get(i).x = 40;
            containers.get(i).y = (50 + i*(100+5));
            containers.get(i).render(guiGraphics,mouseX,mouseY,partialTick,canInteract);
            guiGraphics.pose().translate(0,(100+5),0);
        }
        guiGraphics.pose().popPose();
    }

    public ArrayGUIContainer createContainer(String name){

        try {
            JsonObject obj = ArrayDataReader.getArrayInfo(name);

            String flavour = obj.get("flavour").getAsString();
            String description = obj.get("description").getAsString();
            String texture = obj.get("texture").getAsString();
            int width = obj.get("width").getAsInt();
            int height = obj.get("height").getAsInt();

            return new ArrayGUIContainer(
                ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,texture),
                    240,
                    100,
                    width,
                    height,
                    name,
                    flavour,
                    description,
                    0,0,
                    this
            );
        } catch (IOException e) {
            return  null;
        }
    }


    public ArrayHolderGUIContainer( int rawWidth, int rawHeight, int x, int y,double defaultScale, RootGUIContainer parent) {
        super(ResourceLocation.fromNamespaceAndPath(
                ArrayFormationsMod.MOD_ID,
                "textures/gui/programmable_gui/menu_background/array_info_holder_unfurled.png"
        ), rawWidth, rawHeight, x, y,defaultScale,parent);
        for(String str : availableArrays) {
            containers.add(createContainer(str));
        }

    }

    @Override
    public boolean click(double mouseX, double mouseY) {
        for(ArrayGUIContainer container : containers.subList(offset,offset+3)){
            if(!container.isMouseOver(mouseX,mouseY)) continue;
            return container.click(mouseX,mouseY);
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX > this.x && mouseX < this.x + rawWidth*getTotalScaling()/scalar && mouseY > this.y && mouseY < this.y + rawHeight*scalar;
    }
}
