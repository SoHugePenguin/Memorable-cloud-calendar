/**
 * @Project dingLaoZuoYe
 * @File ballObject
 * @Time 2022/12/28 11:12
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.Utils.objs;/*
 *dingLaoZuoYe -> ballObject
 *2022/12/28 11:12
 *ToDo:
 *
 * @Author: SoHugePenguin
 */

import java.awt.*;

public class ballObject {
    Image image;
    Color color;
    int startX;
    public int startY;
    int day;

    public ballObject(Image image, int startX, int startY, int day, Color color) {
        this.image = image;
        this.startX = startX;
        this.startY = startY;
        this.day = day;
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
