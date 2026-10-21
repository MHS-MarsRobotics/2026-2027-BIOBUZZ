package com.gobilda.prism.types;

public enum LayerHeight {
    LAYER_0(0),
    LAYER_1(1),
    LAYER_2(2),
    LAYER_3(3),
    LAYER_4(4),
    LAYER_5(5),
    LAYER_6(6),
    LAYER_7(7),
    LAYER_8(8),
    LAYER_9(9),
    LAYER_DISABLED(255);

    private final int value;

    LayerHeight(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
