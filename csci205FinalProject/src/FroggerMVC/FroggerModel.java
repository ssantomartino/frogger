/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2016
*
* Name: Sam Santomartino, Grace Cook, and John O'Brien
* Date: Nov 3, 2016
* Time: 2:54:17 PM
*
* Project: csci205FinalProject
* Package: FroggerMVC
* File: FroggerModel
* Description:
*
* ****************************************
 */
package FroggerMVC;

import FroggerObjects.CarPath;

/**
 *
 * @author sms063
 */
public class FroggerModel {

    private static final String[] carFilesL = {"Car.png", "Police.png", "taxi.png"};
    private static final String[] carFilesR = {"Audi.png", "Black_viper.png", "Mini_truck.png"};
//    private static final String[] carFiles = {"blackCar.png"};

    public CarPath[] generateCarPaths() {

        int numRoads = 5;
        CarPath[] paths = new CarPath[numRoads];

        for (int i = 0; i < numRoads; i++) {
            int startX;
            int startY;
            int endX;

            if (i % 2 == 1) {
                startX = -50;
                startY = 637 - i * 50;
                endX = 750;
                paths[i] = new CarPath(startX, startY, endX, true);
            } else {
                startX = 750;
                startY = 637 - i * 50;
                endX = -50;
                paths[i] = new CarPath(startX, startY, endX, false);
            }

        }
        return paths;
    }

}
