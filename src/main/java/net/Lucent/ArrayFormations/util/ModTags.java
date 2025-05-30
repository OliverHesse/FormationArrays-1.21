package net.Lucent.ArrayFormations.util;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import javax.swing.text.html.parser.Entity;

public class ModTags {

    public static class Blocks{
        public static final TagKey<Block> ARRAY_FLAG = createTag("array_flag");
        public static final TagKey<Block> WOOD_ARRAY_FLAG = createTag("wood_array_flag");
        public static final TagKey<Block> WATER_ARRAY_FLAG = createTag("water_array_flag");
        public static final TagKey<Block> FIRE_ARRAY_FLAG = createTag("fire_array_flag");
        public static final TagKey<Block> METAL_ARRAY_FLAG = createTag("metal_array_flag");
        public static final TagKey<Block> EARTH_ARRAY_FLAG = createTag("earth_array_flag");

        public static final TagKey<Block> FORMATION_CORE = createTag("formation_core");


        public static final TagKey<Block> PORTAL_RECEIVER = createTag("portal_receiver");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, name));
        }
    }


    public static class Items {
        public static final TagKey<Item> SPIRIT_STONE = createTag("spirit_stone");
        public static final TagKey<Item> FORMATION_ARRAY_FUEL = createTag("array_formation_fuel");

        public static final TagKey<Item> BARRIER_TOKEN = createTag("barrier_token");
        public static  final TagKey<Item> ARRAY_BLUEPRINT = createTag("array_blueprint");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, name));
        }
    }
}
