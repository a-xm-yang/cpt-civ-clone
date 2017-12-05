package civilizationclone;

import civilizationclone.GameMap.MapSize;
import civilizationclone.Tile.Desert;
import civilizationclone.Tile.Hills;
import civilizationclone.Tile.Ocean;
import civilizationclone.Tile.Plains;
import civilizationclone.Tile.Tile;
import civilizationclone.Unit.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class GameLoop extends JPanel {

    GameMap map;
    ArrayList<Player> playerList;

    JFrame frame;
    Scanner scan;

    public GameLoop() {

        scan = new Scanner(System.in);
        map = new GameMap(MapSize.MEDIUM, 500);
        Unit.referenceMap(map);
        City.referenceMap(map);

        playerList = new ArrayList<Player>();
        playerList.add(new Player("Stalin"));
        playerList.get(0).addUnit(new SettlerUnit(playerList.get(0), new Point(35, 60)));

        initializeGraphics();
        repaint();
        loop();

    }

    public void loop() {

        System.out.println("Input anything to settle");
        scan.next();

        Unit u = playerList.get(0).getUnitList().get(0);

        if (u instanceof SettlerUnit) {
            ((SettlerUnit) u).settle("ss");
        }

        repaint();
    }

    //TESTING GRAPHICS
    //<editor-fold>
    @Override
    public void paintComponent(Graphics g) {

        //Testing map printing
        super.paintComponent(g);

        setBackground(Color.BLACK);
        for (int i = 0; i < map.getMap().length; i++) {
            for (int k = 0; k < map.getMap()[0].length; k++) {

                Tile t = map.getTile(i, k);

                if (t instanceof Ocean) {
                    g.setColor(Color.BLUE);
                } else if (t instanceof Plains) {
                    g.setColor(Color.GREEN);
                } else if (t instanceof Hills) {
                    g.setColor(Color.GRAY);
                } else if (t instanceof Desert) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLACK);
                }

                if (t.hasUnit()) {
                    g.setColor(Color.RED);
                } else if (t.hasCity()) {
                    g.setColor(Color.WHITE);
                }

                if (k % 2 == 0) {
                    g.fillPolygon(new int[]{i * 12 + 5 + i, i * 12 + 12 + i, i * 12 + 12 + i, i * 12 + 5 + i, i * 12 + i, i * 12 + i}, new int[]{k * 12 - k, k * 12 + 3 - k, k * 12 + 9 - k, k * 12 + 12 - k, k * 12 + 9 - k, k * 12 + 3 - k}, 6);
                } else {
                    g.fillPolygon(new int[]{i * 12 + 5 + 5 + i + 2, i * 12 + 12 + 5 + i + 2, i * 12 + 12 + 5 + i + 2, i * 12 + 5 + 5 + i + 2, i * 12 + 5 + i + 2, i * 12 + 5 + i + 2}, new int[]{k * 12 - k, k * 12 + 3 - k, k * 12 + 9 - k, k * 12 + 12 - k, k * 12 + 9 - k, k * 12 + 3 - k}, 6);
                }
            }
        }
    }

    public void initializeGraphics() {
        frame = new JFrame("TESTING");
        frame.add(this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(1060, 960);
    }
    //</editor-fold>
}
