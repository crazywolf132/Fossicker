package fdoom.screen;

import fdoom.core.Game;
import fdoom.core.Renderer;
import fdoom.core.io.InputHandler;
import fdoom.gfx.Color;
import fdoom.gfx.Screen;

public class SplashMenu extends Display {

    protected InputHandler input;
    private int rdm;
    private int tickc;

    public SplashMenu() {
        this.tickc = 0;
    }

    @Override
    public void init(Display parent) {
        super.init(null); // Cant have a parent for a splash screen
    }

    @Override
    public void tick(InputHandler input) {
        this.input = input;
        ++this.tickc;
        if (this.tickc >= 200) {
            Game.setMenu(new TitleDisplay());
        }
    }

    @Override
    public void render(final Screen screen) {
        int h = 5;
        int w = 46;
        ++this.rdm;
        screen.clear(0);
        for (int y = 3; y < h; ++y) {
            for (int x = 17; x < w; ++x) {
                final int titleColor = Color.get(this.rdm + x * 8, this.rdm + x * 8, this.rdm + x * 8, this.rdm + x * 8);
                screen.render(x * 4, y * 8, 352, titleColor, 0);
            }
        }
        h = Renderer.HEIGHT;
        w = Renderer.WIDTH;
        ++this.rdm;
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                final int titleColor = Color.get(0, 0, this.rdm + x * 5 + y * 2, 551);
                screen.render(x * 8, y * 8, 355, titleColor, 0);
            }
        }
    }




}
