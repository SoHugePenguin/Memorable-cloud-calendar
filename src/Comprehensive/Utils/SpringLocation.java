/**
 * @Project calendarDemo
 * @File SpringLocation
 * @Time 2022/12/29 14:33
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.Utils;

import javax.swing.*;

public class SpringLocation {
    public static void setLocation(SpringLayout layout, JComponent component, int x, int y) {
        SpringLayout.Constraints scrollPaneC = layout.getConstraints(component);
        scrollPaneC.setX(Spring.constant(x));
        scrollPaneC.setY(Spring.constant(y));
    }
}
