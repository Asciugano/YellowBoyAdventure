package Main;

import java.awt.image.BufferedImage;
import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // FullScreen
            if(gp.fullScreenOn)
                bw.write("On");
            else
                bw.write("Off");
            bw.newLine();

            // Music
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SE
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String line = br.readLine();
            if(line.equals("On"))
                gp.fullScreenOn = true;
            else if(line.equals("Off"))
                gp.fullScreenOn = false;

            line = br.readLine();
            gp.music.volumeScale = Integer.parseInt(line);

            line = br.readLine();
            gp.se.volumeScale = Integer.parseInt(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
