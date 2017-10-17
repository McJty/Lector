package mcjty.lector.items;


import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static LectorManual manual;

    public static void init() {
        manual = new LectorManual();
    }

    public static void initCrafting() {
        GameRegistry.addShapedRecipe(new ItemStack(manual), "ppp", "pbp", "ppp", 'b', Items.BOOK, 'p', Items.PAPER);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        manual.initModel();
    }
}
