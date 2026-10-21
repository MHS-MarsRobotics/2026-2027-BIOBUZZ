package com.gobilda.prism.animations;

public class Rainbow extends AnimationBase {
    private float startHue = 0.0f;
    private float stopHue = 360.0f;
    private float speed = 0.5f;

    public Rainbow() {
        super(6); // 6 = RAINBOW
    }

    public float getStartHue() { return startHue; }
    public void setStartHue(float h) { this.startHue = h; }

    public float getStopHue() { return stopHue; }
    public void setStopHue(float h) { this.stopHue = h; }

    public float getSpeed() { return speed; }
    public void setSpeed(float s) { this.speed = s; }
}
