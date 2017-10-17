package mcjty.lector.books.renderers;

import mcjty.lector.proxy.ClientProxy;
import org.apache.commons.lang3.StringUtils;

public class RenderElementRuler implements RenderElement {

    private final int x;
    private final int y;

    public RenderElementRuler(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String render(int dy, float ix, float iy) {
        float charw = ClientProxy.font.getWidth("" + '\u2014');
        int repeat = (int) ((768 - x) / charw);
        String s = StringUtils.repeat('\u2014', repeat);
        ClientProxy.font.drawString(x, 512 - (y + dy) + 20, s, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f);
        return null;
    }
}
