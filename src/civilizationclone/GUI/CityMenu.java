package civilizationclone.GUI;

import civilizationclone.City;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CityMenu extends Pane {

    City city;
    ZoomMap zoomMapRef;

    //graphics related members
    Rectangle border;
    Text statusText, cityNameText;
    Circle closeButton;
    Canvas productionDisplay;

    public CityMenu(City city, int resX, int resY, ZoomMap zoomMapRef) {

        this.city = city;
        this.zoomMapRef = zoomMapRef;

        border = new Rectangle(0, 0, 460, resY + 10);
        setTranslateX(resX - 450);
        ImagePattern cityBackground = new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_BACKGROUND), 0, 0, 1, 1, true);
        border.setFill(cityBackground);

        statusText = new Text(displayCityInfo());
        statusText.setFont(Font.font("Times New Roman", 15));
        statusText.setFill(Color.WHITE);
        statusText.setTranslateY(120);
        statusText.setTranslateX(25);

        cityNameText = new Text(city.getName());
        cityNameText.setFont(Font.font("Oswald", 50));
        cityNameText.setFill(Color.CORNSILK);
        cityNameText.setTranslateX(30);
        cityNameText.setTranslateY(60);

        closeButton = new Circle(420, 40, 30);
        closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_BUTTON)));
        closeButton.setOnMouseClicked((e) -> {
            e.consume();
            close();
        });

        productionDisplay = new Canvas(100, 100);
        productionDisplay.setTranslateX(280);
        productionDisplay.setTranslateY(130);
        updateProductionDisplay();

        getChildren().addAll(border, statusText, cityNameText, closeButton, productionDisplay);

    }

    private void updateProductionDisplay() {
        GraphicsContext gc = productionDisplay.getGraphicsContext2D();
        Image productionImage;

        if (city.getCurrentUnit() != null) {
            productionImage = ImageBuffer.getImage(city.getCurrentUnit());
        } else if (city.getCurrentProject() != null) {
            productionImage = ImageBuffer.getImage(city.getCurrentProject());
        } else {
            productionImage = ImageBuffer.getImage(MiscAsset.WARNING);
        }

        gc.drawImage(productionImage, 15, 15);
        
    }

    private String displayCityInfo() {
        String msg = "";

        msg = msg + "Population: " + city.getPopulation();
        msg = msg + "\nFood income: " + city.getFoodIncome();
        msg = msg + "\nTech income: " + city.getTechIncome();
        msg = msg + "\nGold income: " + city.getGoldIncome();

        if (city.getCurrentProject() != null) {
            msg = msg + "\n\n     Current Project: " + city.getCurrentProject().name();
            msg = msg + "\n    Progress: " + city.getCurrentProduction() + "/" + city.getCurrentProject().getProductionCost();
        } else if (city.getCurrentUnit() != null) {
            msg = msg + "\n\n     Current Project: " + city.getCurrentUnit().name();
            msg = msg + "\n     Progress: " + city.getCurrentProduction() + "/" + city.getCurrentUnit().getProductionCost();
        } else {
            msg = msg + "\n\n    Nothing is being produced!";
        }

        return msg;
    }

    private void close() {

        //should be able to start dragginag again after menu is closed
      //  zoomMapRef.enableDragging(true);

        if (this.getParent() instanceof GamePane) {
            ((GamePane) this.getParent()).removeCityMenu();
        }
    }

}
