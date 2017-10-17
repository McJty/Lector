package mcjty.lector;

import mcjty.lector.apiimpl.LectorApi;
import mcjty.lector.items.ModItems;
import mcjty.lector.proxy.CommonProxy;
import mcjty.lib.compat.CompatCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Lector.MODID, name = "Lector",
        dependencies =
                        "required-after:compatlayer@[" + Lector.COMPATLAYER_VER + ",);" +
                        "after:Forge@[" + Lector.MIN_FORGE10_VER + ",);" +
                        "after:forge@[" + Lector.MIN_FORGE11_VER + ",)",
        version = Lector.VERSION,
        acceptedMinecraftVersions = "[1.10,1.12)")
public class Lector {
    public static final String MODID = "lector";
    public static final String VERSION = "0.0.1";
    public static final String MIN_FORGE10_VER = "12.18.3.2488";
    public static final String MIN_FORGE11_VER = "13.20.1.2454";
    public static final String COMPATLAYER_VER = "0.2.9";

    @SidedProxy(clientSide = "mcjty.lector.proxy.ClientProxy", serverSide = "mcjty.lector.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static Lector instance;

    public static Logger logger;
    public static CreativeTabs creativeTab;

    public static LectorApi api = new LectorApi();

    /**
     * Run before anything else. Read your config, create blocks, items, etc, and
     * register them with the GameRegistry.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        creativeTab = new CompatCreativeTabs("lector") {
            @Override
            protected Item getItem() {
                return ModItems.manual;
            }
        };
        this.proxy.preInit(e);
    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        this.proxy.init(e);
    }

    /**
     * Handle interaction with other mods, complete your setup based on this.
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        this.proxy.postInit(e);
    }
}
