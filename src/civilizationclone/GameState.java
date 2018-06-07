package civilizationclone;

import civilizationclone.GameMap.MapSize;
import civilizationclone.Tile.Improvement;
import civilizationclone.Unit.BuilderUnit;
import civilizationclone.Unit.MilitaryUnit;
import civilizationclone.Unit.SettlerUnit;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.UnitType;
import civilizationclone.Unit.WarriorUnit;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class GameState {

    private GameMap gameMap;
    private ArrayList<Player> playerList;
    private Player currentPlayer;
    private ArrayList<String> notificationLog;

    //for single player purposes
    public GameState(ArrayList<Player> playerList, MapSize ms) {

        this.playerList = playerList;
        notificationLog = new ArrayList<String>();

        //construct a random new map 
        int seed = (int) (Math.random() * 1000 + 1);
        gameMap = new GameMap(ms, seed);

        Unit.referenceMap(gameMap);
        City.referenceMap(gameMap);

        for (Player player : playerList) {
            do {
                Point p = new Point((int) (Math.random() * (gameMap.getSize() - 4)) + 2, (int) (Math.random() * (gameMap.getSize() - 1)));
                if (gameMap.canSpawn(p)) {
                    player.addUnit(new SettlerUnit(player, p));
                    player.addUnit(new WarriorUnit(player, new Point(p.x, p.y + 1)));
                    updateFogOfWar(player);
                    break;
                }
            } while (true);
        }

        currentPlayer = playerList.get(0);
        currentPlayer.startTurn();
    }

    public GameState(ArrayList<Player> playerList, MapSize ms, int seed, Player currentPlayer) {
        
        this.playerList = playerList;
        notificationLog = new ArrayList<String>();

        //construct a random new map 
        gameMap = new GameMap(ms, seed);

        Unit.referenceMap(gameMap);
        City.referenceMap(gameMap);

        Random random = new Random(seed);
        for (Player player : playerList) {
            do {
                Point p = new Point((int) (random.nextDouble() * (gameMap.getSize() - 4)) + 2, (int) (random.nextDouble() * (gameMap.getSize() - 1)));
                if (gameMap.canSpawn(p)) {
                    player.addUnit(new SettlerUnit(player, p));
                    player.addUnit(new WarriorUnit(player, new Point(p.x, p.y + 1)));
                    updateFogOfWar(player);
                    break;
                }
            } while (true);
        }

        this.currentPlayer = currentPlayer;
        
        processAllPlayersTurn();
    }

    public boolean decodeAction(String s) {

        try {
            //decode the notification given
            System.out.println(s);
            String[] msg = s.split("/");

            //First check the player
            Player player = null;
            for (Player p : playerList) {
                if (p.getName().equals(msg[0])) {
                    player = p;
                    break;
                }
            }

            if (msg[1].equals("Unit")) {
                //UNIT RELATED HANDLING
                //<editor-fold>
                Unit unit = null;
                for (Unit u : player.getUnitList()) {
                    if (Integer.toString(u.hashCode()).equals(msg[2])) {
                        unit = u;
                        break;
                    }
                }

                if (msg[3].equals("Move")) {
                    int x = getIntFromString(msg[4]);
                    int y = getIntFromString(msg[5]);

                    unit.move(new Point(x, y));
                    updateFogOfWar(player);

                } else if (msg[3].equals("Attack") && unit instanceof MilitaryUnit) {

                    int x = getIntFromString(msg[4]);
                    int y = getIntFromString(msg[5]);

                    if (gameMap.getTile(x, y).hasUnit()) {

                        Unit enemyUnit = gameMap.getTile(x, y).getUnit();

                        if (enemyUnit.getPlayer() == unit.getPlayer()) {
                            return false;
                        }

                        //This message only displays if you are one of the parties involved
                        if (unit.getPlayer() == currentPlayer || enemyUnit.getPlayer() == currentPlayer) {

                            if (enemyUnit instanceof MilitaryUnit) {

                                int unitHealth = ((MilitaryUnit) unit).getHealth();
                                int enemyHealth = ((MilitaryUnit) enemyUnit).getHealth();

                                ((MilitaryUnit) unit).attack(enemyUnit);

                                notificationLog.add(unit.getPlayer().getName() + "'s " + unit.toString() + " (-" + (unitHealth - ((MilitaryUnit) unit).getHealth()) + ") attacked "
                                        + enemyUnit.getPlayer().getName() + "'s " + enemyUnit.toString() + " (-" + (enemyHealth - ((MilitaryUnit) enemyUnit).getHealth()) + ")!");

                            } else {
                                ((MilitaryUnit) unit).attack(enemyUnit);
                                notificationLog.add(unit.getPlayer().getName() + "'s " + unit.toString() + " has killed "
                                        + enemyUnit.getPlayer().getName() + "'s " + enemyUnit.toString() + "!");
                            }

                        } else {
                            ((MilitaryUnit) unit).attack(enemyUnit);
                        }

                        updateFogOfWar(enemyUnit.getPlayer());

                    } else if (gameMap.getTile(x, y).hasCity()) {

                        City enemyCity = gameMap.getTile(x, y).getCity();

                        if (enemyCity.getPlayer() == unit.getPlayer()) {
                            return false;
                        }

                        //This message only displays if you are one of the parties involved
                        if (unit.getPlayer() == currentPlayer || enemyCity.getPlayer() == currentPlayer) {

                            int unitHealth = ((MilitaryUnit) unit).getHealth();
                            int enemyHealth = enemyCity.getHealth();

                            ((MilitaryUnit) unit).siegeAttack(enemyCity);

                            notificationLog.add(unit.getPlayer().getName() + "'s " + unit.toString() + " (-" + (unitHealth - ((MilitaryUnit) unit).getHealth()) + ") attacked "
                                    + enemyCity.getPlayer().getName() + "'s " + enemyCity.getName() + " (-" + (enemyHealth - enemyCity.getHealth()) + ")!");

                            if (enemyCity.getPlayer() == unit.getPlayer()) {
                                notificationLog.add(unit.getPlayer().getName() + " has captured " + enemyCity.getName() + "!");
                            }

                        } else {
                            ((MilitaryUnit) unit).siegeAttack(enemyCity);
                        }

                        updateFogOfWar(enemyCity.getPlayer());

                    } else {
                        return false;
                    }

                    updateFogOfWar(player);

                } else if (msg[3].equals("Fortify")) {
                    MilitaryUnit m = (MilitaryUnit) unit;
                    m.setFortified(true);
                    m.setMovement(0);
                } else if (msg[3].equals("EndTurn")) {
                    unit.setMovement(0);
                } else if (msg[3].equals("Settle")) {
                    if (unit instanceof SettlerUnit && ((SettlerUnit) unit).canSettle()) {
                        if (unit.getPlayer() == currentPlayer) {
                            notificationLog.add("The new settlement of " + msg[4] + " has been built!");
                        } else {
                            notificationLog.add("Rumour has it that " + unit.getPlayer().getName() + " has established a new settlement!");
                        }

                        ((SettlerUnit) unit).settle(msg[4]);

                    } else {
                        return false;
                    }
                } else if (msg[3].equals("Build")) {
                    BuilderUnit builderUnit = (BuilderUnit) unit;

                    for (Improvement i : builderUnit.getPossibleImprovements()) {
                        if (i.toString().equals(msg[4])) {
                            builderUnit.improve(i);
                            break;
                        }
                    }
                } else if (msg[3].equals("Kill")) {
                    unit.delete();
                    updateFogOfWar(player);
                } else {
                    return false;
                }

                //</editor-fold>
            } else if (msg[1].equals("City")) {
                //CITY RELATED HANDLINGS
                //<editor-fold>
                City city = null;
                for (City c : player.getCityList()) {
                    if (Integer.toString(c.hashCode()).equals(msg[2])) {
                        city = c;
                        break;
                    }
                }

                if (msg[3].equals("Produce")) {

                    for (UnitType t : city.getPlayer().getBuildableUnit()) {
                        if (t.toString().equals(msg[4])) {
                            city.setProduction(t);
                            return true;
                        }
                    }

                    for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                        if (c.toString().equals(msg[4])) {
                            city.setProduction(c);
                            break;
                        }
                    }

                } else if (msg[3].equals("Purchase")) {

                    for (UnitType t : city.getPlayer().getBuildableUnit()) {
                        if (t.toString().equals(msg[4])) {
                            if (!city.getCityTile().hasUnit()) {
                                if (city.getPlayer().getCurrentGold() > t.getPurchaseCost()) {
                                    city.getPlayer().setCurrentGold(city.getPlayer().getCurrentGold() - t.getPurchaseCost());
                                } else {
                                    return false;
                                }
                                city.getPlayer().addUnit((Unit) t.getCorrespondingClass().getConstructor(City.class).newInstance(city));
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }

                    for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                        if (c.toString().equals(msg[4])) {
                            if (city.getPlayer().getCurrentGold() > c.getPurchaseCost()) {
                                city.getPlayer().setCurrentGold(city.getPlayer().getCurrentGold() - c.getPurchaseCost());
                            } else {
                                return false;
                            }
                            city.addCityProject(c);
                            city.calcIncome();
                            break;
                        }
                    }

                } else if (msg[3].equals("Citizen")) {
                    if (msg[4].equals("Add")) {
                        int x = getIntFromString(msg[5]);
                        int y = getIntFromString(msg[6]);
                        city.getWorkedTiles().add(gameMap.getTile(x, y));
                        city.calcIncome();
                    } else if (msg[4].equals("Remove")) {
                        int x = getIntFromString(msg[5]);
                        int y = getIntFromString(msg[6]);
                        city.getWorkedTiles().remove(gameMap.getTile(x, y));
                        city.calcIncome();
                    }
                } else if (msg[3].equals("Expand")) {
                    int x = getIntFromString(msg[4]);
                    int y = getIntFromString(msg[5]);
                    city.addTile(gameMap.getTile(x, y));
                    updateFogOfWar(player);
                } else {
                    return false;
                }
                //</editor-fold>
            } else if (msg[1].equals("Science")) {
                //SCIENCE HANDLING
                //<editor-fold>
                for (TechType t : player.getResearchableTech()) {
                    if (msg[2].equals(t.toString())) {
                        player.setResearch(t);
                        break;
                    }
                }
                //</editor-fold>
            }

        } catch (Exception e) {
            System.out.println("Decode failure!");
            return false;
        }

        return true;

    }

    public void updateCurrentPlayer() {
        if (this.getPlayerList().indexOf(this.getCurrentPlayer()) == this.getPlayerList().size() - 1) {
            this.currentPlayer = this.getPlayerList().get(0);
        } else {
            this.currentPlayer = this.getPlayerList().get(this.getPlayerList().indexOf(this.getCurrentPlayer()) + 1);
        }
    }

    public void processCurrentPlayerTurn() {

        if (currentPlayer.isDefeated()) {
            Player temp = currentPlayer;
            updateCurrentPlayer();
            getPlayerList().remove(temp);
        }

        //Check for notification updates in tech and production
        TechType t = currentPlayer.getResearch();
        String[] productionList = new String[currentPlayer.getCityList().size()];

        for (int i = 0; i < productionList.length; i++) {
            City c = currentPlayer.getCityList().get(i);
            if (c.getCurrentUnit() != null) {
                productionList[i] = c.getCurrentUnit().toString();
            } else if (c.getCurrentProject() != null) {
                productionList[i] = c.getCurrentProject().toString();
            }
        }

        currentPlayer.startTurn();

        if (currentPlayer.getResearch() == TechType.NONE && t != TechType.NONE) {
            notificationLog.add("Research for " + t.toString() + " is complete!");
        }

        for (int i = 0; i < productionList.length; i++) {
            City c = currentPlayer.getCityList().get(i);

            //only happens if both are null
            if (c.getCurrentUnit() == null && c.getCurrentProject() == null) {
                if (productionList[i] != null) {
                    notificationLog.add(c.getName() + " has finished its production of " + productionList[i] + "!");
                }
            }
        }
    }

    public void processAllPlayersTurn() {

        ArrayList<Player> tempDefeatedPlayer = new ArrayList<>();

        for (Player p : playerList) {
            if (p != currentPlayer) {
                if (p.isDefeated()) {
                    tempDefeatedPlayer.add(p);
                } else {
                    p.startTurn();
                }
            }
        }

        for (Player p : tempDefeatedPlayer) {
            notificationLog.add(p.getName() + " has been defeated!");
            playerList.remove(p);
        }

        processCurrentPlayerTurn();
    }

    public String readNotification() {

        String msg = notificationLog.get(0);
        notificationLog.remove(0);

        return msg;
    }

    private void updateFogOfWar(Player p) {
        p.addExploredTiles(gameMap.getVisibleTiles(p.getAllPositions()));
    }

    //GETTER & SETTER
    //<editor-fold>
    private int getIntFromString(String s) {

        String newString;

        if (s.contains(".")) {
            newString = s.substring(0, s.indexOf("."));
        } else {
            newString = s;
        }

        int num = -1;
        try {
            num = Integer.parseInt(newString);
        } catch (Exception e) {
            System.out.println("Integer conversion error");
        }

        return num;

    }

    public boolean isNotificationEmpty() {
        return notificationLog.isEmpty();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    //</editor-fold>
}
