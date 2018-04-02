package civilizationclone.GUI;

import civilizationclone.Tile.Improvement;
import static civilizationclone.Tile.Improvement.NONE;
import civilizationclone.Unit.*;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UnitMenu extends Pane {

    private Unit unit;
    private ArrayList<UnitOption> opts;
    private DisplayMap dmapRef;
    private Canvas infoDisplay;
    private GraphicsContext gc;
    private Text statusText;

    private Effect shadow = new DropShadow(25, Color.BLACK);
    private Effect noneEffect = null;

    public UnitMenu(Unit unit, DisplayMap dmapRef) {
        this.unit = unit;
        opts = new ArrayList<>();
        //Adds the different options for each type of unit
        if (unit instanceof SettlerUnit) {
            opts.add(new UnitOption(65, -60, 250, 40, "Move"));
            opts.add(new UnitOption(120, 0, 250, 40, "Settle"));
            opts.add(new UnitOption(120, 60, 250, 40, "End Turn"));
            opts.add(new UnitOption(65, 120, 250, 40, "Kill"));
        } else if (unit instanceof BuilderUnit) {
            opts.add(new UnitOption(65, -60, 250, 40, "Move"));
            opts.add(new UnitOption(120, 0, 250, 40, "Improve"));
            opts.add(new UnitOption(120, 60, 250, 40, "End Turn"));
            opts.add(new UnitOption(65, 120, 250, 40, "Kill"));
        } else if (unit instanceof MilitaryUnit) {
            opts.add(new UnitOption(65, -60, 250, 40, "Move"));
            opts.add(new UnitOption(120, 0, 250, 40, "Attack"));
            opts.add(new UnitOption(120, 60, 250, 40, "Fortify"));
            opts.add(new UnitOption(65, 120, 250, 40, "Kill"));
        }

        //Initilize the information display (left of unit)
        infoDisplay = new Canvas(230, 250);
        infoDisplay.setTranslateX(-195);
        infoDisplay.setTranslateY(-60);
        infoDisplay.setVisible(true);

        //Left info display
        statusText = new Text(displayUnitInfo());
        statusText.setFont(Font.font("Times New Roman", 20));
        statusText.setFill(Color.WHITE);
        statusText.setTranslateY(-25);
        statusText.setTranslateX(-180);

        //initialize graphics context for drawing color/images
        gc = infoDisplay.getGraphicsContext2D();
        gc.setFill(unit.getPlayer().getColor());
        gc.fillPolygon(new double[]{0, 230, 230, 175, 175, 230, 230, 0}, new double[]{0, 0, 40, 75, 155, 180, 220, 220}, 8);
        gc.setFill(Color.DARKSLATEGREY);
        gc.fillPolygon(new double[]{5, 225, 225, 170, 170, 225, 225, 5}, new double[]{5, 5, 35, 70, 160, 185, 215, 215}, 8);

        for (UnitOption o : opts) {
            o.setFill(Color.DARKSLATEGREY);
        }

        //Check if the option is available and if it is, make it white, otherwise grey it out
        for (UnitOption o : opts) {
            if (o.getOptionType().equals("Move")) {
                if (unit.getMovement() > 0) {
                    o.getText().setFill(Color.WHITE);
                } else {
                    o.getText().setFill(Color.DARKGREY);
                }
            } else if (o.getOptionType().equals("Attack")) {
                if ((((MilitaryUnit) unit).getAttackable().length > 0 || ((MilitaryUnit) unit).getSiegable().length > 0) && unit.getMovement() > 0) {
                    o.getText().setFill(Color.WHITE);
                } else {
                    o.getText().setFill(Color.DARKGREY);
                }
            } else if (o.getOptionType().equals("Settle")) {
                if (unit.getMovement() > 0 && ((SettlerUnit) unit).canSettle()) {
                    o.getText().setFill(Color.WHITE);
                } else {
                    o.getText().setFill(Color.DARKGREY);
                }
            } else if (o.getOptionType().equals("Fortify")) {
                if (unit.getMovement() > 0) {
                    o.getText().setFill(Color.WHITE);
                } else {
                    o.getText().setFill(Color.DARKGREY);
                }
            } else if (o.getOptionType().equals("Kill")) {
                o.getText().setFill(Color.WHITE);
            } else if (o.getOptionType().equals("Improve")) {
                if (((BuilderUnit) unit).getPossibleImprovements().length >= 1
                        && ((BuilderUnit) unit).getMapRef().getTile(unit.getX(), unit.getY()).getImprovement() == NONE
                        && unit.getMovement() > 0
                        && ((BuilderUnit) unit).getMapRef().getTile(unit.getX(), unit.getY()).getControllingCity() != (null)
                        && ((BuilderUnit) unit).getMapRef().getTile(unit.getX(), unit.getY()).getControllingCity().getPlayer().equals(unit.getPlayer())
                        && !(unit.getMapRef().getTile(unit.getX(), unit.getY()).hasCity())) {
                    o.getText().setFill(Color.WHITE);
                } else {
                    o.getText().setFill(Color.DARKGREY);
                }

            } else if (o.getOptionType().equals("End Turn")) {
                o.getText().setFill(Color.WHITE);
            }
        }

        this.dmapRef = dmapRef;

        this.getChildren().add(infoDisplay);
        this.getChildren().add(statusText);

        //If the unit belongs to the current player, display the options, otherwise it will just show the info display
        if (unit.getPlayer().equals(dmapRef.getCurrentPlayer())) {
            for (UnitOption o : opts) {
                getChildren().add(o);
                getChildren().add(o.getText());
            }
        }
        this.setVisible(true);

        setOnMouseClicked((MouseEvent event) -> {
            clickEventHandling(event);
        });
    }

    private void clickEventHandling(MouseEvent e) {
        //Check where the click originated, and call the appropriate method in zoomMap

        if (e.getTarget() instanceof UnitOption) {

            if (((UnitOption) e.getTarget()).getOptionType().equals("Move") && ((UnitOption) e.getTarget()).getText().getFill() == Color.WHITE) {
                if (unit.getMovement() > 0) {
                    dmapRef.activateMove();
                }
            } else if (((UnitOption) e.getTarget()).getOptionType().equals("Attack") && ((UnitOption) e.getTarget()).getText().getFill() == Color.WHITE) {
                if ((((MilitaryUnit) unit).getAttackable().length > 0 || ((MilitaryUnit) unit).getSiegable().length > 0) && unit.getMovement() > 0) {
                    dmapRef.activateAttack();
                }
            } else if (((UnitOption) e.getTarget()).getOptionType().equals("Settle") && ((UnitOption) e.getTarget()).getText().getFill() == Color.WHITE) {
                if (unit.getMovement() > 0) {
                    dmapRef.activateSettle();
                }
            } else if (((UnitOption) e.getTarget()).getOptionType().equals("Fortify") && ((UnitOption) e.getTarget()).getText().getFill() == Color.WHITE) {
                if (unit.getMovement() > 0) {
                    dmapRef.activateFortify();
                }
            } else if (((UnitOption) e.getTarget()).getOptionType().equals("Kill") && ((UnitOption) e.getTarget()).getText().getFill() == Color.WHITE) {
                dmapRef.activateKill();
            } else if (((UnitOption) e.getTarget()).getOptionType().equals("Improve") && ((UnitOption) e.getTarget()).getText().getFill() == Color.WHITE) {
                if (((BuilderUnit) unit).getPossibleImprovements().length >= 1 && ((BuilderUnit) unit).getMapRef().getTile(unit.getX(), unit.getY()).getImprovement() == NONE && unit.getMovement() > 0) {
                    getChildren().add(new BuildMenu(unit));
                    return;
                }
            } else if (((UnitOption) e.getTarget()).getOptionType().equals("End Turn") && ((UnitOption) e.getTarget()).getText().getFill() == Color.WHITE) {
                dmapRef.activateEndTurn();
            }
        }
        delete();

    }

    private String displayUnitInfo() {
        String msg = "";

        msg = msg + unit.getPlayer().getName().substring(unit.getPlayer().getName().indexOf(" ") + 1) + "'s " + unit.getClass().getSimpleName().substring(0, unit.getClass().getSimpleName().length() - 4) + "\n\n";

        msg = msg + "Movement: " + unit.getMovement() + "/" + unit.getMAX_MOVEMENT() + "\n";
        if (unit instanceof MilitaryUnit) {
            msg = msg + "Health: " + ((MilitaryUnit) unit).getHealth() + " / " + ((MilitaryUnit) unit).getMAX_HEALTH() + "\n";
            if (unit instanceof RangeUnit) {
                msg = msg + "Range Combat: " + ((MilitaryUnit) unit).getCombat();
                msg = msg + "\nMelee Combat: " + ((RangeUnit) unit).getCloseCombat();
            } else if (unit instanceof SiegeUnit) {
                msg = msg + "Combat: " + ((MilitaryUnit) unit).getCombat();
                msg = msg + "\nSiege: " + ((SiegeUnit) unit).getSiegeCombat();
            } else {
                msg = msg + "Combat: " + ((MilitaryUnit) unit).getCombat();
            }
            if (((MilitaryUnit) unit).isFortified()) {
                msg = msg + "\n\nFortified";
            }
        } else {
            msg = msg + "Health: 1/1 \n";
        }
        if (unit instanceof BuilderUnit) {
            msg = msg + "Builds: " + ((BuilderUnit) unit).getActions();
        }

        return msg;
    }

    void delete() {
        dmapRef.getChildren().remove(this);
    }

    private class UnitOption extends Rectangle {

        private int x, y, w, h;
        //Rectangle rect;
        private String optionType;
        private Text text;

        public UnitOption(int x, int y, int w, int h, String optionType) {
            super(x, y, w, h);

            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.optionType = optionType;

            this.text = new Text(optionType);
            this.text.setTranslateX(this.x + 20);
            this.text.setTranslateY(this.y + 25);
            this.text.setFill(Color.WHITE);
            this.text.setFont(Font.font("Times New Roman", 20));
            this.text.setMouseTransparent(true);

            this.setFill(Color.BLACK);

            effectProperty().bind(
                    Bindings.when(hoverProperty()).then(shadow).otherwise(noneEffect)
            );
        }

        public Rectangle getRect() {
            return this;
        }

        public String getOptionType() {
            return optionType;
        }

        public Text getText() {
            return text;
        }

    }

    private class BuildMenu extends Pane {

        private Rectangle border;
        private ComboBox comboBox;
        private Circle closeButton, confirmButton;
        private Text title, info;
        private ImageView display;

        private Unit unit;
        private boolean canConfirm;

        public BuildMenu(Unit unit) {

            this.unit = unit;
            canConfirm = false;

            border = new Rectangle(160, 150);
            border.setFill(Color.DARKSLATEGRAY);
            setTranslateX(-190);
            setTranslateY(-55);

            closeButton = new Circle(130, 180, 30);
            closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            closeButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });
            closeButton.effectProperty().bind(
                    Bindings.when(closeButton.hoverProperty()).then(shadow).otherwise(noneEffect)
            );

            confirmButton = new Circle(80, 180, 22);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOpacity(0.1);
            confirmButton.setOnMouseClicked((e) -> {
                e.consume();
                if (canConfirm) {
                    confirm();
                }

            });
            confirmButton.effectProperty().bind(
                    Bindings.when(confirmButton.hoverProperty()).then(shadow).otherwise(noneEffect)
            );

            title = new Text("WHAT WOULD YOU \n LIKE TO BUILD");
            title.setFont(Font.font("Times New Roman", 22));
            title.setFill(Color.WHITESMOKE);
            title.setTranslateY(25);
            title.setTranslateX(5);

            initializeComboBox();
            comboBox.setOnAction(e -> {
                updateInfo();
                e.consume();
            });

            info = new Text();
            info.setFont(Font.font("Times New Roman", 18));
            info.setFill(Color.WHITESMOKE);
            info.setWrappingWidth(200);
            info.setTranslateX(350);
            info.setTranslateY(160);

            display = new ImageView();
            display.setTranslateX(40);
            display.setTranslateY(85);

            getChildren().addAll(border, closeButton, confirmButton, title, comboBox, info, display);
        }

        private void updateInfo() {

            canConfirm = true;
            confirmButton.setOpacity(1);

            //Determine what exactly did the person click
            for (Improvement i : ((BuilderUnit) unit).getPossibleImprovements()) {
                display.setImage(ImageBuffer.getImage(i));
                info.setVisible(true);
                display.setVisible(true);
                return;
            }
        }

        private void confirm() {
            String selection = (String) comboBox.getValue();
            dmapRef.activateBuild(selection);
            close();
        }

        private void close() {
            if (getParent() instanceof UnitMenu) {
                ((UnitMenu) getParent()).delete();
            }
            dmapRef.repaint();
        }

        private void initializeComboBox() {
            ObservableList<String> options = FXCollections.observableArrayList();

            options.add("----- IMPROVEMENTS -----");

            for (Improvement i : ((BuilderUnit) unit).getPossibleImprovements()) {
                options.add(i.toString());
            }

            comboBox = new ComboBox(options);
            comboBox.setMaxWidth(160);
            comboBox.setTranslateX(2);
            comboBox.setTranslateY(55);
            comboBox.setPromptText("--- Please Select ---");
            comboBox.setVisibleRowCount(8);

        }
    }
}
