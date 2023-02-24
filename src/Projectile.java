import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;

public class Projectile {
    private Point startPoint;
    private Point directionPoint;
    private double len;
    protected Image projectileImage;
    protected double x;
    protected double y;
    protected double playerDiretionX;
    protected double playerDiretionY;
    protected boolean justHit;
    protected double angle;
    protected DrawOptions rotationAngle = new DrawOptions();


    public Projectile( Image projectileImage, Point startPoint, Point directionPoint) {
        this.projectileImage=projectileImage;
        this.startPoint = startPoint;
        this.x = startPoint.x;
        this.y = startPoint.y;
        justHit=false;
        this.directionPoint = directionPoint;

        len = startPoint.distanceTo(directionPoint);
        playerDiretionX = (directionPoint.x - startPoint.x)/len;
        playerDiretionY = (directionPoint.y - startPoint.y)/len;

        double oppOverAdj = (directionPoint.y - y) / (directionPoint.x -x);
        angle = Math.atan(oppOverAdj);
        rotationAngle.setRotation(angle);
    }



    public void move(double speed,Character target) {
        if (isOutOfBound(target)) return;
            x += speed * playerDiretionX;
            y += speed * playerDiretionY;
            projectileImage.draw(x, y,rotationAngle);

    }

    public boolean isOutOfBound(Character character) {
        if ((y > character.heightBottom) || (y < character.heightTop) ||
                (x < character.widthLeft) || (x > character.widthRight)) {
            return true;
        }
        return false;
    }

}
