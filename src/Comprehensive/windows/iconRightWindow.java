/**
 * @Project calendarDemo
 * @File iconRightWindow
 * @Time 2022/12/28 23:52
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.windows;

import javax.swing.*;
import java.awt.*;

public class iconRightWindow extends JFrame {
    public iconRightWindow(Point location) {
        this.setSize(100, 30);
        this.setBackground(new Color(0, 0, 0, 255));
        this.setLocation((int) (location.getX() - 10), (int) (location.getY() - 40));
        this.setAlwaysOnTop(true);
        //去除边框
        this.setUndecorated(true);
        //父类与子类可见性
        this.setVisible(true);
        JButton button = new JButton("退出");
        button.addActionListener(e -> System.exit(0));
        this.add(button);
    }
}
