package com.gobilda.prism.animations;

import com.gobilda.prism.types.Color;

public class Solid extends AnimationBase {
    private Color primaryColor;

    public Solid(Color color) {
        super(1); // 1 = SOLID
        this.primaryColor = color != null ? color : Color.RED;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(Color color) {
        this.primaryColor = color;
    }
}
