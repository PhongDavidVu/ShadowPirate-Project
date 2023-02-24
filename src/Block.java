import bagel.Image;


public class Block extends Object {
    private final static Image BLOCK = new Image("res/block.png");

    /**
     * This is this Constructor for a block which just act as a obstacles for all characters could not pass
     * @param startX The X position of a block
     * @param startY The y position of a block
     */
    public Block(int startX, int startY){
        super(startX,startY, BLOCK);
    }



}