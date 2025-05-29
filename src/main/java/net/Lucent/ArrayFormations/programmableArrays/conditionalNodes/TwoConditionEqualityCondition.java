package net.Lucent.ArrayFormations.programmableArrays.conditionalNodes;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.AbstractDataChannel;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.GenericInputDataChannel;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TwoConditionEqualityCondition extends AbstractConditionalNode{
    public GenericInputDataChannel<?> input1;
    public GenericInputDataChannel<?> input2;


    @Override
    public AbstractFormationNode copy() {
        return new TwoConditionEqualityCondition();
    }

    @Override
    public void addNodeConnection(String connection, AbstractFormationNode node,AbstractFormationNode receiverNode, String nodeConnection) {
        switch (connection){
            case "input1":
                input1 = new GenericInputDataChannel<>(node,receiverNode,nodeConnection);
                break;
            case "input2":
                input2 = new GenericInputDataChannel<>(node,receiverNode,nodeConnection);
                break;
            default:
                System.out.println("no connection "+ connection+ " exists fr Two Condition equality node");
        }
    }

    public boolean evaluateInputs(Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity){
        if(input1 == null || input2 == null) return false;
        Optional<?> condition1 = input1.getChannelData(level,slot,rotation,blockEntity);
        Optional<?> condition2 = input2.getChannelData(level,slot,rotation,blockEntity);
        if(condition1.isEmpty() || condition2.isEmpty()) return false;

        //TODO add more custom condition evaluations depending on combinations
        //TODO for example when comparing BigDecimal to a string convert string to BigDecimal
        return condition1.get().equals(condition2.get());

    }


    //only has 1 connection result
    @Override
    public Optional<?> runConnection(String connection, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {
        if(Objects.equals(connection, "evaluate")){
            return Optional.of(evaluateInputs(level,slot,rotation,blockEntity));
        }
        return Optional.empty();
    }

    @Override
    public void runWithChannel(AbstractDataChannel<?> channel, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {

    }

    @Override
    public List<String> getConnections() {
        return List.of("input1","input2");
    }

    @Override
    public AbstractDataChannel<?> getDataChannel(String connection) {
        return switch (connection){
            case "input1":
                yield input1;
            case "input2":
                yield input2;
            default:
                yield null;
        };
    }

    @Override
    public String getDisplayName() {
        return "Heavens Equality Scale";
    }

    @Override
    public String getNodeAsString() {
        return "TwoConditionEqualityCondition";
    }


}
