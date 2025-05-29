package net.Lucent.ArrayFormations.gui.guiLibrary.controls;

import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Clickable;
import net.Lucent.ArrayFormations.gui.guiLibrary.interfaces.Hoverable;

import java.util.ArrayList;
import java.util.List;

//does not control rendering
public class DropDown implements Clickable, Hoverable {

    public boolean selecting = false;

    public List<String> options = new ArrayList<>();

    public int x;
    public int y;

    public int width;

    public int height;

    public int activeIndex = 0;
    public DropDown(int x, int y, int width, int height){
        this(x,y,width,height,List.of());
    }

    public DropDown(int x, int y, int width, int height,List<String> options){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.options =options;

    }

    public boolean isSelecting(){ return selecting;}
    public void  setSelecting(boolean selecting){this.selecting = selecting;}


    public Integer mouseOverOption(double mouseX,double mouseY){
        if(options.size() <= 0) return null;
        if((mouseX > this.x && mouseX <this.x+width) && mouseY > this.y && mouseY < this.y+height) return activeIndex;
        if(!(mouseX > this.x && mouseX <this.x+width) && mouseY > this.y && mouseY < this.y+height*(1+options.size())) return null;
        return  (int) Math.floor((mouseY-this.y)/(height * (options.size())));

    }

    public void optionSelected(int index){}

    @Override
    public boolean click(double mouseX, double mouseY) {

        if(!isSelecting()){
            setSelecting(true);
        }else{
            setSelecting(false);
            optionSelected(mouseOverOption(mouseX,mouseY));
        }
        return true;
    }



    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseOverOption(mouseX, mouseY) != null;
    }


}
