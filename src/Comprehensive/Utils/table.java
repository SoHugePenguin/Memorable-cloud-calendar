/**
 * @Project dingLaoZuoYe
 * @File table
 * @Time 2022/12/27 12:27
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.Utils;

import Comprehensive.Utils.objs.ballObject;

import java.awt.*;
import java.util.ArrayList;

public class table {

    public static ArrayList<ballObject> dayList;

    public static void tablePrint(int year, int mouth, int week) {
        int startX, startY = 250;
        int printTime = getMouthDay(year, mouth);
        //周日到周六依次为12345678,周六是最后一天
        startX = week * 120 + 100;
        dayList = new ArrayList<>();
        for (int i = 1; i <= printTime; i++) {
            dayList.add(new ballObject(ImageUtils.ball, startX, startY, i, Color.CYAN));
            startX += 120;
            if (startX > 900) {
                startY += 80;
                startX = 100;
            }
        }
    }

    public static int getMouthDay(int year, int mouth) {
        switch (mouth) {
            case 4:
            case 9:
            case 6:
            case 11: {
                return 30;
            }

            case 1:
            case 3:
            case 5:
            case 7:
            case 12: {
                return 31;
            }
            case 2: {
                //非整百年能被4整除的为闰年
                //能被400整除的是闰年
                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                    return 29;
                } else return 28;
            }
            default:
                return 30;
        }
    }
}
