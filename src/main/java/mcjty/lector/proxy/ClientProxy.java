package mcjty.lector.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import mcjty.lector.Lector;
import mcjty.lector.font.FontLoader;
import mcjty.lector.font.TrueTypeFont;
import mcjty.lib.tools.MinecraftTools;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.concurrent.Callable;

public class ClientProxy extends CommonProxy {

    public static TrueTypeFont font;
    public static TrueTypeFont font_bold;
    public static TrueTypeFont font_italic;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        font = FontLoader.createFont(new ResourceLocation(Lector.MODID, "fonts/ubuntu.ttf"), 64, false,
                new char[] { '\u2022', '\u2014' });
        font_bold = FontLoader.createFont(new ResourceLocation(Lector.MODID, "fonts/ubuntu_bold.ttf"), 64, false,
                new char[] { '\u2022', '\u2014' });
        font_italic = FontLoader.createFont(new ResourceLocation(Lector.MODID, "fonts/ubuntu_italic.ttf"), 64, false,
                new char[] { '\u2022', '\u2014' });
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    @Override
    public World getClientWorld() {
        return MinecraftTools.getWorld(Minecraft.getMinecraft());
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return MinecraftTools.getPlayer(Minecraft.getMinecraft());
    }

    @Override
    public <V> ListenableFuture<V> addScheduledTaskClient(Callable<V> callableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(callableToSchedule);
    }

    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }
}
