package civilizationclone;

import civilizationclone.GameMap.MapSize;
import civilizationclone.Tile.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/*
Alexander Yang
Nick Seniow
Justin Tang
 */
public class CivilizationClone extends JPanel {

    static GameMap map = new GameMap(MapSize.MEDIUM, 5515);

    public static void main(String[] args) {

       CivilizationClone test = new CivilizationClone();
    }

    public CivilizationClone() {

        JFrame frame = new JFrame("TESTING");
        frame.add(this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(1060, 960);
        Graphics g;
        g = getGraphics();
        paintComponent(g);
        while (true) {
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
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

                //g.fillRect(i * 5, k * 5, 5, 5);
                if (k % 2 == 0) {
                    g.fillPolygon(new int[]{i * 12 + 5 + i, i * 12 + 12 + i, i * 12 + 12 + i, i * 12 + 5 + i, i * 12 + i, i * 12 + i}, new int[]{k * 12 - k, k * 12 + 3 - k, k * 12 + 9 - k, k * 12 + 12 - k, k * 12 + 9 - k, k * 12 + 3 - k}, 6);
                } else {
                    g.fillPolygon(new int[]{i * 12 + 5 + 5 + i + 2, i * 12 + 12 + 5 + i + 2, i * 12 + 12 + 5 + i + 2, i * 12 + 5 + 5 + i + 2, i * 12 + 5 + i + 2, i * 12 + 5 + i + 2}, new int[]{k * 12 - k, k * 12 + 3 - k, k * 12 + 9 - k, k * 12 + 12 - k, k * 12 + 9 - k, k * 12 + 3 - k}, 6);
                }
            }
        }
    }

}
