package net.Lucent.ArrayFormations.util;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks{
        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SPIRIT_STONE = createTag("spirit_stone");
        public static final TagKey<Item> ARRAY_FORMATION_FUEL = createTag("array_formation_fuel");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, name));
        }
    }
}
