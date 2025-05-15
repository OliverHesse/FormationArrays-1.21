package net.Lucent.ArrayFormations.arrayExecutors;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.joml.Vector2d;
import oshi.util.tuples.Pair;

import java.util.List;

public abstract class AbstractPortalExecutor extends AbstractArrayExecutor {
    protected AbstractPortalExecutor(List<Pair<TagKey<Block>, Vector2d>> formationFlags,String qi_drain,String qi_gain) {
        super(formationFlags,qi_drain,qi_gain);
    }
}
