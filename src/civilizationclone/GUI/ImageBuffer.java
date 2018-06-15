package civilizationclone.GUI;

import civilizationclone.CityProject;
import civilizationclone.Leader;
import static civilizationclone.Leader.ZEDONG;
import civilizationclone.TechType;
import civilizationclone.Tile.*;
import civilizationclone.Unit.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.Image;

enum MiscAsset {
    CLOSE_ICON, CONFIRM_ICON, CITY_BACKGROUND, CITY_OPTION_BACKGROUND, WARNING, CITIZEN_ICON, MONEY_ICON, PRODUCTION_ICON, CLOUD, GOLD, SCIENCE, HAPPY, EXPANSION_ICON, GOLD_ICON, CITY, TITLE_BACKGROUND, HIGHLIGHT_TOGGLE, RESOURCE_TOGGLE, YIELDS_TOGGLE, READY_ICON, WAITING_ICON, CHAT_ICON, SEND_ICON;
}

public class ImageBuffer {

    //A class the loads all the resources that the game needs so that it can be saved in memory and called efficiently
    //IMAGE VARS ----------------------------
    //<editor-fold>
    //TILES
    private static Image desert;
    private static Image hills;
    private static Image mountain;
    private static Image ocean;
    private static Image plains;
    private static Image city;

    //UNITS
    private static Image builder;
    private static Image settler;
    private static Image scout;
    private static Image warrior;
    private static Image swordsman;
    private static Image musketman;
    private static Image infantry;
    private static Image slinger;
    private static Image archer;
    private static Image crossbowman;
    private static Image fieldCannon;
    private static Image horseman;
    private static Image knight;
    private static Image mountedForce;
    private static Image catapult;
    private static Image bombard;
    private static Image galley;
    private static Image quadrireme;
    private static Image caravel;
    private static Image ironclad;
    private static Image destroyer;
    private static Image embark;

    //CITY PROJECTS
    private static Image granary;
    private static Image library;
    private static Image lighthouse;
    private static Image stable;
    private static Image barracks;
    private static Image market;
    private static Image workshop;
    private static Image university;
    private static Image bank;
    private static Image shipyard;
    private static Image factory;
    private static Image hospital;
    private static Image stockexchange;
    private static Image ancientwall;
    private static Image medievalwall;
    private static Image renaissancewall;

    //MISC
    private static Image closeIcon;
    private static Image confirmIcon;
    private static Image cityBackground;
    private static Image cityOptionBackground;
    private static Image warning;
    private static Image citizenIcon;
    private static Image productionIcon;
    private static Image goldIcon;
    private static Image expansionIcon;
    private static Image cloud;
    private static Image titleBackground;
    private static Image readyIcon;
    private static Image waitingIcon;
    private static Image chatIcon;
    private static Image sendIcon;
    //IMPROVEMENT
    public static Image farm;
    private static Image mine;
    private static Image fishing;
    private static Image ranch;
    private static Image plantation;
    private static Image academy;
    private static Image quarry;
    private static Image oilwell;

    //ICONS
    private static Image gold;
    private static Image science;
    private static Image happy;

    //RESOURCE
    private static Image wheat;
    private static Image rice;
    private static Image cotton;
    private static Image banana;
    private static Image wine;
    private static Image iron;
    private static Image stone;
    private static Image mercury;
    private static Image cattle;
    private static Image sheep;
    private static Image truffle;
    private static Image fish;
    private static Image crab;
    private static Image whale;
    private static Image oil;

    private static Image teddy;
    private static Image hitler;
    private static Image stalin;
    private static Image musolini;
    private static Image mao;
    private static Image churchill;

    //TECHTYPES
    private static Image defaultTech;
    private static Image pottery;

    //YIELD ICONS
    private static Image gold1;
    private static Image gold2;
    private static Image gold3;
    private static Image gold4;
    private static Image gold5;
    private static Image food1;
    private static Image food2;
    private static Image food3;
    private static Image food4;
    private static Image food5;
    private static Image production1;
    private static Image production2;
    private static Image production3;
    private static Image production4;
    private static Image production5;
    private static Image science1;
    private static Image science2;
    private static Image science3;
    private static Image science4;
    private static Image science5;

    //TOGGLES
    private static Image highlightToggle;
    private static Image resourceToggle;
    private static Image yieldsToggle;

    //</editor-fold>
    //Image loading
    static {
        desert = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tiles/Desert.png"), 100, 110, false, false);
        hills = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tiles/Hills.png"), 100, 110, false, false);
        mountain = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tiles/Mountain.png"), 100, 110, false, false);
        ocean = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tiles/Ocean.png"), 100, 110, false, false);
        plains = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tiles/Plains.png"), 100, 110, false, false);
        city = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tiles/City.png"), 90, 90, false, false);
        //UNIT
        builder = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/builder(new).png"), 70, 70, false, false);
        settler = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Settler.png"), 70, 70, false, false);
        scout = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Scout.png"), 70, 70, false, false);
        warrior = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Warrior.png"), 70, 70, false, false);
        swordsman = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Swordsman.png"), 70, 70, false, false);
        musketman = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Musketman.png"), 70, 70, false, false);
        infantry = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Infantry.png"), 70, 70, false, false);
        slinger = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Slinger.png"), 70, 70, false, false);
        archer = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Archer.png"), 70, 70, false, false);
        crossbowman = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Crossbowman.png"), 70, 70, false, false);
        fieldCannon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Fieldcannon.png"), 70, 70, false, false);
        horseman = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Horseman.png"), 70, 70, false, false);
        knight = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Knight.png"), 70, 70, false, false);
        mountedForce = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/mountedForce.png"), 70, 70, false, false);
        catapult = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Catapult.png"), 70, 70, false, false);
        bombard = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Bombard.png"), 70, 70, false, false);
        galley = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Galley.png"), 70, 70, false, false);
        quadrireme = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Quadrireme.png"), 70, 70, false, false);
        caravel = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Caravel.png"), 70, 70, false, false);
        ironclad = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Ironclad.png"), 70, 70, false, false);
        embark = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/embark.png"), 70, 70, false, false);
        //MISC
        closeIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/close_button.png"), 60, 60, false, false);
        confirmIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/confirm_icon.png"), 60, 60, true, false);
        cityBackground = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/city_background.jpg"), 1080, 1920, false, false);
        cityOptionBackground = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/city_option_background.jpg"), 1280, 800, false, false);
        warning = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/warning.png"), 70, 70, false, false);
        citizenIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/citizen_icon.png"), 120, 120, false, false);
        productionIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/production_icon.png"), 120, 120, false, false);
        goldIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/gold_icon.png"), 120, 120, false, false);
        cloud = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/cloud.jpg"), 225, 225, false, false);
        expansionIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/expand_icon.png"), 75, 75, false, false);
        titleBackground = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/title_background.png"), 2560, 1440, true, false);
        gold = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/gold.png"), 25, 25, false, false);
        science = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/science.png"), 25, 25, false, false);
        happy = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/happy.png"), 25, 25, false, false);
        readyIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/ready.png"), 50, 50, false, false);
        waitingIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/loading.png"), 50, 50, false, false);
        chatIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/chat_icon.png"), 50, 50, false, false);
        sendIcon = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/send_icon.png"), 50, 50, false, false);
        //IMPROVEMENT
        farm = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/farm.png"), 70, 70, false, false);
        mine = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/mine.png"), 70, 70, false, false);
        plantation = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/plantation.png"), 70, 70, false, false);
        fishing = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/fishing.png"), 70, 70, false, false);
        ranch = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/ranch.png"), 70, 70, false, false);
        academy = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/academy.png"), 70, 70, false, false);
        oilwell = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/oilwell.png"), 70, 70, false, false);
        quarry = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Improvement/quarry.png"), 70, 70, false, false);
        //BUILDINGS
        granary = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/granary.png"), 70, 70, false, false);
        library = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/library.png"), 70, 70, false, false);
        lighthouse = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/lighthouse.png"), 70, 70, false, false);
        stable = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/stable.png"), 70, 70, false, false);
        barracks = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/barracks.png"), 70, 70, false, false);
        market = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/market.png"), 70, 70, false, false);
        workshop = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/workshop.png"), 70, 70, false, false);
        university = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/university.png"), 70, 70, false, false);
        bank = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/bank.png"), 70, 70, false, false);
        shipyard = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/shipyard.png"), 70, 70, false, false);
        factory = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/factory.png"), 70, 70, false, false);
        hospital = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/hospital.png"), 70, 70, false, false);
        stockexchange = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/stockexchange.png"), 70, 70, false, false);
        ancientwall = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/ancient_wall.png"), 70, 70, false, false);
        medievalwall = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/medieval_wall.png"), 70, 70, false, false);
        renaissancewall = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/CityProject/renaissance_wall.png"), 70, 70, false, false);
        //RESOURCE
        wheat = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/wheat.png"), 45, 45, false, false);
        banana = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/banana.png"), 45, 45, false, false);
        cattle = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/cattle.png"), 45, 45, false, false);
        crab = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/crab.png"), 45, 45, false, false);
        fish = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/fish.png"), 45, 45, false, false);
        iron = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/iron.png"), 45, 45, false, false);
        mercury = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/mercury.png"), 45, 45, false, false);
        rice = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/rice.png"), 45, 45, false, false);
        sheep = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/sheep.png"), 45, 45, false, false);
        stone = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/stone.png"), 45, 45, false, false);
        truffle = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/truffle.png"), 45, 45, false, false);
        whale = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/whale.png"), 45, 45, false, false);
        wine = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/wine.png"), 45, 45, false, false);
        cotton = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/cotton.png"), 45, 45, false, false);
        oil = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Resource/oil.png"), 45, 45, false, false);
        //TECH
        defaultTech = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tech/default.png"), 100, 100, false, false);
        pottery = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tech/pottery.png"), 100, 100, false, false);
        stalin = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Players/stalin.png"), 60, 60, false, false);
        hitler = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Players/hitler.png"), 60, 60, false, false);
        mao = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Players/mao.png"), 60, 60, false, false);
        teddy = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Players/teddy.png"), 60, 60, false, false);
        musolini = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Players/musolini.png"), 60, 60, false, false);
        churchill = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Players/churchill.png"), 60, 60, false, false);
        destroyer = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Units/Destroyer.png"), 70, 70, false, false);
        //YIELD ICON
        gold1 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/gold_1.png"), 45, 45, false, false);
        gold2 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/gold_2.png"), 45, 45, false, false);
        gold3 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/gold_3.png"), 45, 45, false, false);
        gold4 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/gold_4.png"), 45, 45, false, false);
        gold5 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/gold_5.png"), 45, 45, false, false);
        food1 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/food_1.png"), 45, 45, false, false);
        food2 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/food_2.png"), 45, 45, false, false);
        food3 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/food_3.png"), 45, 45, false, false);
        food4 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/food_4.png"), 45, 45, false, false);
        food5 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/food_5.png"), 45, 45, false, false);
        production1 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/production_1.png"), 45, 45, false, false);
        production2 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/production_2.png"), 45, 45, false, false);
        production3 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/production_3.png"), 45, 45, false, false);
        production4 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/production_4.png"), 45, 45, false, false);
        production5 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/production_5.png"), 45, 45, false, false);
        science1 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/science_1.png"), 45, 45, false, false);
        science2 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/science_2.png"), 45, 45, false, false);
        science3 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/science_3.png"), 45, 45, false, false);
        science4 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/science_4.png"), 45, 45, false, false);
        science5 = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Yields/science_5.png"), 45, 45, false, false);
        //TOGGLE
        highlightToggle = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/highlightToggle.png"), 120, 120, false, false);
        resourceToggle = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/resourceToggle.png"), 120, 120, false, false);
        yieldsToggle = new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Misc/yieldsToggle.png"), 120, 120, false, false);
    }

    public static Image getImage(Unit unit) {

        if (unit.hasEmbarked()) {
            return embark;
        }

        if (unit instanceof BuilderUnit) {
            return builder;
        } else if (unit instanceof ScoutUnit) {
            return scout;
        } else if (unit instanceof SettlerUnit) {
            return settler;
        } else if (unit instanceof WarriorUnit) {
            return warrior;
        } else if (unit instanceof SwordsmanUnit) {
            return swordsman;
        } else if (unit instanceof MusketmanUnit) {
            return musketman;
        } else if (unit instanceof InfantryUnit) {
            return infantry;
        } else if (unit instanceof SlingerUnit) {
            return slinger;
        } else if (unit instanceof ArcherUnit) {
            return archer;
        } else if (unit instanceof CrossbowmanUnit) {
            return crossbowman;
        } else if (unit instanceof FieldCannonUnit) {
            return fieldCannon;
        } else if (unit instanceof HorsemanUnit) {
            return horseman;
        } else if (unit instanceof KnightUnit) {
            return knight;
        } else if (unit instanceof MountedForceUnit) {
            return mountedForce;
        } else if (unit instanceof CatapultUnit) {
            return catapult;
        } else if (unit instanceof BombardUnit) {
            return bombard;
        } else if (unit instanceof GalleyUnit) {
            return galley;
        } else if (unit instanceof QuadriremeUnit) {
            return quadrireme;
        } else if (unit instanceof CaravelUnit) {
            return caravel;
        } else if (unit instanceof IroncladUnit) {
            return ironclad;
        }

        return destroyer;
    }

    public static Image getImage(UnitType unit) {
        switch (unit) {
            case BUILDER:
                return builder;
            case SCOUT:
                return scout;
            case SETTLER:
                return settler;
            case WARRIOR:
                return warrior;
            case SWORDSMAN:
                return swordsman;
            case MUSKETMAN:
                return musketman;
            case INFANTRY:
                return infantry;
            case SLINGER:
                return slinger;
            case ARCHER:
                return archer;
            case CROSSBOWMAN:
                return crossbowman;
            case FIELDCANNON:
                return fieldCannon;
            case HORSEMAN:
                return horseman;
            case KNIGHT:
                return knight;
            case MOUNTEDFORCE:
                return mountedForce;
            case CATAPULT:
                return catapult;
            case BOMBARD:
                return bombard;
            case GALLEY:
                return galley;
            case QUADRIREME:
                return quadrireme;
            case CARAVEL:
                return caravel;
            case IRONCLAD:
                return ironclad;
        }

        return destroyer;
    }

    public static Image getImage(TechType t) {

        //Loading tech is not often hence this is fine
        if (t != TechType.NONE) {
            return (new Image(ImageBuffer.class.getClassLoader().getResourceAsStream("Assets/Tech/" + t.name().toLowerCase() + ".png"), 100, 100, false, false));
        }

        return defaultTech;

    }

    public static Image getImage(Improvement i) {
        switch (i) {
            case FARM:
                return farm;
            case MINE:
                return mine;
            case RANCH:
                return ranch;
            case ACADEMY:
                return academy;
            case FISHING:
                return fishing;
            case PLANTATION:
                return plantation;
            case OILWELL:
                return oilwell;
            case QUARRY:
                return quarry;
        }

        return destroyer;
    }

    public static Image getImage(CityProject c) {

        switch (c) {
            case GRANARY:
                return granary;
            case LIBRARY:
                return library;
            case LIGHTHOUSE:
                return lighthouse;
            case STABLE:
                return stable;
            case BARRACKS:
                return barracks;
            case MARKET:
                return market;
            case WORKSHOP:
                return workshop;
            case UNIVERSITY:
                return university;
            case BANK:
                return bank;
            case SHIPYARD:
                return shipyard;
            case FACTORY:
                return factory;
            case HOSPITAL:
                return hospital;
            case STOCKEXCHANGE:
                return stockexchange;
            case ANCIENTWALL:
                return ancientwall;
            case MEDIEVALWALL:
                return medievalwall;
            case RENAISSANCEWALL:
                return renaissancewall;
        }

        return destroyer;
    }

    public static Image getImage(MiscAsset m) {
        switch (m) {
            case CLOSE_ICON:
                return closeIcon;
            case CITY_BACKGROUND:
                return cityBackground;
            case CITY_OPTION_BACKGROUND:
                return cityOptionBackground;
            case WARNING:
                return warning;
            case GOLD_ICON:
                return goldIcon;
            case PRODUCTION_ICON:
                return productionIcon;
            case CITIZEN_ICON:
                return citizenIcon;
            case CONFIRM_ICON:
                return confirmIcon;
            case CLOUD:
                return cloud;
            case GOLD:
                return gold;
            case SCIENCE:
                return science;
            case HAPPY:
                return happy;
            case CITY:
                return city;
            case TITLE_BACKGROUND:
                return titleBackground;
            case HIGHLIGHT_TOGGLE:
                return highlightToggle;
            case RESOURCE_TOGGLE:
                return resourceToggle;
            case YIELDS_TOGGLE:
                return yieldsToggle;
            case EXPANSION_ICON:
                return expansionIcon;
            case WAITING_ICON:
                return waitingIcon;
            case READY_ICON:
                return readyIcon;
            case CHAT_ICON:
                return chatIcon;
            case SEND_ICON:
                return sendIcon;
        }

        return destroyer;
    }

    public static Image getCityImage() {
        return city;
    }

    public static Image getImage(Tile tile) {
        if (tile instanceof Ocean) {
            return ocean;
        } else if (tile instanceof Plains) {
            return plains;
        } else if (tile instanceof Hills) {
            return hills;
        } else if (tile instanceof Desert) {
            return desert;
        } else {
            return mountain;
        }

    }

    public static Image getImage(Resource r) {

        switch (r) {
            case WHEAT:
                return wheat;
            case RICE:
                return rice;
            case BANANA:
                return banana;
            case CATTLE:
                return cattle;
            case COTTON:
                return cotton;
            case CRAB:
                return crab;
            case FISH:
                return fish;
            case IRON:
                return iron;
            case MERCURY:
                return mercury;
            case SHEEP:
                return sheep;
            case STONE:
                return stone;
            case TRUFFLE:
                return truffle;
            case WHALE:
                return whale;
            case WINE:
                return wine;
            case OIL:
                return oil;

        }

        return destroyer;
    }

    public static Image getImage(Leader l) {
        switch (l) {
            case ZEDONG:
                return mao;
            case HITLER:
                return hitler;
            case ROOSEVELT:
                return teddy;
            case MUSSOLINI:
                return musolini;
            case CHURCHILL:
                return churchill;
            case STALIN:
                return stalin;
        }
        return destroyer;
    }

    public static ArrayList<Image> getYieldIcons(Tile t) {
        ArrayList<Image> images = new ArrayList<Image>();

        if (t.getFoodOutput() == 1) {
            images.add(food1);
        } else if (t.getFoodOutput() == 2) {
            images.add(food2);
        } else if (t.getFoodOutput() == 3) {
            images.add(food3);
        } else if (t.getFoodOutput() == 4) {
            images.add(food4);
        } else if (t.getFoodOutput() >= 5) {
            images.add(food5);
        }

        if (t.getGoldOutput() == 1) {
            images.add(gold1);
        } else if (t.getGoldOutput() == 2) {
            images.add(gold2);
        } else if (t.getGoldOutput() == 3) {
            images.add(gold3);
        } else if (t.getGoldOutput() == 4) {
            images.add(gold4);
        } else if (t.getGoldOutput() >= 5) {
            images.add(gold5);
        }

        if (t.getScienceOutput() == 1) {
            images.add(science1);
        } else if (t.getScienceOutput() == 2) {
            images.add(science2);
        } else if (t.getScienceOutput() == 3) {
            images.add(science3);
        } else if (t.getScienceOutput() == 4) {
            images.add(science4);
        } else if (t.getScienceOutput() >= 5) {
            images.add(science5);
        }

        if (t.getProductionOutput() == 1) {
            images.add(production1);
        } else if (t.getProductionOutput() == 2) {
            images.add(production2);
        } else if (t.getProductionOutput() == 3) {
            images.add(production3);
        } else if (t.getProductionOutput() == 4) {
            images.add(production4);
        } else if (t.getProductionOutput() >= 5) {
            images.add(production5);
        }

        return images;
    }

}
