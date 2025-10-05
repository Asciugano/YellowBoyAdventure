package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionsStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        InputStream is = getClass().getResourceAsStream("/maps/tileData_V4.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            String line;
            while((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionsStatus.add(br.readLine());
            }
            br.close();
        } catch(IOException e) {
            System.err.println(e);
        }

        tile = new Tile[fileNames.size()];
        getTileImage();

        is = getClass().getResourceAsStream("/maps/worldV4.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line = br.readLine();
            String[] maxTile =  line.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;

            gp.worldWidth = gp.maxWorldCol * gp.tileSize;
            gp.worldHeight = gp.maxWorldRow * gp.tileSize;

            br.close();
        } catch(Exception e) {
            System.err.println(e);
        }

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        loadMap("/maps/worldV4.txt", 0);
        loadMap("/maps/interior01.txt", 1);
    }

    public void getTileImage(){
        for(int i = 0; i < fileNames.size(); i++){
            String fileName = fileNames.get(i);
            boolean collision = collisionsStatus.get(i).equals("true");

            setup(i, fileName, collision);
        }
    }

    public void setup(int index, String imagename, boolean collision){

        UtilityTool ut = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagename));
            tile[index].image = ut.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadMap(String filePat, int map) {
        try {

            InputStream is = getClass().getResourceAsStream(filePat);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(gp.player.screenX > gp.player.worldX){
                screenX = worldX;
            }
            if(gp.player.screenY > gp.player.worldY){
                screenY = worldY;
            }
            int rigthOffset = gp.screenWidth - gp.player.screenX;
            if(rigthOffset > gp.worldWidth - gp.player.worldX){
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if(bottomOffset > gp.worldHeight - gp.player.worldY){
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }
            else if(gp.player.screenX > gp.player.worldX ||
                    gp.player.screenY > gp.player.worldY ||
                    rigthOffset > gp.worldWidth - gp.player.screenX ||
                    bottomOffset > gp.worldHeight - gp.player.screenY){

                g2.drawImage(tile[tileNum].image,screenX,screenY,null);
            }

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image,screenX,screenY,null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }

        }
        if(drawPath) {
            g2.setColor(new Color(255, 0, 0, 70));

            for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX,screenY,gp.tileSize,gp.tileSize);
            }
        }

    }

}
