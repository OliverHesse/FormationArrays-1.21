package net.Lucent.ArrayFormations.programmableArrays.constantNodes;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.AbstractDataChannel;
import net.minecraft.world.level.Level;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//holds a constant value like int,string,bool etc
public class ConstantInputNode<T> extends AbstractFormationNode {

    public T data = null;

    public ConstantInputNode(T data){
        this.data = data;
    }
    public ConstantInputNode(){
        this(null);
    }


    public void setData(T data) {
        this.data = data;
    }

    @Override
    public AbstractFormationNode copy() {
        return new ConstantInputNode<T>();
    }

    @Override
    public void addNodeConnection(String connection, AbstractFormationNode node, AbstractFormationNode receiverNode, String nodeConnection) {

    }

    @Override
    public Optional<?> runConnection(String connection, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {
        if(connection.equals("data")){
            return Optional.of(data);
        }
        return Optional.empty();
    }

    @Override
    public void runWithChannel(AbstractDataChannel<?> channel, Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {

    }

    @Override
    public List<String> getConnections() {
        return List.of();
    }

    @Override
    public AbstractDataChannel<?> getDataChannel(String connection) {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Heavens Constant";
    }

    @Override
    public String getNodeAsString() {
        return "ConstantInputNode";
    }

    public Optional<?> getData(){
        return Optional.of(data);
    }
}
