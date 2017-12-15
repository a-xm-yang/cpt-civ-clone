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
import java.util.Set;
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
        map = new GameMap(MapSize.MEDIUM, 268);
        Unit.referenceMap(map);
        City.referenceMap(map);
 
        playerList = new ArrayList<Player>();
        playerList.add(new Player("Joseph Stalin"));
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
 
            while (true) {
                System.out.println("What do you want to do?");
                System.out.print("\n1: Manage Units\n2: Manage Cities\n3: Select Research\n4: Next Turn\n---");
 
                choice = scan.nextInt();
                scan.nextLine();
 
                if (choice == 1) {
                    manageUnit(player);
                } else if (choice == 2) {
                    manageCity(player);
                } else if (choice == 3) {
                    setResearch(player, player.getResearchableTech());
                } else if (choice == 4) {
                    if (player.canEndTurn()) {
                        System.out.println("End turn!");
                        break;
                    } else {
                        System.out.println("Cannot end turn! You still need to select things");
                    }
                } else {
                    System.out.println("Invalid input!");
                }
 
                repaint();
            }
        }
 
        loop();
 
    }
 
    //CITY MANAGEMENT
    //<editor-fold>
    public void manageCity(Player player){
               
        if (player.getCityList().size() == 0){
            System.out.println("You have no cities right now");
            return;
        }
        
        System.out.print("Which city would you like to manage?: ");
        System.out.println(showCities(0,player.getCityList()));
        choice = scan.nextInt();
        scan.nextLine();
        
        City c = player.getCityList().get(choice);
        System.out.println("The selected city is: " + c.getName());
        
        //do more shit
    }
   
    public String showCities(int index, ArrayList<City> cityList) {
        if (index == cityList.size()) {
            return "";
        }
 
        return index + ") " + cityList.get(index).getName() + "\n" + showCities(index + 1, cityList);
    }
    //</editor-fold>
 
    //RESEARCH SETTING
    //<editor-fold>
    public void setResearch(Player player, Set<TechType> researchableTech) {
        
        System.out.println("Your current research is " + player.getResearch().name());
        System.out.println("Would you like to change it?");
        System.out.print("0) Yes\n1) No \n---");
        choice = scan.nextInt();
        scan.nextLine();
        
        switch (choice){
            case 0: 
                break;
            case 1:
                return;
            default:
                System.out.println("Wrong input, returned");
                return;
        }
        
        
        TechType[] researchableList = researchableTech.toArray(new TechType[researchableTech.size()]);
        System.out.println(showResearchable(researchableList, 0));
        
        System.out.print("Which tech would you like to research?: ");
        player.setResearch(researchableList[scan.nextInt()]);
        scan.nextLine();
        System.out.println("The current tech being researched is: " + player.getResearch().name());
 
    }
 
    public String showResearchable(TechType[] researchableTech, int index) {
        if (index == researchableTech.length) {
            return null;
        }
        return index+ ") "+ researchableTech[index].name() + "\n"+ showResearchable(researchableTech, index + 1);
    }
    //</editor-fold>
    
    //UNIT MANAGEMENT
    //<editor-fold>
    public void manageUnit(Player p) {
 
        ArrayList<Unit> list = new ArrayList<Unit>();
        for (Unit u : p.getUnitList()) {
            if (u.canMove()) {
                list.add(u);
            }
        }
 
        if (list.isEmpty()) {
            System.out.println("You have no movable unit at this time!");
            return;
        }
 
        System.out.println("Please select a unit from the following list: ");
 
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ") " + list.get(i).getClass().getSimpleName());
        }
 
        choice = scan.nextInt();
        scan.nextLine();
 
        Unit unit = list.get(choice);
 
        System.out.println("What do you want to do with " + unit.getClass().getSimpleName());
        System.out.print("\n1: Move Units\n2: Attack\n3: Special Actions\n4: Rest\n---");
 
        choice = scan.nextInt();
        scan.nextLine();
 
        switch (choice) {
            case 1:
                moveUnit(unit);
                break;
            case 2:
                if (unit instanceof MilitaryUnit) {
                    attackUnit((MilitaryUnit) unit);
                } else {
                    System.out.println("This unit cannot attack!");
                    return;
                }
                break;
            case 3:
                actUnit(unit);
                break;
            case 4:
                System.out.println(unit.getClass().getSimpleName() + " is resting!");
                unit.setMovement(0);
                break;
            default:
                System.out.println("Invalid input number");
                return;
 
        }
    }
 
    public void moveUnit(Unit unit) {
 
        Point[] movable = unit.getMoves();
 
        if (movable.length == 0) {
            System.out.println("This unit cannot move anywhere!");
            return;
        }
 
        System.out.println("Where do you want this unit to move to?");
 
        for (int i = 0; i < movable.length; i++) {
            System.out.println(i + ") x: " + movable[i].x + "  y: " + movable[i].y);
        }
 
        System.out.print("Input here: ");
        choice = scan.nextInt();
        scan.nextLine();
 
        if (choice >= 0 && choice < movable.length) {
            unit.move(movable[choice]);
            System.out.println("Unit moved!");
            return;
        }
 
        System.out.println("Invalid input!");
 
    }
 
    public void attackUnit(MilitaryUnit unit) {
 
        Point[] attackable = unit.getAttackable();
 
        if (attackable.length == 0) {
            System.out.println("There is nothing for this unit to attack!");
            return;
        }
 
        System.out.println("Which unit would you like to attack? ");
 
        for (int i = 0; i < attackable.length; i++) {
            Unit victim = map.getTile(attackable[i].x, attackable[i].y).getUnit();
            System.out.println(i + ") " + victim.getClass().getSimpleName() + " x: " + victim.getX() + " y: " + victim.getY());
        }
 
        System.out.print("Enter here: ");
        choice = scan.nextInt();
        scan.nextLine();
 
        if (choice < 0 || choice > attackable.length - 1) {
            System.out.println("Invalid selection!");
            return;
        }
 
        unit.attack(map.getTile(attackable[choice].x, attackable[choice].y).getUnit());
    }
 
    public void actUnit(Unit u) {
        if (u instanceof SettlerUnit) {
            System.out.println("What would you like to name this new city?");
            ((SettlerUnit) u).settle(scan.nextLine());
        }
 
        if (u instanceof BuilderUnit) {
            System.out.println("How would you like to improve the tile?");
            
        }
 
    }
 
    //</editor-fold>
    
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
                
                if (playerList.get(0).getCityList().size() != 0){
                    if (playerList.get(0).getCityList().get(0).getOwnedTiles().contains(t)){
                        g.setColor(Color.PINK);
                    }
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