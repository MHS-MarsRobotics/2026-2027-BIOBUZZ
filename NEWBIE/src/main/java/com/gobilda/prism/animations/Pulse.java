package com.gobilda.prism.animations;

import com.gobilda.prism.types.Color;

public class Pulse extends AnimationBase {
    private Color primaryColor;
    private Color secondaryColor;
    private int period = 1000;

    public Pulse(Color primary, Color secondary) {
        super(3); // 3 = PULSE
        this.primaryColor = primary != null ? primary : Color.GREEN;
        this.secondaryColor = secondary != null ? secondary : Color.BLACK;
    }

    public Pulse(Color primary) {
        this(primary, Color.BLACK);
    }

    public Color getPrimaryColor() { return primaryColor; }
    public void setPrimaryColor(Color c) { this.primaryColor = c; }

    public Color getSecondaryColor() { return secondaryColor; }
    public void setSecondaryColor(Color c) { this.secondaryColor = c; }

    public int getPeriod() { return period; }
    public void setPeriod(int p) { this.period = p; }
}
