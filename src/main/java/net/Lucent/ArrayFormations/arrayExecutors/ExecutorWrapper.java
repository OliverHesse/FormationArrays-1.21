package net.Lucent.ArrayFormations.arrayExecutors;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExecutorWrapper {


    public AbstractArrayExecutor EXECUTOR_TEMPLATE;

    public Map<AbstractFormationCoreBlockEntity,AbstractArrayExecutor> instanceMap =  new HashMap<>();

    public ExecutorWrapper(AbstractArrayExecutor baseExecutor){
        EXECUTOR_TEMPLATE = baseExecutor;
    }

    public AbstractArrayExecutor createFreshInstance(){
        return EXECUTOR_TEMPLATE.copy();
    }

    public AbstractArrayExecutor getExecutor(AbstractFormationCoreBlockEntity blockEntity){

        AbstractArrayExecutor instance = instanceMap.get(blockEntity);
        if(instance == null){

            instance = createFreshInstance();
            instanceMap.put(blockEntity,instance);
        }
        return instance;
    }

    public void removeExecutor(AbstractFormationCoreBlockEntity blockEntity){

        AbstractArrayExecutor instance = instanceMap.get(blockEntity);

        if(instance != null){
            instance.cancel();
            instanceMap.remove(blockEntity);
        }

    }

}
