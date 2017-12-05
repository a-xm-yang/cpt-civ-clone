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
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class GameLoop extends JPanel {

    GameMap map;
    ArrayList<Player> playerList;

    JFrame frame;
    Scanner scan;
    int choice;

    public GameLoop() {

        scan = new Scanner(System.in);
        map = new GameMap(MapSize.MEDIUM, 1);
        Unit.referenceMap(map);
        City.referenceMap(map);

        playerList = new ArrayList<Player>();
        playerList.add(new Player("Stalin"));
        playerList.get(0).addUnit(new SettlerUnit(playerList.get(0), new Point(30, 65)));
        playerList.get(0).addUnit(new WarriorUnit(playerList.get(0), new Point(30, 64)));

        initializeGraphics();
        repaint();
        
        choice = 0;
        loop();

    }

    public void loop() {

        for (Player player : playerList) {

            System.out.println("Player " + player.getName() + " starting turn!");
            player.startTurn();

            //choosing actions
            System.out.println("What do you want to do");
            
            System.out.print("\n1: Move Units\n2: b\n3: d\n4: t\n---");
            
            choice = scan.nextInt();
            scan.nextLine();
            
            if (choice == 1) {
                moveUnit(player);
            } else if (choice == 2) {
              
            } else if (choice == 3) {
            
            } else if (choice == 4) {
                
            } else {
                System.out.println("Invalid Choice!");
            }

        }

        loop();
    }
    
    public void moveUnit (Player p){
        
        //create a temporary list of all the units that can move still
        ArrayList<Unit> list = new ArrayList<Unit>();
        for (Unit u: p.getUnitList()){
            if (u.canMove()){
                list.add(u);
            }
        }
        
        if (list.isEmpty()){
            System.out.println("You have no movable unit at this time!");
            System.out.println("Ending turn");
            return;
        }
        
        System.out.println("Please select a unit from the following list: ");
        
        for (int i = 0; i < list.size(); i++){
            System.out.println(i+") "+ list.get(i).getClass().getSimpleName());
        }
        
        
        
        
        
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

                if (i % 2 == 0) {
                    g.fillPolygon(new int[]{k * 12 + 5 + k, k * 12 + 12 + k, k * 12 + 12 + k, k * 12 + 5 + k, k * 12 + k, k * 12 + k}, new int[]{i * 12 - i, i * 12 + 3 - i, i * 12 + 9 - i, i * 12 + 12 - i, i * 12 + 9 - i, i * 12 + 3 - i}, 6);
                } else {
                    g.fillPolygon(new int[]{k * 12 + 5 + 5 + k + 2, k * 12 + 12 + 5 + k + 2, k * 12 + 12 + 5 + k + 2, k * 12 + 5 + 5 + k + 2, k * 12 + 5 + k + 2, k * 12 + 5 + k + 2}, new int[]{i * 12 - i, i * 12 + 3 - i, i * 12 + 9 - i, i * 12 + 12 - i, i * 12 + 9 - i, i * 12 + 3 - i}, 6);
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
