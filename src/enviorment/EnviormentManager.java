package enviorment;

import Main.GamePanel;

import java.awt.*;

public class EnviormentManager {
    GamePanel gp;
    Lightning lightning;

    public EnviormentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        lightning = new Lightning(gp, 350);
    }

    public void draw(Graphics2D g2) {
        lightning.draw(g2);
    }
}
