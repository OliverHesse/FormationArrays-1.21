package net.Lucent.ArrayFormations.programmableArrays;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.programmableArrays.coreNodes.AbstractCoreFormationNode;
import net.minecraft.world.level.Level;

import java.util.List;

public class FormationNodeWrapper {
    public List<AbstractCoreFormationNode> rootCoreNodes;
    public FormationNodeWrapper(List<AbstractCoreFormationNode> nodes){
        this.rootCoreNodes = nodes;
    }


    public void run(Level level, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity){
        for(AbstractCoreFormationNode node:rootCoreNodes){
            node.run(level,slot,rotation,blockEntity);
        }
    }

}
