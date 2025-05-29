package net.Lucent.ArrayFormations.gui.util;

import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.arrayNodes.ArrayNode;
import net.Lucent.ArrayFormations.gui.controls.programmableArrayControls.DataChannelSocket;

public class ArrayClickResult {
    public enum Result {
        ARRAY_CLICKED,
        CHANNEL_CLICKED,
        OTHER,
        NOTHING,

    }


    public ArrayNode node;
    public DataChannelSocket socket;
    public Result result;

    public ArrayClickResult(Result result, ArrayNode node, DataChannelSocket socket){
        this.node = node;
        this.socket = socket;
        this.result = result;
    }
}
