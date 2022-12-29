/**
 * @Project dingLaoZuoYe
 * @File Utils
 * @Time 2022/12/27 12:05
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.Utils;

import Comprehensive.windows.perpetual_calendar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ImageUtils {
    public static Image ball, bg1, bg3, apple, book, bookshelf, cookie, fall_icon, fish, exit_icon, iron_pickaxe, smiting;

    //图片资源获取
    static {
        try {
            ball = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/ball.png")));
            bg1 = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/penguin.png")));
            bg3 = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/bg3.png")));
            apple = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_apple.png")));
            book = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_book_writable.png")));
            bookshelf = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_bookshelf.png")));
            cookie = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_cookie.png")));
            fall_icon = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_fall.png")));
            fish = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_fish_clownfish_raw.png")));
            exit_icon = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_import.png")));
            iron_pickaxe = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/icon_iron_pickaxe.png")));
            smiting = ImageIO.read(Objects.requireNonNull(perpetual_calendar.class.getClassLoader().getResource("resources/smiting_icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
