/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2016
*
* Name: Sam Santomartino, Grace Cook, and John O'Brien
* Date: Nov 2, 2016
* Time: 5:19:31 PM
*
* Project: csci205FinalProject
* Package:
* File: FroggerView
* Description:
*
* ****************************************
 */
package FroggerMVC;

import FroggerObjects.Car;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;

/**
 *
 * @author jeo008
 */
class FroggerView {

    private FroggerModel theModel;
    private Pane root;
    private Frog theFrog;

    public FroggerView(FroggerModel theModel) {
        this.theModel = theModel;
        root = new Pane();

        root.setPrefWidth(500);
        root.setPrefHeight(500);
        root.setPadding(new Insets(15, 15, 15, 15));

        this.theFrog = new Frog(20, 20, "basicFrog.png", 250, 475);

        root.getChildren().add(theFrog);

        Car[] cars = theModel.generateCars();
        for (Car car : cars) {
            root.getChildren().add(car.getTheCar());
            root.getChildren().add(car.getThePath());
//            car.moveCar();
        }
    }

    public Pane getRootNode() {
        return root;
    }

    public int getRootXMin() {
        return (int) this.root.getBoundsInLocal().getMinX();
    }

    public int getRootXMax() {
        return (int) this.root.getBoundsInLocal().getMaxX();
    }

    public int getRootYMin() {
        return (int) this.root.getBoundsInLocal().getMinY();
    }

    public int getRootYMax() {
        return (int) this.root.getBoundsInLocal().getMaxY();
    }

    public Frog getTheFrog() {
        return theFrog;
    }

}
