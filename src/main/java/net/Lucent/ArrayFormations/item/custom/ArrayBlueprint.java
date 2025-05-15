package net.Lucent.ArrayFormations.item.custom;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.Lucent.ArrayFormations.arrayExecutors.ExecutorWrapper;
import net.minecraft.world.item.Item;

public class ArrayBlueprint extends Item {


    public ExecutorWrapper executorWrapper;


    public ArrayBlueprint(Properties properties, AbstractArrayExecutor executor) {
        this(properties);

        this.executorWrapper = new ExecutorWrapper(executor);
    }


    public ArrayBlueprint(Properties properties) {
        super(properties);
    }
}
