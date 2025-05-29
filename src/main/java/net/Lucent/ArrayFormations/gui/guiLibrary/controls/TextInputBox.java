package net.Lucent.ArrayFormations.gui.guiLibrary.controls;

import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public abstract class TextInputBox  implements Clickable, Hoverable {
    EditBox editBox;

    public int x;
    public int y;
    public int height;
    public int width;
    public TextInputBox(int x, int y, int width, int height) {
        this.editBox = new EditBox(Minecraft.getInstance().font,x,y,width,height, Component.empty());
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return (mouseX > this.x && mouseX < this.x+this.width) &&(mouseY > this.y && mouseY < this.y +this.height);
    }

    public void setFocused(boolean focused){
        if(editBox.isFocused() != focused) editBox.setFocused(focused);
    }

    public boolean keyPressed(int keyCode,int scanCode,int modifiers){
        if (!editBox.isFocused()) {
            return false;
        }
        editBox.keyPressed(keyCode, scanCode, modifiers);
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            onEnterPressed();
        }
        return keyCode != GLFW.GLFW_KEY_ESCAPE;
    }

    public boolean charTyped(char codePoint,int modifiers){
        return editBox.charTyped(codePoint,modifiers);
    }

    public String getValue(){
        return editBox.getValue();
    }
    public void onEnterPressed(){

    }
    public void setValue(String value){
        editBox.setValue(value);
    }

    public void setBordered(boolean bordered){
        editBox.setBordered(bordered);

        if (!bordered) {
            editBox.setX(editBox.getX() + 1);
            editBox.setY(editBox.getY() + 1);
            editBox.setWidth(editBox.getWidth() - 6);
        } else {
            editBox.setX(editBox.getX());
            editBox.setY(editBox.getY());
            editBox.setWidth(editBox.getWidth());
        }
    }
    public void setMaxLength(int maxLength) {
        editBox.setMaxLength(maxLength);
    }
    public void setEditable(boolean editable) {
        editBox.setEditable(editable);
    }

}
