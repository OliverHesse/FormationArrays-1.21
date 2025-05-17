package net.Lucent.ArrayFormations.programmableArrays.conditionalNodes;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.AbstractDataChannel;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.GenericInputDataChannel;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class ListInputContainsCondition extends AbstractConditionalNode{

    public GenericInputDataChannel<?> listInputChannel;
    public GenericInputDataChannel<?> containsInputChannel;


    //do some stuff if list items are entities
    public boolean doesListContain(Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntit){
        if(listInputChannel == null || containsInputChannel == null) return false;
        Optional<?> listResult = listInputChannel.getChannelData(level,slot,rotation,blockEntit);
        Optional<?> containsResult = containsInputChannel.getChannelData(level,slot,rotation,blockEntit);
        if(listResult.isEmpty()) return false;
        if(containsResult.isEmpty()) return false; //since looping over will just return true no matter what
        if(!(listResult.get() instanceof List<?> resultList)) return false;

        for (Object o : resultList) {
            if (o.equals(containsResult.get())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNodeConnection(String connection, AbstractFormationNode node,AbstractFormationNode receiverNode, String nodeConnection) {
        switch (connection){
            case "listInput":
                listInputChannel = new GenericInputDataChannel<>(node,receiverNode,nodeConnection);
                break;
            case "containsInput":
                containsInputChannel = new GenericInputDataChannel<>(node,receiverNode,nodeConnection);
                break;
            default:
                System.out.println("no connection "+ connection+ " exists for List contains node");
        }
    }

    //only contains one connection
    @Override
    public Optional<?> runConnection(String connection, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {
        if(connection.equals("evaluate")){
            return Optional.of(doesListContain(level,slot,rotation,blockEntity));
        }
        return Optional.empty();
    }

    @Override
    public void runWithChannel(AbstractDataChannel<?> channel, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {

    }

    @Override
    public List<String> getConnections() {
        return List.of("listInput","containsInput");
    }

    @Override
    public AbstractDataChannel<?> getDataChannel(String connection) {
        return switch (connection){
            case "listInput":
                yield listInputChannel;
            case "containsInput":
                yield containsInputChannel;
            default:
               yield null;
        };
    }

    @Override
    public String getDisplayName() {
        return "Heavens plucking touch";
    }

    @Override
    public String getNodeAsString() {
        return "ListInputContainsCondition";
    }

}
