package net.Lucent.ArrayFormations.gui.guiLibrary.general;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;


//tries to scale to fit
public abstract class GUIContainer extends SquareRenderable {



    @Override
    public double getTotalScaling() {
        double scaling = 1.0;
        if(this.parent != null){
           scaling = this.parent.getTotalScaling();
        }

        if(!scaled) return scaling;

        return defaultScale*scaling;
    }




    //for nesting containers you might not want one or 2 to scale
    public boolean scaled = true;

    public double defaultScale = 1.0;


    public GUIContainer( int rawWidth, int rawHeight, int x, int y,double defaultScale,ContainerRenderable parent){
        this.defaultScale = defaultScale;
        this.rawHeight = rawHeight;
        this.rawWidth = rawWidth;
        this.parent = parent;
        this.rawX = x;
        this.rawY = y;
        this.x = x;
        this.y = y;
    }
;
    public GUIContainer( int rawWidth, int rawHeight, int x, int y){
        this(rawWidth,rawHeight,x,y,1,null);

    }


    //containers use this
    public void render(GuiGraphics guiGraphics, double mouseX,double mouseY,float partialTick,boolean canInteract){
        for(ContainerRenderable renderable : renderables){
            renderable.render(guiGraphics,mouseX,mouseY,partialTick,canInteract);
        }
    };

    //this is called by the screen
    public void renderScaled(GuiGraphics guiGraphics, double mouseX,double mouseY,float partialTick,boolean canInteract){


        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x,y,0);
        if(scaled) {
            guiGraphics.pose().scale((float) getTotalScaling() / scalar, (float) getTotalScaling() / scalar, 1f);
        }
        //guiGraphics.pose().translate(x,y,0);
        render(guiGraphics,mouseX,mouseY,partialTick,canInteract);
        guiGraphics.pose().popPose();
    }


}
