package tile_interactive;

import Main.GamePanel;

public class IT_Trunk extends Interacting_Tile{

    GamePanel gp;

    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;

        down1 = setup("/tiles_interactive/trunk", gp.tileSize, gp.tileSize);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
