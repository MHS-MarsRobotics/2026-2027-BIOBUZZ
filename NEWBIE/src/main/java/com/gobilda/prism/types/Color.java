package com.gobilda.prism.types;

public class Color {
    public int red;
    public int green;
    public int blue;

    public Color(int r, int g, int b) {
        this.red = r & 0xFF;
        this.green = g & 0xFF;
        this.blue = b & 0xFF;
    }

    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color ORANGE = new Color(255, 165, 0);
    public static final Color PURPLE = new Color(128, 0, 128);
}
