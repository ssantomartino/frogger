/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2016
*
* Name: Sam Santomartino, Grace Cook, and John O'Brien
* Date: Nov 4, 2016
* Time: 10:31:39 AM
*
* Project: csci205FinalProject
* Package: FroggerMVC
* File: Frog
* Description:
*
* ****************************************
 */
package FroggerObjects;

import javafx.scene.image.ImageView;

/**
 *
 * @author gmc017
 */
public class Frog extends ImageView {

    public static final int STARTING_X_POS = 350;
    public static final int STARTING_Y_POS = 675;

    private static final double width = 25;
    private static final double height = 25;

    private int currentLevel;
    private final int maxLevel = 5;

    private double XLocation;
    private double YLocation;

    private boolean isOnWaterObject;

    public Frog(String fileName, double XLocation,
                double YLocation) {

        super(fileName);
        this.XLocation = XLocation;
        this.YLocation = YLocation;
        setFitHeight(height);
        setFitWidth(width);
        setSmooth(true);
        setTranslateX(this.XLocation);
        setTranslateY(this.YLocation);

        this.isOnWaterObject = false;
        this.currentLevel = 1;

    }

    public double getXLocation() {
        return this.XLocation;
    }

    public double getYLocation() {
        return this.YLocation;
    }

    public void setXLocation(double XLocation) {
        this.XLocation = XLocation;
    }

    public void setYLocation(double YLocation) {
        this.YLocation = YLocation;
    }

    public void setXTranslation(double Xtranslate) {
        setTranslateX(Xtranslate);
    }

    public static double getWidth() {
        return width;
    }

    public static double getHeight() {
        return height;
    }

    public boolean getisOnWaterObject() {
        return this.isOnWaterObject;
    }

    public void setisOnWaterObjectTrue() {
        this.isOnWaterObject = true;
    }

    public void setisOnWaterObjectFalse() {
        this.isOnWaterObject = false;
    }

    public void restartFrog() {
        setTranslateX(STARTING_X_POS);
        setTranslateY(STARTING_Y_POS);
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void levelUp() {
        this.currentLevel++;
        restartFrog();
    }

}
