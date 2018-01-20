
package civilizationclone.GUI;

import civilizationclone.City;
import civilizationclone.CityProject;
import civilizationclone.Tile.Improvement;
import static civilizationclone.Tile.Improvement.NONE;
import civilizationclone.Unit.BuilderUnit;
import civilizationclone.Unit.CalvaryUnit;
import civilizationclone.Unit.MeleeUnit;
import civilizationclone.Unit.MilitaryUnit;
import civilizationclone.Unit.RangeUnit;
import civilizationclone.Unit.ReconUnit;
import civilizationclone.Unit.ScoutUnit;
import civilizationclone.Unit.SettlerUnit;
import civilizationclone.Unit.SiegeUnit;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.UnitType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UnitMenu extends Pane{
    
    Unit unit;
    UnitOption[] opts = new UnitOption[4];
    UnitOption opt1;
    UnitOption opt2, opt3, opt4;
    ZoomMap zmapRef;
    Canvas infoDisplay;
    GraphicsContext gc;
    Text statusText;
    
    Text opt1T, opt2T, opt3T, opt4T;
    Text[] optT = new Text[4];
    
    //TODO fix the menu options, right now the code is sloppy
    public UnitMenu(Unit unit, ZoomMap zmapRef) {
        this.unit = unit;
        optT = new Text[4];
        opts = new UnitOption[4];
        
        if(unit instanceof SettlerUnit){
            opts[0] = new UnitOption(65, -60, 250, 40, "Move");
            opts[1] = new UnitOption(120, 0, 250, 40, "Move");
            opts[2] = new UnitOption(120, 60, 250, 40, "Settle");
            opts[3] = new UnitOption(65, 120, 250, 40, "Kill");
        }else if(unit instanceof BuilderUnit){
            opts[0] = new UnitOption(65, -60, 250, 40, "Move");
            opts[1] = new UnitOption(120, 0, 250, 40, "Improve");
            opts[2] = new UnitOption(120, 60, 250, 40, "Destroy");
            opts[3] = new UnitOption(65, 120, 250, 40, "Kill");
        }else if(unit instanceof MilitaryUnit){
            opts[0] = new UnitOption(65, -60, 250, 40, "Move");
            opts[1] = new UnitOption(120, 0, 250, 40, "Attack");
            opts[2] = new UnitOption(120, 60, 250, 40, "Heal");
            opts[3] = new UnitOption(65, 120, 250, 40, "Kill");
        }else if(unit instanceof ReconUnit){
            opts[0] = new UnitOption(65, -60, 250, 40, "Move");
            opts[1] = new UnitOption(120, 0, 250, 40, "Move");
            opts[2] = new UnitOption(120, 60, 250, 40, "Heal");
            opts[3] = new UnitOption(65, 120, 250, 40, "Kill");
        }
        
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
        
        gc = infoDisplay.getGraphicsContext2D();
        gc.setFill(Color.AQUA);
        gc.fillPolygon(new double[]{0, 230, 230, 175, 175, 230, 230, 0}, new double[]{0, 0, 40, 75, 155, 180, 220, 220}, 8);
        gc.setFill(Color.DARKSLATEGREY);
        gc.fillPolygon(new double[]{5, 225, 225, 170, 170, 225, 225, 5}, new double[]{5, 5, 35, 70, 160, 185, 215, 215}, 8);
        
        System.out.println(unit.getClass().getSimpleName());
        
        for(UnitOption o:opts){
            o.setFill(Color.DARKSLATEGREY);
        }
        
        for(UnitOption o:opts){
            if(o.getOptionType().equals("Move")){
                if(unit.getMovement() > 0){
                    o.getText().setFill(Color.WHITE);
                }else{
                    o.getText().setFill(Color.DARKGREY);
                }
            }else if(o.getOptionType().equals("Attack")){
                if(((MilitaryUnit)unit).getAttackable().length > 0 && unit.getMovement() > 0){
                    o.getText().setFill(Color.WHITE);
                }else{
                    o.getText().setFill(Color.DARKGREY);
                }
            }else if(o.getOptionType().equals("Settle")){
                if(unit.getMovement() > 0){
                    o.getText().setFill(Color.WHITE);
                }else{
                    o.getText().setFill(Color.DARKGREY);
                }
            }else if(o.getOptionType().equals("Heal")){
                if(unit.getMovement() > 0){
                    o.getText().setFill(Color.WHITE);
                }else{
                    o.getText().setFill(Color.DARKGREY);
                }
            }else if(o.getOptionType().equals("Kill")){
                o.getText().setFill(Color.WHITE);
            }else if(o.getOptionType().equals("Improve")){
                if(((BuilderUnit)unit).getPossibleImprovements().length > 1 && 
                        ((BuilderUnit)unit).getMapRef().getTile(unit.getX(), unit.getY()).getImprovement() == NONE && 
                        unit.getMovement() > 0 &&
                        ((BuilderUnit)unit).getMapRef().getTile(unit.getX(), unit.getY()).getControllingCity() != (null) &&
                        ((BuilderUnit)unit).getMapRef().getTile(unit.getX(), unit.getY()).getControllingCity().getPlayer().equals(unit.getPlayer())){
                    o.getText().setFill(Color.WHITE);
                }else{
                    o.getText().setFill(Color.DARKGREY);
                }
                    
            }else if(o.getOptionType().equals("Destroy")){
                o.getText().setFill(Color.DARKGRAY);
            }
        }
        
        this.zmapRef = zmapRef;
        
        
        this.getChildren().add(infoDisplay);
        this.getChildren().add(statusText);
        for(UnitOption o:opts){
            getChildren().add(o);
            getChildren().add(o.getText());
        }
        this.setVisible(true);
        
        setOnMouseClicked((MouseEvent event) -> {
            clickEventHandling(event);
        });
    }
    
    
    private void clickEventHandling(MouseEvent e) {
        //System.out.println(e.getTarget());
        if(e.getTarget() instanceof UnitOption){
            System.out.println(((UnitOption)e.getTarget()).getOptionType());
            
            if(((UnitOption)e.getTarget()).getOptionType().equals("Move") && ((UnitOption)e.getTarget()).getText().getFill() == Color.WHITE){
                if(unit.getMovement() > 0){
                    zmapRef.activateMove();
                }
            }else if(((UnitOption)e.getTarget()).getOptionType().equals("Attack") && ((UnitOption)e.getTarget()).getText().getFill() == Color.WHITE){
                if(((MilitaryUnit)unit).getAttackable().length > 0 && unit.getMovement() > 0){
                    zmapRef.activateAttack();
                }
            }else if(((UnitOption)e.getTarget()).getOptionType().equals("Settle") && ((UnitOption)e.getTarget()).getText().getFill() == Color.WHITE){
                if(unit.getMovement() > 0){
                    zmapRef.activateSettle();
                }
            }else if(((UnitOption)e.getTarget()).getOptionType().equals("Heal") && ((UnitOption)e.getTarget()).getText().getFill() == Color.WHITE){
                if(unit.getMovement() > 0){
                    zmapRef.activateHeal();
                }
            }else if(((UnitOption)e.getTarget()).getOptionType().equals("Kill") && ((UnitOption)e.getTarget()).getText().getFill() == Color.WHITE){
                zmapRef.activateKill();
            }else if(((UnitOption)e.getTarget()).getOptionType().equals("Improve") && ((UnitOption)e.getTarget()).getText().getFill() == Color.WHITE){
                if(((BuilderUnit)unit).getPossibleImprovements().length > 1 && ((BuilderUnit)unit).getMapRef().getTile(unit.getX(), unit.getY()).getImprovement() == NONE && unit.getMovement() > 0){
                    getChildren().add(new BuildMenu(unit));
                    return;
                }
            }else if(((UnitOption)e.getTarget()).getOptionType().equals("Destroy") && ((UnitOption)e.getTarget()).getText().getFill() == Color.WHITE){
                //zmapRef.activateDestroy();
            }
        }
    delete();
    
    }
    
    private String displayUnitInfo() {
        String msg = "";

        msg = msg + unit.getPlayer().getName() + "'s " + unit.getClass().getSimpleName().substring(0, unit.getClass().getSimpleName().length()-4) + "\n\n";
        
        
        msg = msg + "Movement: " + unit.getMovement() + "/" + unit.getMAX_MOVEMENT() + "\n";
        if(unit instanceof MilitaryUnit){
            msg = msg + "Health: " + ((MilitaryUnit) unit).getHealth() + " / " +((MilitaryUnit) unit).getMAX_HEALTH() + "\n";
        }else{
            msg = msg + "Health: 1/1 \n";
        }
        if(unit instanceof BuilderUnit){
            msg = msg + "Builds: " + ((BuilderUnit) unit).getActions();
        }
              

        return msg;
    }
    
    void delete(){
        zmapRef.getChildren().remove(this);
    }
    
    private class UnitOption extends Rectangle{
        int x,y,w,h;
        //Rectangle rect;
        String optionType;
        Text text;

        public UnitOption(int x, int y, int w, int h, String optionType) {
            super(x,y,w,h);

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

            confirmButton = new Circle(80, 180, 22);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOpacity(0.1);
            confirmButton.setOnMouseClicked((e) -> {
                e.consume();
                if (canConfirm) {
                    confirm();
                }

            });

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

            //to be changed later
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

            String selection = (String) comboBox.getValue();
            System.out.println(selection);
            canConfirm = true;
            confirmButton.setOpacity(1);


            //Determine what exactly did the person click
            for (Improvement i : ((BuilderUnit)unit).getPossibleImprovements()){
                    display.setImage(ImageBuffer.getImage(i));
                    info.setVisible(true);
                    display.setVisible(true);
                    return;
                }
            }
    
        

        private void confirm() {

            String selection = (String) comboBox.getValue();

            //Determine what exactly did the person click
            for (Improvement i : ((BuilderUnit)unit).getPossibleImprovements()) {
                if (i.name().equals(selection)) {

                    ((BuilderUnit)unit).improve(i);

                    close();
                }
            }

            close();
        }

        private void close() {
            if (getParent() instanceof UnitMenu) {
                ((UnitMenu) getParent()).delete();
            }
            zmapRef.repaint();
        }

        private void initializeComboBox() {
            ObservableList<String> options = FXCollections.observableArrayList();

            options.add("----- IMPROVEMENTS -----");

            for (Improvement i : ((BuilderUnit)unit).getPossibleImprovements()) {
                options.add(i.name());
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
