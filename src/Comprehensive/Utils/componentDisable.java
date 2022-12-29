/**
 * @Project calendarDemo
 * @File disableCompent
 * @Time 2022/12/29 16:05
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.Utils;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class componentDisable {
    public static void disable(JComponent component, int second) {
        final int[] time = {second};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                component.setEnabled(false);
                time[0]--;
                if (time[0] < 1) {
                    component.setEnabled(true);
                    component.setVisible(true);
                    cancel();
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }
}
