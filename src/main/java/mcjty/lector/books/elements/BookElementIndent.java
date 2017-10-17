package mcjty.lector.books.elements;

import mcjty.lector.books.renderers.RenderElement;
import mcjty.lector.books.renderers.RenderElementIndent;
import mcjty.lector.proxy.ClientProxy;

public class BookElementIndent implements BookElement {

    public static final String INDENTSTRING = "     " + '\u2022';

    @Override
    public int getWidth(int curw) {
        return (int) ClientProxy.font.getWidth(INDENTSTRING);
    }

    @Override
    public int getHeight() {
        return (int) ClientProxy.font.getHeight();
    }

    @Override
    public RenderElement createRenderElement(int x, int y, int w, int h) {
        return new RenderElementIndent(x, y);
    }
}
