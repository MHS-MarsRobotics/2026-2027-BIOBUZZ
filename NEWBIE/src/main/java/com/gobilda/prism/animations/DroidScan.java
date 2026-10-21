package com.gobilda.prism.animations;

import com.gobilda.prism.types.Color;

public class DroidScan extends AnimationBase {
    private Color primaryColor;
    private Color secondaryColor;
    private float speed = 0.5f;
    private int eyeWidth = 3;
    private int trailWidth = 5;

    public DroidScan(Color primary) {
        super(5); // 5 = DROID_SCAN
        this.primaryColor = primary != null ? primary : Color.RED;
        this.secondaryColor = Color.BLACK;
    }

    public Color getPrimaryColor() { return primaryColor; }
    public void setPrimaryColor(Color c) { this.primaryColor = c; }

    public Color getSecondaryColor() { return secondaryColor; }
    public void setSecondaryColor(Color c) { this.secondaryColor = c; }

    public float getSpeed() { return speed; }
    public void setSpeed(float s) { this.speed = s; }

    public int getEyeWidth() { return eyeWidth; }
    public void setEyeWidth(int w) { this.eyeWidth = w; }

    public int getTrailWidth() { return trailWidth; }
    public void setTrailWidth(int w) { this.trailWidth = w; }
}
