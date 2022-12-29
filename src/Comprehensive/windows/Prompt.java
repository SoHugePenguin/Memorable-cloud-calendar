/**
 * @Project calendarDemo
 * @File Prompt
 * @Time 2022/12/28 21:55
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.windows;

import Comprehensive.Utils.ImageUtils;
import Comprehensive.Utils.SpringLocation;

import javax.swing.*;
import java.awt.*;

public class Prompt extends JFrame {
    public Prompt(String text) {
        this.setBounds(520, 300, 400, 250);
        this.setIconImage(ImageUtils.iron_pickaxe);
        this.setTitle("提示");
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setBackground(Color.black);


        JPanel panel = new JPanel(true);
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        JLabel label = new JLabel(text);
        label.setFont(new Font("楷体", Font.BOLD, 30));
        label.setForeground(Color.orange);
        JButton button = new JButton("确认");
        button.setFont(new Font("楷体", Font.BOLD, 35));
        button.setForeground(Color.black);
        button.addActionListener(e -> this.dispose());

        panel.add(label);
        panel.add(button);

        SpringLocation.setLocation(layout, label, 88, 80);
        SpringLocation.setLocation(layout, button, 105, 140);

        this.add(panel);
        this.setVisible(true);
    }
}
