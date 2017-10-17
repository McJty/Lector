package mcjty.lector.books.elements;

import mcjty.lector.books.TextElementFormat;
import mcjty.lector.books.renderers.RenderElement;
import mcjty.lector.books.renderers.RenderElementLink;
import net.minecraft.item.EnumDyeColor;

public class BookElementLink implements BookElement {

    private final String text;
    private final TextElementFormat fmt;

    public BookElementLink(String text, TextElementFormat fmt) {
        this.text = text;
        this.fmt = fmt;
    }

    @Override
    public RenderElement createRenderElement(int x, int y, int w, int h) {
        return new RenderElementLink(text, x, y, w, h, fmt, EnumDyeColor.CYAN);
    }

    @Override
    public int getWidth(int curw) {
        return (int) (fmt.getFont().getWidth(text) * fmt.getScale());
    }

    @Override
    public int getHeight() {
        return (int) (fmt.getFont().getHeight() * fmt.getScale());
    }
}
