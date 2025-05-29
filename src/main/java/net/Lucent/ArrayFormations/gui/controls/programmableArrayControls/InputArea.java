package net.Lucent.ArrayFormations.gui.controls.programmableArrayControls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class InputArea {
    public final EditBox editBox;
    private String unfocusedEmptyHint = null;


    public int x;
    public int y;
    public InputArea(int x, int y) {
        this.editBox = new EditBox(Minecraft.getInstance().font,x+7,y+5,50,13, Component.empty());
        this.x = x;
        this.y = y;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks){
        editBox.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);

        if (editBox.getValue().isEmpty() && unfocusedEmptyHint != null && !editBox.isFocused()) {
            int x = editBox.getX() + editBox.getWidth() / 2 + 2/* editBox.isBordered() ? editBox.getX() + 4 : editBox.getX()*/;
            int y = editBox.isBordered() ? editBox.getY() + (editBox.getHeight() - 8) / 2 : editBox.getY();
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, unfocusedEmptyHint, x, y,-16777216);
        }
    }


    public void setFocused(boolean focused) {
        if (editBox.isFocused() != focused) {
            editBox.setFocused(focused);
        }

    }
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!editBox.isFocused()) {
            return false;
        }
        editBox.keyPressed(keyCode, scanCode, modifiers);
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            onEnterPressed();
        }
        return keyCode != GLFW.GLFW_KEY_ESCAPE;
    }
    public String getValue() {
        return editBox.getValue();
    }
    protected void onEnterPressed() {
        //noop
    }
    public void setValue(String value) {
        editBox.setValue(value);
    }

    public void setBordered(boolean bordered) {
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
