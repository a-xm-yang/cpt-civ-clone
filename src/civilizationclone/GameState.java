package civilizationclone;

import civilizationclone.GameMap.MapSize;
import civilizationclone.Unit.SettlerUnit;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.WarriorUnit;
import java.awt.Point;
import java.util.ArrayList;

public class GameState {

    private GameMap gameMap;
    private ArrayList<Player> playerList;
    private Player currentPlayer;

    //for single player purposes
    public GameState(ArrayList<Player> playerList, MapSize ms) {

        this.playerList = playerList;

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
                    break;
                }
            } while (true);
        }

        currentPlayer = playerList.get(0);
        currentPlayer.startTurn();
    }

    //TEMPORARY
    public void updateCurrentPlayer() {
        if (this.getPlayerList().indexOf(this.getCurrentPlayer()) == this.getPlayerList().size() - 1) {
            this.currentPlayer = this.getPlayerList().get(0);
        } else {
            this.currentPlayer = this.getPlayerList().get(this.getPlayerList().indexOf(this.getCurrentPlayer()) + 1);
        }

        this.getCurrentPlayer().startTurn();
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
}
