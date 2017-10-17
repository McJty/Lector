package mcjty.lector.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import mcjty.lector.Lector;
import mcjty.lector.config.ConfigSetup;
import mcjty.lector.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;
import java.util.concurrent.Callable;

public abstract class CommonProxy {

    public static File modConfigDir;
    private Configuration mainConfig;

    public void preInit(FMLPreInitializationEvent e) {
        ConfigSetup.preInit(e);
        ModItems.init();
    }

    private void readMainConfig() {
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Lector.instance, new GuiProxy());
        ModItems.initCrafting();
    }

    public void postInit(FMLPostInitializationEvent e) {
        ConfigSetup.postInit();
    }

    public World getClientWorld() {
        throw new IllegalStateException("This should only be called from client side");
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }

    public <V> ListenableFuture<V> addScheduledTaskClient(Callable<V> callableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }

}
