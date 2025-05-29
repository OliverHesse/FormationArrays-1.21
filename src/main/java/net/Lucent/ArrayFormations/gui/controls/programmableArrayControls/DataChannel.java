package net.Lucent.ArrayFormations.gui.controls.programmableArrayControls;

import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorScreen;
import net.minecraft.client.gui.GuiGraphics;

public class DataChannel {

    public DataChannelSocket socket1;
    public DataChannelSocket socket2;

    public ProgrammableFormationEditorScreen screen;

    public boolean mouseOver = false;



    public DataChannel(DataChannelSocket socket1,DataChannelSocket socket2,ProgrammableFormationEditorScreen screen){
        this.socket1 = socket1;
        this.socket2 = socket2;
        this.screen = screen;

    }


    public boolean isDataChannelWithNodes(DataChannelSocket socket1,DataChannelSocket socket2){
        return  ((this.socket1 == socket1 || this.socket1 == socket2) && (this.socket2 == socket2 || this.socket2 == socket1));
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick){

        if(isMouseOver(mouseX,mouseY) && screen.menuMode.equals("normal") ){
            //render white lines on either side of it
            //TODO IMPLEMENT
        }

        guiGraphics.vLine(this.socket1.x+4,this.socket1.y+4,this.socket2.y+4, -14877488);
        guiGraphics.hLine(this.socket1.x+4,this.socket2.x+4,this.socket2.y+4,-14877488);
    }


    public void setMouseOver(){
        mouseOver = !mouseOver;
    }

    public boolean isMouseOver(double mouseX,double mouseY){

        return isMouseOverVertical(mouseX,mouseY) || isMouseOverHorizontal(mouseX,mouseY);

    }

    public boolean isMouseOverVertical(double mouseX,double mouseY){
        //give it a bit of leeway
        int minY = socket1.y;
        int maxY = socket2.y;
        if(socket2.y < socket1.y){
            minY = socket2.y;
            maxY = socket1.y;
        }

        return (mouseX < socket1.x +1 && mouseX > socket1.x -1) && (mouseY < maxY && mouseY > minY);

    }
    public boolean isMouseOverHorizontal(double mouseX,double mouseY){
        int minX = socket1.x;
        int maxX = socket2.x;
        if(socket2.x < socket1.x){
            minX = socket2.x;
            maxX = socket1.x;
        }
        return (mouseY < socket2.y +1 && mouseY > socket2.y -1) && (mouseX < maxX && mouseX > minX);

    }

}
