import bagel.Image;


public class Elixir extends Object implements Affectable {
    private final static Image ELIXIR= new Image("res/items/elixir.png");
    private final static Image ELIXIR_ICON =  new Image("res/items/elixirIcon.png");
    private final static int HEALTH_INCREASE = 35;

    /**
     * Constructor for Elixir, which super from Object class
     * @param startX The X position of Elixir
     * @param startY The Y position of Elixir
     */
    public Elixir(int startX, int startY){
        super(startX,startY,ELIXIR);
    }

    /**
     * Implement the affect of max health and heal for gainer
     * @param gainer This is the character to be gained the effect
     */
    @Override
    public void implementAffect(Character gainer) {
        gainer.max_health += HEALTH_INCREASE;
        gainer.healthPoints = gainer.max_health;
        String newHealth = gainer.healthPoints + "/" + gainer.max_health;
        System.out.println("Sailor finds Elixir.  Sailor’s current health:  "+newHealth);
        getPicked(ELIXIR_ICON);


    }


}