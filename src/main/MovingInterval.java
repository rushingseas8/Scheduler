package main;

public class MovingInterval {
    public float min;
    public float max;
    public float width;
    public float pos;

    /**
     * Creates a new MovingInterval. This can be thought of as a block
     * of time that can occur within a range. You can set the start of
     * the time by calling setPosition(); by default it is as far left
     * as possible within the constraints.
     *
     * @param min The start of the range
     * @param max The end of the range
     * @param width The length of the block of time
     */
    public MovingInterval(float min, float max, float width) {
        this.min = min;
        this.max = max;
        this.width = width;

        this.pos = min;
    }

    public void setPosition(float pos) {
        if (pos < min) {
            this.pos = min;
        } else if (pos > max - width) {
            this.pos = max - width;
        } else {
            this.pos = pos;
        }
    }

    @Override
    public String toString() {
        return "Block of length: " + width + " over track [" + min + "," + max + "]";
    }

}
