package net.Lucent.ArrayFormations.programmableArrays.dataChannels;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.minecraft.world.level.Level;

import java.util.Optional;


//the receiver node will always be the one using this
//so even though a formation core node holds its own data channel for outputs. it will pass that data channel to the receiver to use

public abstract class AbstractDataChannel<T>{
    public AbstractFormationNode inputNode;
    public AbstractFormationNode receiverNode;
    public String connection;

    public abstract Optional<?> getChannelData(Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity);
    public abstract AbstractFormationNode getInputFormationNode();
    public AbstractDataChannel(AbstractFormationNode node,AbstractFormationNode receiverNode ,String connection){
        this.inputNode = node;
        this.receiverNode = receiverNode;
        this.connection = connection;

    }
    public AbstractFormationNode getInputNode(){
        return inputNode;
    }
}

