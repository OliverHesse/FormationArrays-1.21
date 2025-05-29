package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers;

import net.Lucent.ArrayFormations.gui.guiLibrary.general.ImageGuiContainer;
import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.elements.ArrayElement;
import net.minecraft.resources.ResourceLocation;

//holds the info of the last selected item. be it
public class ArrayInfoGUIContainer extends ImageGuiContainer {
    public ArrayElement currentlyOpenedArrayElement;

    public ArrayInfoGUIContainer(ResourceLocation backgroundTexture, int rawWidth, int rawHeight, int x, int y,double defaultScaling, RootGUIContainer parent) {
        super(backgroundTexture, rawWidth, rawHeight, x, y,defaultScaling,parent);
    }
}
