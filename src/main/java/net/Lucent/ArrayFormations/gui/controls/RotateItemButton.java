package net.Lucent.ArrayFormations.gui.controls;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.gui.util.TextureBlitData;
import net.Lucent.ArrayFormations.gui.util.TextureSubSection;
import net.Lucent.ArrayFormations.network.custom.serverbound.SyncFormationFlagRotationPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class RotateItemButton extends ImageButton{

    public final int slot;
    public final AbstractFormationCoreBlockEntity formationCore;
    public RotateItemButton(TextureBlitData textureBlitData, int x, int y, int width, int height, int slot, AbstractFormationCoreBlockEntity formationCore) {
        super(textureBlitData, x, y, width, height,button ->{});
        this.slot = slot;
        this.formationCore = formationCore;
    }

    public static RotateItemButton createRotateButton(ResourceLocation data, TextureSubSection unf, TextureSubSection hov, TextureSubSection f,
                                                      int x, int y, int tWidth, int tHeight, int width, int height,
                                                      int slot, AbstractFormationCoreBlockEntity formationCore){
        TextureBlitData textureData = new TextureBlitData(
                data,
                tWidth,
                tHeight,
                unf,
                hov,
                f

        );
        return new RotateItemButton(
                textureData,
                x,
                y,
                width,
                height,
                slot,
                formationCore
        );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (!isMouseOver(mouseX, mouseY)) {
            return false;
        }
        formationCore.blueprintSlotData.get(slot).rotation = (formationCore.blueprintSlotData.get(slot).rotation+1)%4;


        PacketDistributor.sendToServer(new SyncFormationFlagRotationPayload(slot,formationCore.blueprintSlotData.get(slot).rotation,formationCore.getBlockPos()));

        setFocused(!isFocused);
        return true;
    }
}
