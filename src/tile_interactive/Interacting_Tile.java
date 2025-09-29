package tile_interactive;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class Interacting_Tile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public Interacting_Tile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public void update() {
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public Interacting_Tile getDestroyedForm() {
        return null;
    }

    public void playSe() {}

    public Color getParticleColor() {
        return null;
    }

    public int getPaticleSize() {
        return 0;
    }

    public int getParticleSpeed() {
        return 0;
    }

    public int getParticleMaxLife() {
        return 0;
    }
}
