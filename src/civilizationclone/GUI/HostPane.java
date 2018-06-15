package civilizationclone.GUI;

import civilizationclone.GameState;
import civilizationclone.Network.Server;
import civilizationclone.Player;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class HostPane extends MultiplayerPane {

    private Server server;

    public HostPane(GameState gameState, int resX, int resY, boolean isMuted, Stage primaryStage, Server server) {
        super(gameState, resX, resY, isMuted, primaryStage);
        this.server = server;
        server.setPane(this);
    }

    @Override
    public synchronized void requestAction(String s) {

        if (s.startsWith("Chat")) {
            server.sendToAll(s);
            return;
        }

        if (isActivityLocked()) {
            if (s.startsWith("Next")) {
                setActivity(getGameState().getCurrentPlayer().getName(), false);
                checkNextTurn();
                server.sendToAll(s);
            }
        } else {
            if (s.startsWith("Cancel")) {
                setActivity(getGameState().getCurrentPlayer().getName(), true);
            } else {
                getGameState().decodeAction(s);
            }
            server.sendToAll(s);
        }

        updateInfo();
        readNotificationFromGame();
    }

    @Override
    public synchronized void receiveAction(String s) {

        if (s.startsWith("Chat")) {
            getChatroom().receiveMessage(s.substring(4));
            return;
        }

        if (s.startsWith("Next")) {
            String[] msg = s.split("/");
            setActivity(msg[1], false);
            server.sendToAll(s);
            checkNextTurn();
        } else if (s.startsWith("Cancel")) {
            String[] msg = s.split("/");
            setActivity(msg[1], true);
            server.sendToAll(s);
        } else if (getGameState().decodeAction(s)) {
            server.sendToAll(s);
        }

        readNotificationFromGame();
        updateInfo();
    }

    @Override
    public void endGameCheck() {

        for (Player p : getGameState().getPlayerList()) {
            if (p == getGameState().getCurrentPlayer()) {
                if (getGameState().getCurrentPlayer().isDefeated()) {
                    //Play defeat audio and show victory screens
                    mp.setVolume(0.5);
                    dmp = new MediaPlayer(loss);
                    dmp.play();
                    dmp.setMute(isIsMuted());
                    this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), true));
                } else if (getGameState().getPlayerList().size() == 1) {
                    //Play victory audio and show victory screen
                    mp.setVolume(0.5);
                    wmp = new MediaPlayer(win);
                    wmp.play();
                    wmp.setMute(isIsMuted());
                    this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), false));
                }
                mp.setVolume(1.0);
            } else {
                if (p.isDefeated()) {
                    getStatusBar().removeHead(p.getLeader().name());
                }
            }
        }
    }
}
