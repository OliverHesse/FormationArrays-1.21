package net.Lucent.ArrayFormations.gui.guiLibrary.general;

import net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements.containers.EditorGuiContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerRenderable {


    public int scalar;

    //assumes 1920x1080
    public int rawX;
    public int rawY;

    public int rawWidth;
    public int rawHeight;

    //will assume this is unscaled if otherwise programs can override
    public int x;
    public int y;


    public ContainerRenderable parent;

    public List<ContainerRenderable> renderables = new ArrayList<>();

    private void adjustScale(int screenWidth,int screenHeight){
        System.out.println("dimension " + screenWidth + " " + screenHeight);
        System.out.println("window dimension " + Minecraft.getInstance().getWindow().getWidth() + " " + Minecraft.getInstance().getWindow().getHeight());
        double scaleX = (double) Minecraft.getInstance().getWindow().getWidth() / screenWidth;
        double scaleY = (double) Minecraft.getInstance().getWindow().getHeight() / screenHeight;

        scalar = (int) Math.max(1, Math.round(Math.min(scaleX, scaleY)));
        System.out.println(scalar);
        if(this instanceof EditorGuiContainer){
            System.out.println("init for renderables");
            System.out.println(renderables);
        }
        for (ContainerRenderable renderable : renderables){
            renderable.adjustScale(screenWidth,screenHeight);

        }
    }


    //used for repositioning and scaling
    public void init(int screenWidth,int screenHeight) {
        adjustScale(screenWidth,screenHeight);
   }
    //not adjusted to 1920x1080 but just the global screen cords of the element
    //since its normal cords are relative to its parent
    public double getGlobalX(){
        double shiftedX = 0.0;
        if(parent != null){
            shiftedX = parent.getGlobalX();
        }
        return shiftedX+getScaledX();
    }

    public double getGlobalY(){
        double shiftedY = 0.0;
        if(parent != null){
            shiftedY = parent.getGlobalY();
        }
        return shiftedY+getScaledY();
    }


    public double getScaledX(){
        return x * getTotalScaling()/scalar;
    }


    public double getScaledY(){
        return y * getTotalScaling()/scalar;
    }

    public double getScaledHeight(){
        return rawHeight*getTotalScaling()/scalar;
    }
    public double getScaledWidth(){
        return rawWidth*getTotalScaling()/scalar;
    }



    public  double getTotalScaling(){
        if(parent != null) return parent.getTotalScaling();
        return 1.0;
    }

    //TODO fix has a weird effect where further away from center more it is offset

    public int getRelativeMouseX(double mouseX){
        return (int) ( mouseX - getGlobalX() );
    }
    public int getRelativeMouseY(double mouseY){
        return (int) ( mouseY - getGlobalY());
    }

    public abstract void render(GuiGraphics guiGraphics,double mouseX,double mouseY, float partialTick,boolean canInteract);
}
