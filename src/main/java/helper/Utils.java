package helper;

import javax.swing.*;
import java.awt.*;

public class Utils {

    public static void setCenter(Window widow) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        widow.setLocation(dim.width / 2 - widow.getSize().width / 2, dim.height / 2 - widow.getSize().height / 2);
    }


    public static void closeFrame(JPanel panel) {
        ((Window) panel.getRootPane().getParent()).dispose();
    }
}
