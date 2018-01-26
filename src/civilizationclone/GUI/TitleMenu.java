package civilizationclone.GUI;

import civilizationclone.GameMap;
import civilizationclone.GameMap.MapSize;
import civilizationclone.Player;
import civilizationclone.Player.Leader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TitleMenu extends Group {

    //STAGE HANDLING COMPONENTS
    private int resX, resY;
    private Stage primaryStage;
    private Pane titlePane;
    private Pane startPane;
    private Pane optionPane;
    //GENERAL NODES
    private Canvas canvas;
    private MediaPlayer mp;
    //MAP LAUNCHING NODES
    private ChoiceBox mapOption;
    private ArrayList<PlayerBox> playerList;
    private Button startButton;
    private Button addLeader;
    private ComboBox leaderOption;
    //EFFFECTS
    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 5);

    public TitleMenu(int resX, int resY, Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.resX = resX;
        this.resY = resY;

        playerList = new ArrayList<>();

        canvas = new Canvas(resX + 50, resY + 50);
        canvas.getGraphicsContext2D().drawImage(ImageBuffer.getImage(MiscAsset.TITLE_BACKGROUND), 0, 0, resX + 50, resY + 50);

        try {
            Font.loadFont(new FileInputStream("src/Assets/Misc/menuFont.ttf"), 50);
        } catch (FileNotFoundException ex) {
        }

        titlePane = initTitle();
        startPane = initPlay();
        optionPane = initOption();

        getChildren().add(canvas);
        getChildren().add(titlePane);

        Media media = new Media(new File("src/Assets/Misc/Sogno_di_Volare.mp3").toURI().toString());
        mp = new MediaPlayer(media);
        mp.play();
    }

    private Pane initTitle() {

        //shift the whole thing by using fixX and fixY
        Pane root = new Pane();

        int fixX = 10 + (resX - 1200);
        int fixY = 110 + (resY - 800);

        CivTitle title = new CivTitle("C O L O N I Z A T I O N  I I", 48);
        title.setTranslateX(300 + fixX);
        title.setTranslateY(230 + fixY);

        CivTitle subtitle = new CivTitle("A L E X A N D E R   Y A N G ` S", 11);
        subtitle.setTranslateX(521 + fixX);
        subtitle.setTranslateY(175 + fixY);

        CivMenuItems word1 = new CivMenuItems("PLAY");
        word1.setTranslateX(535 + fixX);
        word1.setTranslateY(325 + fixY);
        word1.setOnMouseClicked(e -> {
            openPlay();
        });

        CivMenuItems word2 = new CivMenuItems("OPTIONS");
        word2.setTranslateX(535 + fixX);
        word2.setTranslateY(375 + fixY);

        Line line = new Line(525 + fixX, 300 + fixY, 525 + fixX, 380 + fixY);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));

        root.getChildren().addAll(title, subtitle, word1, word2, line);

        return root;
    }

    private Pane initPlay() {

        Pane root = new Pane();

        int fixX = 62 + (resX - 1200);
        int fixY = 50 + (resY - 800);

        Rectangle rect1 = new Rectangle(270 + fixX, 30 + fixY, 550, 680);
        rect1.setFill(Color.BLACK);
        rect1.setOpacity(0.45);

        Rectangle rect2 = new Rectangle(280 + fixX, 42 + fixY, 530, 80);
        rect2.setFill(Color.BLACK);
        rect2.setOpacity(0.5);

        Rectangle rect3 = new Rectangle(280 + fixX, 165 + fixY, 530, 532);
        rect3.setFill(Color.BLACK);
        rect3.setOpacity(0.5);

        Rectangle rect4 = new Rectangle(345 + fixX, 195 + fixY, 400, 470);
        rect4.setFill(Color.BLACK);
        rect4.setOpacity(0.55);

        Line line1 = new Line(271 + fixX, 128 + fixY, 818 + fixX, 128 + fixY);
        line1.setStrokeWidth(3);
        line1.setStroke(Color.ROSYBROWN);

        Line line2 = new Line(271 + fixX, 160 + fixY, 818 + fixX, 160 + fixY);
        line2.setStrokeWidth(3);
        line2.setStroke(Color.TAN);

        CivTitle heading = new CivTitle("COLONIZATION II", 45);
        heading.setTranslateX(345 + fixX);
        heading.setTranslateY(113 + fixY);

        CivTitle subheading = new CivTitle("ALEXANDER YANG'S", 11);
        subheading.setTranslateX(490 + fixX);
        subheading.setTranslateY(65 + fixY);

        CivTitle text1 = new CivTitle("CREATE GAME", 17);
        text1.setTranslateX(485 + fixX);
        text1.setTranslateY(150 + fixY);

        CivTitle map = new CivTitle("MAP : ", 15);
        map.setTranslateX(400 + fixX);
        map.setTranslateY(645 + fixY);

        mapOption = new ChoiceBox();
        mapOption.setItems(FXCollections.observableArrayList(
                "SMALL", new Separator(), "MEDIUM",
                new Separator(), "LARGE")
        );
        mapOption.setTranslateX(455 + fixX);
        mapOption.setTranslateY(627.5 + fixY);
        mapOption.setValue("MEDIUM");

        startButton = new Button("Start");
        startButton.setFont(Font.font("Penumbra HalfSerif Std", 18));
        startButton.setTranslateX(632 + fixX);
        startButton.setTranslateY(622 + fixY);
        startButton.setDisable(true);
        startButton.setOnMouseClicked(e -> {
            startGame();
        });

        addLeader = new Button("Add");
        addLeader.setFont(Font.font("Penumbra HalfSerif Std", 15));
        addLeader.setTranslateX(575 + fixX);
        addLeader.setTranslateY(207 + fixY);
        addLeader.setOnMouseClicked(e -> {
            addLeader();
        });

        leaderOption = new ComboBox();
        for (Leader l : Leader.values()) {
            leaderOption.getItems().add(l.name());
        }
        leaderOption.setTranslateX(430 + fixX);
        leaderOption.setTranslateY(210 + fixY);

        root.getChildren().addAll(rect3, rect4, rect1, rect2, line1, line2, heading, subheading, text1, map, startButton, mapOption, leaderOption, addLeader);

        return root;
    }

    private Pane initOption() {

        return null;
    }

    private void addLeader() {

        if (playerList.size() > 6 || leaderOption.getValue() == null) {
            return;
        }

        String selection = (String) leaderOption.getValue();
        leaderOption.getItems().remove(leaderOption.getValue());
        leaderOption.setVisibleRowCount(leaderOption.getItems().size());

        PlayerBox pb = new PlayerBox(selection);
        playerList.add(pb);
        startPane.getChildren().add(pb);
        formatPlayerBoxes();

        if (playerList.size() >= 2) {
            startButton.setDisable(false);
            if (playerList.size() == 6) {
                addLeader.setVisible(false);
                leaderOption.setVisible(false);
            }
        }
    }

    private void removeLeader(PlayerBox pb) {
        leaderOption.getItems().add(pb.leader.name());
        leaderOption.setVisibleRowCount(leaderOption.getItems().size());
        playerList.remove(pb);
        startPane.getChildren().remove(pb);

        formatPlayerBoxes();

        if (playerList.size() < 2) {
            startButton.setDisable(true);
        }

        addLeader.setVisible(true);
        leaderOption.setVisible(true);
    }

    private void formatPlayerBoxes() {
        int fixX = 62 + (resX - 1200);
        int fixY = 50 + (resY - 800);

        // Rectangle rect4 = new Rectangle(345 + fixX, 195 + fixY, 400, 470);
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).setTranslateX(345 + fixX);
            playerList.get(i).setTranslateY(195 + fixY + i * 62);
        }

        addLeader.setTranslateY(207 + fixY + 62.0 * playerList.size());
        leaderOption.setTranslateY(210 + fixY + 62.0 * playerList.size());
        addLeader.toFront();
        leaderOption.toFront();
    }

    private void startGame() {
        if (playerList.isEmpty()) {
            return;
        }

        ArrayList<Player> list = new ArrayList<>();
        for (PlayerBox pb : playerList) {
            switch (pb.getLeader()) {
                case STALIN:
                    list.add(new Player("Joseph Stalin"));
                    break;
                case ZEDONG:
                    list.add(new Player("Mao Zedong"));
                    break;
                case HITLER:
                    list.add(new Player("Adolf Hitler"));
                    break;
                case MUSSOLINI:
                    list.add(new Player("Bennito Mussolini"));
                    break;
                case CHURCHILL:
                    list.add(new Player("Winston Churchill"));
                    break;
                case ROOSEVELT:
                    list.add(new Player("Franklin Roosevelt"));
                    break;
            }
        }

        MapSize ms = MapSize.MEDIUM;
        for (MapSize m : MapSize.values()) {
            if (m.name().equalsIgnoreCase((String) mapOption.getValue())) {
                ms = m;
                break;
            }
        }
        int seed = (int) (Math.random() * 1000 + 1);
        GameMap gameMap = new GameMap(ms, seed);

        //change later
        primaryStage.setScene(new Scene(new GamePane(gameMap, list, resX, resY, true)));
        mp.pause();
    }

    private void openPlay() {
        getChildren().clear();
        getChildren().add(canvas);
        getChildren().add(startPane);
    }

    private class PlayerBox extends Group {

        private Text text;
        private Leader leader;
        private Circle removeIcon;

        public PlayerBox(String name) {

            ImageView leaderHead = new ImageView();
            for (Leader l : Leader.values()) {
                if (l.name().equalsIgnoreCase(name)) {
                    leader = l;
                    break;
                }
            }
            leaderHead.setImage(ImageBuffer.getImage(leader));
            leaderHead.setTranslateX(20);
            leaderHead.setTranslateY(1);

            Rectangle rect = new Rectangle(400, 62);
            rect.setFill(Color.STEELBLUE);
            rect.setOpacity(0.75);
            rect.setStrokeWidth(3);
            rect.setStroke(Color.BISQUE);

            text = new Text(name);
            text.setFont(Font.font("Penumbra HalfSerif Std", 25));
            text.setFill(Color.WHITE);
            text.setTranslateX(115);
            text.setTranslateY(41);

            removeIcon = new Circle(360, 30, 20);
            removeIcon.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            removeIcon.effectProperty().bind(
                    Bindings.when(hoverProperty()).then(shadow).otherwise(blur)
            );
            removeIcon.setOnMouseClicked(e -> {
                removeLeader(this);
            });

            getChildren().addAll(rect, leaderHead, text, removeIcon);

        }

        public Leader getLeader() {
            return leader;
        }

    }

    private class CivTitle extends Group {

        private Text text = new Text();

        public CivTitle(String title, int size) {

            text.setText(title);

            text.setFont(Font.font("Penumbra HalfSerif Std", size));

            text.setFill(Color.WHITE);
            text.setEffect(new DropShadow(30, Color.BLACK));

            getChildren().addAll(text);
        }
    }

    private class CivMenuItems extends Group {

        private Text text;

        public CivMenuItems(String words) {

            Rectangle rect = new Rectangle(-8, -30, 150, 40);
            rect.setStroke(Color.color(1, 1, 1, 0.8));
            rect.setEffect(new GaussianBlur());

            rect.fillProperty().bind(
                    Bindings.when(pressedProperty()).then(Color.color(0, 0, 0, 0.75)).otherwise(Color.color(0, 0, 0, 0.25))
            );

            text = new Text(words);

            text.setFont(Font.font("Penumbra HalfSerif Std", 25));
            text.setFill(Color.WHITE);

            text.effectProperty().bind(
                    Bindings.when(hoverProperty()).then(shadow).otherwise(blur)
            );

            getChildren().addAll(rect, text);

        }

    }
}
