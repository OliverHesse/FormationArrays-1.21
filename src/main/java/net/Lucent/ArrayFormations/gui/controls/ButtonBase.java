package net.Lucent.ArrayFormations.gui.controls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.core.Position;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public abstract class ButtonBase implements Renderable, GuiEventListener, NarratableEntry {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Font font;

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }
    public int width(){
        return width;
    }
    public int height(){
        return height;
    }

    public void x(int x){
        this.x = x;
    }
    public void y(int y){
        this.y = y;
    }
    public void setPos(Position pos){
        this.x = (int) pos.x();
        this.y = (int) pos.y();
    }

    protected boolean isHovered = false;
    protected boolean isFocused = false;


    protected IntConsumer onClick;

    protected ButtonBase(int x, int y, int width, int height,IntConsumer onClick){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        font = Minecraft.getInstance().font;
        this.onClick = onClick;

    }



    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        isHovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        renderBg(guiGraphics, mouseX, mouseY);

    }

    @Override
    public void setFocused(boolean focused) {
        isFocused = focused;

    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    protected abstract void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY);

    protected abstract void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }


}
