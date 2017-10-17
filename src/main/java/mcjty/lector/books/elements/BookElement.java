package mcjty.lector.books.elements;

import mcjty.lector.books.renderers.RenderElement;

public interface BookElement {

    int WIDTH_NEWLINE = -1;
    int WIDTH_FULLWIDTH = -2;
    int WIDTH_NEWPARAGRAPH = -3;

    // Get the width of this element. Returns -1 for newline
    int getWidth(int curw);

    // Get the height of this element
    int getHeight();

    RenderElement createRenderElement(int x, int y, int w, int h);
}
