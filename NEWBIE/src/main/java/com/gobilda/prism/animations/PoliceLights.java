package com.gobilda.prism.animations;

public class PoliceLights extends AnimationBase {
    private int period = 500;

    public PoliceLights() {
        super(12); // 12 = POLICE_LIGHTS
    }

    public int getPeriod() { return period; }
    public void setPeriod(int p) { this.period = p; }
}
