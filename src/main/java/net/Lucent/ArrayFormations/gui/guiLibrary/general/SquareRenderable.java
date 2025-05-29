package net.Lucent.ArrayFormations.gui.guiLibrary.general;

import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;

public abstract class SquareRenderable extends ContainerRenderable implements Hoverable {
    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX>getGlobalX() && mouseX < getGlobalX()+getScaledWidth() && mouseY>getGlobalY() && mouseY < getGlobalY()+getScaledHeight();
    }
}
