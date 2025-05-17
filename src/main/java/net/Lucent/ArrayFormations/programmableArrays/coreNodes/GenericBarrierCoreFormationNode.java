package net.Lucent.ArrayFormations.programmableArrays.coreNodes;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.AbstractDataChannel;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.GenericInputDataChannel;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class GenericBarrierCoreFormationNode extends AbstractCoreFormationNode{

    public AbstractDataChannel<?> radiusChannel;
    public AbstractDataChannel<?> canRunChannel;


    public final Double DEFAULT_BARRIER_RADIUS = 5.0;
    public Double barrierRadius = DEFAULT_BARRIER_RADIUS;

    public Boolean canRun = true;

    @Override
    public void run(Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {
        if(level.isClientSide()) return;
        setCanRun(level,slot,rotation,blockEntity);
        setRadius(level,slot,rotation,blockEntity);
        if(!canRun) return;

        System.out.println("Running barrier tick with barrierRadius of: "+ barrierRadius.toString());

    }

    @Override
    public void addNodeConnection(String connection, AbstractFormationNode node,AbstractFormationNode receiverNode, String nodeConnection) {
        switch (connection){
            case "canRun":
                canRunChannel = new GenericInputDataChannel<>(node,receiverNode,nodeConnection);
                break;
            case "barrierRadius":
                radiusChannel = new GenericInputDataChannel<>(node,receiverNode,nodeConnection);
                break;
            default:
                System.out.println("no connection "+ connection+ " exists fr Two Condition equality node");
        };
    }

    @Override
    public Optional<?> runConnection(String connection, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {
        return switch (connection){
            case "barrierHealthPercent":
                //TODO IMPLEMENT
                yield getBarrierHealthPercent(level,slot,rotation,blockEntity);
            default:
                System.out.println("No connection "+ connection+" found in barrier core node");
                yield Optional.empty();
        };

    }

    @Override
    public void runWithChannel(AbstractDataChannel<?> channel, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {

    }

    @Override
    public List<String> getConnections() {
        return List.of("canRun","barrierRadius");
    }

    @Override
    public AbstractDataChannel<?> getDataChannel(String connection) {
        return  switch (connection){
            case "canRun":
                yield canRunChannel;
            case "barrierRadius":
                yield radiusChannel;
            default:
                yield null;
        };
    }

    @Override
    public String getDisplayName() {
        return "Five Element Barrier Array";
    }

    @Override
    public String getNodeAsString() {
        return "GenericBarrierCoreFormationNode";
    }

    public void setCanRun( Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity){
        if(canRunChannel == null) {
            canRun = true;
            return;
        }
        Optional<?> result = canRunChannel.getChannelData(level,slot,rotation,blockEntity);
        if(result.isEmpty()) {
            canRun = false;
            return;
        }
        if(result.get() instanceof Boolean){
            canRun = (Boolean)  result.get();
        } else canRun = false;

    }
    public void setRadius( Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity){
        if(radiusChannel == null){
            barrierRadius = DEFAULT_BARRIER_RADIUS;
            return;
        }
        Optional<?> result = radiusChannel.getChannelData(level,slot,rotation,blockEntity);
        if(result.isEmpty()){
            barrierRadius = DEFAULT_BARRIER_RADIUS;
            return;
        }
        if(result.get() instanceof Double){
            barrierRadius = (Double) result.get();
        }else barrierRadius = DEFAULT_BARRIER_RADIUS;
    }

    public Optional<?> getBarrierHealthPercent( Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity){
        return Optional.empty();
    }
}
