package net.Lucent.ArrayFormations.screen.custom.ArrayEditorMenu.guiElements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ArrayDataReader {

    public static final ResourceLocation dataLocation = ResourceLocation.fromNamespaceAndPath(
            ArrayFormationsMod.MOD_ID,
            "array_info/arrays/array_data.json"
    );

    public static JsonObject getArrayInfo(String name) throws IOException {
        return getJsonData().getAsJsonObject(name);
    }

    public static JsonObject getArrayConnection(String name,String connectionName) throws IOException {
        return getJsonData().getAsJsonObject(name).getAsJsonObject("connections").getAsJsonObject(connectionName);
    }

    public static JsonObject getJsonData() throws IOException {
        try (InputStream in = Minecraft.getInstance().getResourceManager().open(dataLocation)) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JsonElement je = JsonParser.parseReader(reader);
            return je.getAsJsonObject();
        }
    }


}
