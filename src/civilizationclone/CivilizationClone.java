package civilizationclone;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/*
Alexander Yang
Nick Seniow
Justin Tang
 */

public class CivilizationClone extends JPanel {

    static Map map = new Map();

    public static void main(String[] args) {

        //System.out.println("Hello world");
        map.generateMap();
        CivilizationClone test = new CivilizationClone();
        System.out.println("Hello world");

    }

    public CivilizationClone() {

        JFrame frame = new JFrame("TESTING");
        frame.add(this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(800, 800);
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
        for (int i = 0; i < map.simplexNoise.length; i++) {
            for (int k = 0; k < map.simplexNoise[i].length; k++) {
                if (map.simplexNoise[i][k] < 0) {
                    g.setColor(Color.BLUE);
                } else if (map.simplexNoise[i][k] < 0.5) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillRect(i * 5, k * 5, 5, 5);
            }
        }
    }

}
