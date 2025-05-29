package net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.arrayNodes;

import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.DataArrayEnterButton;
import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.DataChannelSocket;
import net.Lucent.ArrayFormations.gui.util.ArrayClickResult;
import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DataArrayNode extends ArrayNode{


    public DataArrayEnterButton inputDataButton;


    public DataArrayNode(
            ResourceLocation texture, int x, int y, int width, int height, int textureWidth, int textureHeight,
             int containerX, int containerY, int containerWidth,
            int containerHeight, String name,ProgrammableFormationEditorScreen screen)
    {
        super(texture, x, y, width, height, textureWidth, textureHeight, List.of(
                new DataChannelSocket(12,28,false,"data")
        ), containerX, containerY, containerWidth, containerHeight, name,screen);
        inputDataButton = new DataArrayEnterButton(8,8,this,screen);
        inputDataButton.setPos(x,y);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        inputDataButton.setPos(x,y);
        inputDataButton.render(guiGraphics,mouseX,mouseY);
    }

    @Override
    public ArrayNode copy() {
        return new DataArrayNode(texture,x,y,width,height,textureWidth,textureHeight,containerX,containerY,containerWidth,containerHeight,name,screen);
    }

    @Override
    public ArrayClickResult mouseClicked(double mouseX, double mouseY) {
        System.out.println("is button pressed: " + inputDataButton.isPressed);
        System.out.println(inputDataButton.isMouseOver(mouseX,mouseY));
        if(inputDataButton.isMouseOver(mouseX,mouseY) && !isHeld){
            inputDataButton.isPressed = true;
            inputDataButton.mouseClicked();
            return new ArrayClickResult(ArrayClickResult.Result.OTHER,this,null);
        }

        return super.mouseClicked(mouseX, mouseY);
    }
}
