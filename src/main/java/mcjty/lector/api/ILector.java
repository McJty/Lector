package mcjty.lector.api;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Global API for Lector
 * Get a reference to an implementation of this interface by calling:
 *         FMLInterModComms.sendFunctionMessage("lector", "getApi", "<whatever>.YourClass$GetApi");
 */
public interface ILector {

    /**
     * Open a manual in a gui (as opposed to using it in a bookstand). Use this
     * function when the player is holding an IBook item in his/her hand.
     * You would typicall call this method from within your book item onItemRightClick
     * at the client side.
     */
    void openManual(EntityPlayer player);
}
