package net.Lucent.ArrayFormations.programmableArrays;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.AbstractDataChannel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;


//while not explicitly hardcoded anywhere the only accepted types are
//Int,Decimal,BigDecimal,String,Boolean,Entity,List
//this makes things slightly easier when trying to compare since there is a limited selection


public abstract class AbstractFormationNode {


    public abstract AbstractFormationNode copy();
    //mainly used by the deserializer
    //i will most likely remove the generic type
    public abstract void addNodeConnection(String connection,AbstractFormationNode node,AbstractFormationNode receiverNode, String nodeConnection);


    public abstract Optional<?> runConnection(String connection, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity);

    public abstract void runWithChannel(AbstractDataChannel<?> channel, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity);

    public abstract List<String> getConnections();

    public abstract AbstractDataChannel<?> getDataChannel(String connection);
    public abstract String getDisplayName();
    public abstract String getNodeAsString();

}



