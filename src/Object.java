import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Object {
    protected int x;
    protected int y;
    protected Image objectImage;

    protected boolean picked = false;
    private final int ICON_X = 10;
    private static int icon_y = 35;
    private final int ICON_OFFSET = 40;

    private final int FPS = 60;
    private int counter =1;
    private int millisecond;

    /**
     * Constructor for all type of Object
     * @param startX Initial (might be final) X position of the object
     * @param startY Initial (might be final) Y position of the object
     * @param objectImage This is the image of the object we want to draw on screen
     */
    public Object(int startX, int startY, Image objectImage){
        this.x = startX;
        this.y = startY;
        this.objectImage = objectImage;
    }

    /**
     * Update the image of the object as the game go on
     */
    public void update() {
        // This mainly to deal with the circumstance of sailor collide with bomb
        // Trigger the explosion and "picked" the bomb i.e. not rendering it on screen after explosion
        if (this instanceof Bomb && ((Bomb) this).exploded) {
            counter++;
            millisecond = counter * 1000/FPS;
            if (millisecond > ((Bomb) this).EXPLODE_DURATION){
                picked = true;
                return;
            }
        }

        objectImage.drawFromTopLeft(x, y);
    }

    /**
     * Get a bounding box around the image
     * @return the bounding box
     */
    public Rectangle getBoundingBox(){
        Rectangle boxRectangle = objectImage.getBoundingBoxAt(new Point(x, y));
        // This move the center to x,y due to we are rendering the image from top-left
        boxRectangle.moveTo(new Point(x,y));
        return boxRectangle;
    }

    /**
     * Taking in the itemIcon and draw it under health bar with OFFSET, so they would not overlap.
     * @param itemIcon This is the item Icon in res that we want to draw
     */
    public void getPicked (Image itemIcon) {
        objectImage = itemIcon;
        x = ICON_X;
        y = icon_y;
        icon_y += ICON_OFFSET;
        picked = true;
    }

}
