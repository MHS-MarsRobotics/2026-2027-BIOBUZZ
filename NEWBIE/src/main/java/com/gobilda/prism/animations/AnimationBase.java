package com.gobilda.prism.animations;

public abstract class AnimationBase {
    protected int animationType;
    protected int brightness = 100;
    protected int startIndex = 0;
    protected int stopIndex = 255;

    public AnimationBase(int type) {
        this.animationType = type;
    }

    public int getAnimationType() {
        return animationType;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = Math.min(100, Math.max(0, brightness));
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStopIndex() {
        return stopIndex;
    }

    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }
}
