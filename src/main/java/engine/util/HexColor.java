package engine.util;

import java.awt.*;

public class HexColor {

    public Color color;

    public HexColor(String hex, int opacity) {
        color = new Color(Integer.valueOf( hex.substring( 1, 3 ), 16 ), Integer.valueOf( hex.substring( 3, 5 ), 16 ), Integer.valueOf( hex.substring( 5, 7 ), 16 ), opacity);
    }

}
