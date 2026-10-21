package com.gobilda.prism.animations;

import com.gobilda.prism.types.Color;

public class Snakes extends AnimationBase {
    private Color[] colors;
    private int snakeLength = 5;
    private int spacingBetween = 2;
    private int repeatAfter = 15;
    private Color backgroundColor = Color.BLACK;
    private float speed = 0.5f;
    private boolean forwardDirection = true;

    public Snakes(Color[] colors, int snakeLength, int spacingBetween, float speed) {
        super(7); // 7 = SNAKES
        if (colors != null && colors.length > 0) {
            this.colors = colors;
        } else {
            this.colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE};
        }
        this.snakeLength = Math.max(1, snakeLength);
        this.spacingBetween = Math.max(0, spacingBetween);
        this.speed = speed;
    }

    public Snakes(Color[] colors) {
        this(colors, 5, 2, 0.5f);
    }

    public Color[] getColors() { return colors; }
    public void setColors(Color[] colors) { this.colors = colors; }

    public int getSnakeLength() { return snakeLength; }
    public void setSnakeLength(int length) { this.snakeLength = length; }

    public int getSpacingBetween() { return spacingBetween; }
    public void setSpacingBetween(int spacing) { this.spacingBetween = spacing; }

    public int getRepeatAfter() { return repeatAfter; }
    public void setRepeatAfter(int repeat) { this.repeatAfter = repeat; }

    public Color getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(Color bg) { this.backgroundColor = bg; }

    public float getSpeed() { return speed; }
    public void setSpeed(float s) { this.speed = s; }

    public boolean isForwardDirection() { return forwardDirection; }
    public void setForwardDirection(boolean forward) { this.forwardDirection = forward; }
}
