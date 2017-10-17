package mcjty.lector.apiimpl;

import mcjty.lector.Lector;
import mcjty.lector.api.ILector;
import mcjty.lector.proxy.GuiProxy;
import net.minecraft.entity.player.EntityPlayer;

public class LectorApi implements ILector {

    @Override
    public void openManual(EntityPlayer player) {
        player.openGui(Lector.instance, GuiProxy.GUI_MANUAL, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
    }
}
