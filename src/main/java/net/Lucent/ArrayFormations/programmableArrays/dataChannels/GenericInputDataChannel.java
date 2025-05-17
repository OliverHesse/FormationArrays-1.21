package net.Lucent.ArrayFormations.programmableArrays.dataChannels;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;


//used to pipe data from input type nodes
public class GenericInputDataChannel<T> extends AbstractDataChannel<T>{


    public GenericInputDataChannel(AbstractFormationNode node,AbstractFormationNode receiverNode, String connection) {
        super(node,receiverNode, connection);
    }

    @Override
    public Optional<?> getChannelData(Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {
        if(inputNode == null) return Optional.empty();
        Optional<?> initialData = inputNode.runConnection(connection,level,slot,rotation,blockEntity);
        return initialData;

    }

    @Override
    public AbstractFormationNode getInputFormationNode() {
        return null;
    }
}
