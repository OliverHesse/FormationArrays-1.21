package net.Lucent.ArrayFormations.item.custom;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.minecraft.world.item.Item;

public class ArrayBlueprint extends Item {


    public AbstractArrayExecutor executor;


    public ArrayBlueprint(Properties properties, AbstractArrayExecutor executor) {
        this(properties);

        this.executor = executor;
    }


    public ArrayBlueprint(Properties properties) {
        super(properties);
    }
}
