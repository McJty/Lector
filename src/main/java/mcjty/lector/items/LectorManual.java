package mcjty.lector.items;

import mcjty.lector.Lector;
import mcjty.lector.api.IBook;
import mcjty.lib.compat.CompatItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LectorManual extends CompatItem implements IBook {

    public LectorManual() {
        setMaxStackSize(1);
        setRegistryName("manual");
        setUnlocalizedName(Lector.MODID + ".manual");
        setCreativeTab(Lector.creativeTab);
        GameRegistry.register(this);
    }

    @Override
    public String getTitle() {
        return "The Lector Manual";
    }

    @Override
    public ResourceLocation getJson() {
        return new ResourceLocation(Lector.MODID, "text/manual.json");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

//    @Override
//    protected EnumActionResult clOnItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        ChatTools.addChatMessage(player, new TextComponentString("Use this book on a book stand"));
//        return EnumActionResult.PASS;
//    }
//
    @Override
    protected ActionResult<ItemStack> clOnItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            Lector.api.openManual(player);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }


}
