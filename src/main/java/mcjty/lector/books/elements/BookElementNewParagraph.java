package mcjty.lector.books.elements;

import mcjty.lector.books.renderers.RenderElement;
import mcjty.lector.books.renderers.RenderElementNone;
import mcjty.lector.proxy.ClientProxy;

public class BookElementNewParagraph implements BookElement {

    @Override
    public int getWidth(int curw) {
        return WIDTH_NEWPARAGRAPH;
    }

    @Override
    public int getHeight() {
        return (int) ClientProxy.font.getHeight();
    }

    @Override
    public RenderElement createRenderElement(int x, int y, int w, int h) {
        return new RenderElementNone();
    }
}
