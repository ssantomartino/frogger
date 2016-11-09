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

    private static final int STARTING_X_POS = 250;
    private static final int STARTING_Y_POS = 475;
    private double XLocation;
    private double YLocation;

    public Frog(double width, double height, String fileName, double XLocation,
                double YLocation) {

        super(fileName);
        this.XLocation = XLocation;
        this.YLocation = YLocation;
        setFitHeight(height);
        setFitWidth(width);
        setTranslateX(this.XLocation);
        setTranslateY(this.YLocation);

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

    public void restartFrog() {
        setTranslateX(STARTING_X_POS);
        setTranslateY(STARTING_Y_POS);
    }

}