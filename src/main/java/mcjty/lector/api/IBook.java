package mcjty.lector.api;

import net.minecraft.util.ResourceLocation;

/**
 * Implement this interface on a an item if this item represents a book
 */
public interface IBook {

    /**
     * The name/title of this book
     */
    String getTitle();

    /**
     * Get the json file for the book text
     */
    ResourceLocation getJson();
}
