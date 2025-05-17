package net.Lucent.ArrayFormations.programmableArrays.sensoryNodes;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.AbstractDataChannel;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.GenericInputDataChannel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class EntityDetectionNode extends  AbstractSensoryNode{

    public double DEFAULT_RADIUS = 8; //can be modified using a constant input node
    public double detectionRadius = DEFAULT_RADIUS;

    public GenericInputDataChannel<Double> detectionRadiusChannel;


    public void getDetectionRadius( Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity){

        Optional<?> result = detectionRadiusChannel.getChannelData(level,slot,rotation,blockEntity);
        if(result.isEmpty() || !(result.get() instanceof Double)) detectionRadius = DEFAULT_RADIUS;
        else detectionRadius = ((Double) result.get());

    }

    @Override
    public void addNodeConnection(String connection, AbstractFormationNode node,AbstractFormationNode receiverNode, String nodeConnection) {
        switch (connection){
            case "detectionRadius":
                detectionRadiusChannel = new GenericInputDataChannel<>(node,receiverNode,nodeConnection);
                break;
        }
    }

    @Override
    public Optional<?> runConnection(String connection, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {
        return switch (connection){
            //requires radius to work so calls getDetectionRadius
            case "nearbyEntities":
                getDetectionRadius(level,slot,rotation,blockEntity);
                yield Optional.of(getNearbyEntities(level,blockEntity));

            default:
                System.out.println("No match");
                yield Optional.empty();

        };
    }

    @Override
    public void runWithChannel(AbstractDataChannel<?> channel, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {

    }

    @Override
    public List<String> getConnections() {
        return  List.of("detectionRadius");
    }

    @Override
    public AbstractDataChannel<?> getDataChannel(String connection) {
        return switch (connection){
            case "detectionRadius":
                yield detectionRadiusChannel;
            default:
                yield null;
        };
    }

    @Override
    public String getDisplayName() {
        return "Heavenly Detection Array";
    }

    @Override
    public String getNodeAsString() {
        return "EntityDetectionNode";
    }


    public List<Entity> getNearbyEntities(Level level, AbstractFormationCoreBlockEntity blockEntity){
        return level.getEntities((Entity) null,new AABB(blockEntity.getBlockPos()).inflate(detectionRadius), entity -> {

            if(!(entity instanceof LivingEntity)) return false;
            var distance = entity.position().distanceToSqr(blockEntity.getBlockPos().getCenter());
            return distance < Math.pow(detectionRadius, 2);

        });

    }
}
