/**
 * @Project dingLaoZuoYe
 * @File runMain
 * @Time 2022/12/28 16:43
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive;/*
 *dingLaoZuoYe -> runMain
 *2022/12/28 16:43
 *ToDo:
 *
 * @Author: SoHugePenguin
 */

import Comprehensive.windows.perpetual_calendar;

import static Comprehensive.jdbc.sqlMain.druid_connection;

public class runMain {
    public static void main(String[] args) throws Exception {
        druid_connection();
        perpetual_calendar.main = new perpetual_calendar();
        perpetual_calendar.main.launch();
    }
}
