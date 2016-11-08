/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2016
*
* Name: Sam Santomartino, Grace Cook, and John O'Brien
* Date: Nov 7, 2016
* Time: 10:10:47 AM
*
* Project: csci205FinalProject
* Package: FroggerObjects
* File: Car
* Description:
*
* ****************************************
 */
package FroggerObjects;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author sms063
 */
public class Car {

    private Rectangle theCar;
    private Path thePath;
    private PathTransition pathTransition;

    public Car(int startX, int startY, int endX) {
        this.theCar = new Rectangle(30, 15, Color.RED);

        this.thePath = new Path();
        this.thePath.getElements().add(new MoveTo(startX, startY));
        this.thePath.getElements().add(new HLineTo(endX));
        this.thePath.setOpacity(0.0);

        this.pathTransition = new PathTransition();
        pathTransition.setPath(thePath);
        pathTransition.setNode(theCar);

    }

    public void setDuration(int seconds) {
        pathTransition.setDuration(Duration.seconds(seconds));
    }

    public void setDelay(int seconds) {
        pathTransition.setDelay(Duration.seconds(seconds));
    }

    public void moveCar() {
        pathTransition.play();
    }

    public Rectangle getTheCar() {
        return theCar;
    }

    public Path getThePath() {
        return thePath;
    }

}