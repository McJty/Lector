package mcjty.lector.books.elements;

import mcjty.lector.books.renderers.RenderElement;
import mcjty.lector.books.renderers.RenderElementRuler;
import mcjty.lector.proxy.ClientProxy;

public class BookElementRuler implements BookElement {

    @Override
    public int getWidth(int curw) {
        return WIDTH_FULLWIDTH;
    }

    @Override
    public int getHeight() {
        return (int) ClientProxy.font.getHeight()/2;
    }

    @Override
    public RenderElement createRenderElement(int x, int y, int w, int h) {
        return new RenderElementRuler(x, y);
    }
}
