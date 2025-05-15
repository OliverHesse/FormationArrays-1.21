package net.Lucent.ArrayFormations.datamap.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FormationCoreFuelMap(String qi) {
    public static final Codec<FormationCoreFuelMap> CODEC =
            RecordCodecBuilder.create(instance ->
               instance.group(
                       Codec.STRING.fieldOf("qi").forGetter(FormationCoreFuelMap::qi)
               ).apply(instance,FormationCoreFuelMap::new));
}
