package mcjty.lector.books.renderers;

import mcjty.lector.books.TextElementFormat;
import net.minecraft.item.EnumDyeColor;

public class RenderElementLink extends RenderElementText {

    private final float sr;
    private final float sg;
    private final float sb;

    public RenderElementLink(String text, int x, int y, int w, int h, TextElementFormat fmt, EnumDyeColor selected) {
        super(text, x, y, w, h, fmt);
        int value = selected.getMapColor().colorValue;
        sr = ((value >> 16) & 255) / 255.0f;
        sg = ((value >> 8) & 255) / 255.0f;
        sb = (value & 255) / 255.0f;
    }

    @Override
    public String render(int dy, float ix, float iy) {
        int w = (int) (fmt.getFont().getWidth(text) * fmt.getScale());
        int h = (int) (fmt.getFont().getHeight() * fmt.getScale());
        ix = (float) (ix * 1024 * 1.2 - 95);
        iy = (float) (iy * 1024 * 1.08 - 55);

        if (ix >= x && ix <= x+w && iy >= y && iy <= y+h) {
            renderText(dy, sr, sg, sb);
            return text;
        } else {
            super.render(dy, ix, iy);
            return null;
        }
    }
}
