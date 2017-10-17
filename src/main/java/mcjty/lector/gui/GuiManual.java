package mcjty.lector.gui;

import mcjty.lector.Lector;
import mcjty.lector.api.IBook;
import mcjty.lector.books.*;
import mcjty.lector.sound.SoundTools;
import mcjty.lector.books.renderers.RenderElementText;
import mcjty.lector.proxy.ClientProxy;
import mcjty.lib.tools.ItemStackTools;
import mcjty.lib.tools.MinecraftTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiManual extends GuiScreen {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 220;
    private int guiLeft;
    private int guiTop;

    private ResourceLocation json;
    private List<BookPage> pages;
    private int pageNumber = 0;
    private String result = null;

    private static final ResourceLocation background = new ResourceLocation(Lector.MODID, "textures/gui/manual_paper.png");
    private static final ResourceLocation backgroundFront = new ResourceLocation(Lector.MODID, "textures/gui/manual_front.png");

    public GuiManual() {
    }

    @Override
    public void initGui() {
        super.initGui();

        guiLeft = (this.width - WIDTH) / 2;
        guiTop = (this.height - HEIGHT) / 2;

        EntityPlayerSP player = MinecraftTools.getPlayer(Minecraft.getMinecraft());
        ItemStack book = player.getHeldItemMainhand();
        if (ItemStackTools.isValid(book) && book.getItem() instanceof IBook) {
            json = ((IBook) book.getItem()).getJson();
            BookParser parser = new BookParser();
            pages = parser.parse(json, 768, 900);
            pageNumber = 0;
            result = null;
        } else {
            json = null;    // Shouldn't be possible
            pages = new ArrayList<>();
            pages.add(new BookPage());
            RenderSection section = new RenderSection("Error");
            TextElementFormat fmt = new TextElementFormat("red,bold");
            section.addElement(new RenderElementText("Error!", 10, 10, (int) ClientProxy.font.getWidth("Error!"), (int) ClientProxy.font.getHeight(), fmt));
            pages.get(0).addSection(section);
            pageNumber = 0;
            result = null;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private static int findPageForSection(List<BookPage> pages, String section) {
        for (int i = 0 ; i < pages.size() ; i++) {
            for (RenderSection s : pages.get(i).getSections()) {
                if (section.equals(s.getName())) {
                    return i;
                }
            }

        }
        return -1;
    }


    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        if (result != null) {
            if ("<".equals(result)) {
                pageDec();
            } else if (">".equals(result)) {
                pageInc();
            } else if ("^".equals(result)) {
                pageFront();
            } else {
                int number = findPageForSection(pages, result);
                if (number != -1 && number != pageNumber) {
                    pageNumber = number;
                    playPageTurn();
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_RIGHT) {
            pageInc();
        } else if (keyCode == Keyboard.KEY_LEFT || keyCode == Keyboard.KEY_BACK) {
            pageDec();
        } else if (keyCode == Keyboard.KEY_HOME) {
            pageFront();
        }
    }

    private void pageFront() {
        if (pageNumber != 0) {
            pageNumber = 0;
            playPageTurn();
        }
    }

    private void pageInc() {
        if (pageNumber < pages.size()-1) {
            pageNumber++;
            playPageTurn();
        }
    }

    private void pageDec() {
        if (pageNumber > 0) {
            pageNumber--;
            playPageTurn();
        }
    }

    private void playPageTurn() {
        EntityPlayerSP player = MinecraftTools.getPlayer(Minecraft.getMinecraft());
        SoundTools.playPageTurn(MinecraftTools.getWorld(Minecraft.getMinecraft()), player.getPosition());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (pageNumber == 0) {
            mc.getTextureManager().bindTexture(backgroundFront);
        } else {
            mc.getTextureManager().bindTexture(background);
        }
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);

        float ix = (float) (mouseX - guiLeft) / WIDTH;
        float iy = (float) (mouseY - guiTop) / HEIGHT;
        if (ix < .15f) {
            result = "<";
        } else if (ix > 1-.1f) {
            result = ">";
        } else if (iy < .15f) {
            result = "^";
        } else {
            result = null;
        }
        String rc = BookRenderHelper.renderPageForGUI(pages, pageNumber, 1.0f, ix, iy, guiLeft, guiTop);
        if (rc != null) {
            result = rc;
        }
    }
}
