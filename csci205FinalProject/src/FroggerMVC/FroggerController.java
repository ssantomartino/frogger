/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2016
*
* Name: Sam Santomartino, Grace Cook, and John O'Brien
* Date: Nov 2, 2016
* Time: 5:19:44 PM
*
* Project: csci205FinalProject
* Package:
* File: FroggerController
* Description:
*
* ****************************************
 */
package FroggerMVC;

import FroggerObjects.Car;
import FroggerObjects.CarPath;
import FroggerObjects.HighScores;
import FroggerObjects.WaterObject;
import FroggerObjects.WaterObjectPath;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;

/**
 *
 * @author jeo008
 */
class FroggerController {

    private FroggerView theView;
    private FroggerModel theModel;

    private MoveCarsTask theMoveCarTask;
    private MoveWaterObjectsTask theMoveWaterObjectsTask;

    private CheckCollisionsTask theCollisionsTask;
    private CheckDrowningTask theDrowningTask;

    private int numLives;
    private int score;
    private int maxScore;

    //private static ArrayList<Integer> highScores = new ArrayList<Integer>();
    private static final int MAX_NUM_SCORES = 10;

    private HighScores highScores;

    private static final double STEP_SIZE = 25;

    FroggerController(FroggerView theView, FroggerModel theModel) {
        this.theView = theView;
        this.theModel = theModel;

        this.theMoveCarTask = null;
        this.theCollisionsTask = null;
        this.theDrowningTask = null;

        this.numLives = FroggerView.getNUM_LIVES();
        this.maxScore = 0;
        this.score = 0;
        this.highScores = new HighScores();
    }

    public int getNumLives() {
        return this.numLives;
    }

    public void updateFrogUpPosition() {
        this.theView.getTheFrog().setTranslateY(
                this.theView.getTheFrog().getTranslateY() - STEP_SIZE);
        this.theView.getTheFrog().setRotate(0);
        this.adjustScore(1);
    }

    public void updateFrogDownPosition() {
        this.theView.getTheFrog().setTranslateY(
                this.theView.getTheFrog().getTranslateY() + STEP_SIZE);
        this.theView.getTheFrog().setRotate(180);
        this.adjustScore(-1);
    }

    public void updateFrogRightPosition() {
        this.theView.getTheFrog().setTranslateX(
                this.theView.getTheFrog().getTranslateX() + STEP_SIZE);

        this.theView.getTheFrog().setRotate(90);
    }

    public void updateFrogLeftPosition() {
        this.theView.getTheFrog().setTranslateX(
                this.theView.getTheFrog().getTranslateX() - STEP_SIZE);
        this.theView.getTheFrog().setRotate(270);
    }

    public boolean checkBottomBound() {
        int yMax = theView.getRootYMax();
        Bounds frogBounds = theView.getTheFrog().localToScene(
                theView.getTheFrog().getBoundsInLocal());
        int frogYMax = (int) frogBounds.getMaxY();
        //System.out.println(frogYMax);
        if (yMax - frogYMax < 10) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkTopBound() {
        int yMin = theView.getRootYMin();
        Bounds frogBounds = theView.getTheFrog().localToScene(
                theView.getTheFrog().getBoundsInLocal());
        int frogYMin = (int) frogBounds.getMinY();
        //System.out.println(frogYMin);
        if (frogYMin - yMin < 10) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkRightBound() {
        int xMax = theView.getRootXMax();
        Bounds frogBounds = theView.getTheFrog().localToScene(
                theView.getTheFrog().getBoundsInLocal());
        int frogXMax = (int) frogBounds.getMaxX();
        //System.out.println(xMax + "," + frogXMax);
        if (xMax - frogXMax < 10) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkLeftBound() {
        int xMin = theView.getRootXMin();
        Bounds frogBounds = theView.getTheFrog().localToScene(
                theView.getTheFrog().getBoundsInLocal());
        int frogXMin = (int) frogBounds.getMinX();
        //System.out.println(frogXMin);
        if (frogXMin - xMin < 10) {
            return false;
        } else {
            return true;
        }
    }

    public void startTheCars() {
        CarPath[] theRoad = this.theView.getTheRoad();
        for (CarPath path : theRoad) {
            Car[] cars = path.getTheCars();
            this.theMoveCarTask = new MoveCarsTask(cars);
            Thread th = new Thread(theMoveCarTask);
            th.setDaemon(true);
            th.start();
        }
    }

    public void startTheWaterObjects() {
        WaterObjectPath[] theRiver = this.theView.getTheRiver();
        for (WaterObjectPath path : theRiver) {
            WaterObject[] waterObjects = path.getTheObjects();
            this.theMoveWaterObjectsTask = new MoveWaterObjectsTask(waterObjects);
            Thread th = new Thread(theMoveWaterObjectsTask);
            th.setDaemon(true);
            th.start();
        }
    }

    public void checkCarCollisions() {
        CarPath[] theRoad = this.theView.getTheRoad();
        for (CarPath path : theRoad) {
            Car[] cars = path.getTheCars();
            this.theCollisionsTask = new CheckCollisionsTask(cars);
            Thread th = new Thread(theCollisionsTask);
            th.setDaemon(true);
            th.start();
        }
    }

    public void checkFellInWater() {
        WaterObjectPath[] theRiver = this.theView.getTheRiver();
        for (WaterObjectPath path : theRiver) {
            this.theDrowningTask = new CheckDrowningTask(path);
            Thread th = new Thread(this.theDrowningTask);
            th.setDaemon(true);
            th.start();

        }
    }

    public void removeLife() {
        this.numLives--;
    }

    public void endGame() {
        System.out.println("Final Score: " + this.score);
        ArrayList<Integer> theScores = this.highScores.insertScore(this.score);
        this.highScores.saveScores();
        this.theView.endGame(this.score, theScores);

    }

    private void adjustScore(int i) {
        this.score += i * 10;
        if (this.score > this.maxScore) {
            this.maxScore = this.score;
            theView.updateScore(maxScore);
        }
    }

    public int getMaxScore() {
        return maxScore;
    }

    class MoveWaterObjectsTask extends Task<Integer> {

        private final WaterObject[] waterObjects;

        public MoveWaterObjectsTask(WaterObject[] waterObjects) {
            this.waterObjects = waterObjects;
        }

        @Override
        protected Integer call() throws Exception {
            for (WaterObject waterObject : this.waterObjects) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        waterObject.moveWaterObject();
                    }
                });

                Thread.sleep(3000);
            }

            return 1;
        }
    }

    class MoveCarsTask extends Task<Integer> {

        private final Car[] cars;

        /**
         * Construct the task with the model and cars to run through
         */
        public MoveCarsTask(Car[] cars) {
            this.cars = cars;
        }

        @Override
        protected Integer call() throws Exception {
            for (Car car : this.cars) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        car.moveCar();

                    }
                });

                Thread.sleep(3000);
            }

            return 1;
        }

    }

    class CheckCollisionsTask extends Task<Integer> {

        private final Car[] cars;

        /**
         * Construct the task with the model and cars to run through
         */
        public CheckCollisionsTask(Car[] cars) {
            this.cars = cars;
        }

        @Override
        protected Integer call() throws Exception {
            Bounds frogBounds = FroggerController.this.theView.getTheFrog().getBoundsInParent();
            for (Car car : this.cars) {
                if (car.getBoundsInParent().intersects(frogBounds)) {
                    //FroggerController.this.numLives--;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            FroggerController.this.theView.getTheFrog().restartFrog();
                            FroggerController.this.theView.removeNextLife();
                            FroggerController.this.removeLife();
                            if (FroggerController.this.numLives <= 0) {
                                FroggerController.this.endGame();
                            }
                        }
                    });
                }
                Thread.sleep(1);
            }
            return 1;
        }

    }

    class CheckDrowningTask extends Task<Integer> {

        private final WaterObjectPath waterPath;

        /**
         * Construct the task with the model and the water path to check for
         * collisions
         */
        public CheckDrowningTask(WaterObjectPath path) {
            this.waterPath = path;
        }

        @Override
        protected Integer call() throws Exception {

            Bounds frogBoundsParent = FroggerController.this.theView.getTheFrog().getBoundsInParent();

            boolean isOnALog = false;
            WaterObject[] waterObjects = this.waterPath.getTheObjects();
            for (WaterObject waterObject : waterObjects) {
                if (waterObject.getBoundsInParent().intersects(frogBoundsParent)) {
                    System.out.println("intersecting log/turtle");
                    return 1;

                }

                //Thread.sleep(1);
            }
            if ((!isOnALog) && this.waterPath.getTheRiver().getBoundsInParent().intersects(
                    frogBoundsParent)) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FroggerController.this.theView.getTheFrog().restartFrog();
                        FroggerController.this.theView.removeNextLife();
                        FroggerController.this.removeLife();
                        if (FroggerController.this.numLives <= 0) {
                            FroggerController.this.endGame();
                        }
                    }
                });

                Thread.sleep(1);
            }

            return 1;
        }

    }

}
