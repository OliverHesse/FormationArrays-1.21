package net.Lucent.ArrayFormations.datamap.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FormationCoreStats(int maxBlueprintSlots, String maxQi){
    public static final Codec<FormationCoreStats> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.INT.fieldOf("maxBlueprintSlots").forGetter(FormationCoreStats::maxBlueprintSlots),
                            Codec.STRING.fieldOf("maxQi").forGetter(FormationCoreStats::maxQi)
                    ).apply(instance,FormationCoreStats::new));
}
