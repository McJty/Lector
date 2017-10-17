package mcjty.lector.config;

import mcjty.lector.varia.BookType;
import net.minecraft.init.Items;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.HashMap;
import java.util.Map;

public class GeneralConfiguration {
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_BOOKS = "books";

    public static float basePageTurnVolume = 1.0f;   // Use 0 to turn off

    public static Map<String,String> validBooks = new HashMap<>();


    public static void init(Configuration cfg) {
        setupBookConfig(cfg);

        basePageTurnVolume = (float) cfg.get(CATEGORY_GENERAL, "basePageTurnVolume", basePageTurnVolume,
                "The volume for the page turning sound (0.0 is off)").getDouble();
    }

    public static void setupBookConfig(Configuration cfg) {
        ConfigCategory category = cfg.getCategory(CATEGORY_BOOKS);
        if (category.isEmpty()) {
            // Initialize with defaults
            addBook(cfg, Items.BOOK.getRegistryName().toString(), "*");
            addBook(cfg, Items.ENCHANTED_BOOK.getRegistryName().toString(), "*");
            addBook(cfg, Items.WRITABLE_BOOK.getRegistryName().toString(), "*");
            addBook(cfg, Items.WRITTEN_BOOK.getRegistryName().toString(), "*");
            addBook(cfg, "rftools:rftools_manual", BookType.BOOK_BLUE.getModel());
            addBook(cfg, "rftoolscontrol:rftoolscontrol_manual", BookType.BOOK_GREEN.getModel());
            addBook(cfg, "rftoolsdim:rftoolsdim_manual", BookType.BOOK_GREEN.getModel());
            addBook(cfg, "deepresonance:dr_manual", BookType.BOOK_RED.getModel());
        } else {
            for (Map.Entry<String, Property> entry : category.entrySet()) {
                validBooks.put(entry.getKey(), entry.getValue().getString());
            }
        }
    }

    private static void addBook(Configuration cfg, String name, String type) {
        cfg.get(CATEGORY_BOOKS, name, type);
        validBooks.put(name, type);
    }

}
