public class State {
    protected boolean idle;
    protected boolean attack;
    protected boolean invincible;
    protected boolean ready;

    /**
     * Initialize the state for a certain character which make everything false at the begining
     */
    public State() {
        this.idle = false;
        this.attack = false;
        this.invincible = false;
        this.ready = false;
    }

    /**
     * This act like a setter to set Idle state to our desire outcome
     * @param bool This is what we want the state to be.
     */
    public void idleState(boolean bool) {
        this.idle = bool;
    }
    /**
     * This act like a setter to set Attack state to our desire outcome
     * @param bool This is what we want the state to be.
     */
    public void attackState(boolean bool) {
        this.attack = bool;
    }
    /**
     * This act like a setter to set Invincible state to our desire outcome
     * @param bool This is what we want the state to be.
     */
    public void invincibleState(boolean bool) {
        this.invincible= bool;
    }

    /**
     * This act like a setter to set ready state to our desire outcome
     * @param bool This is what we want the state to be.
     */
    public void readyState(boolean bool) {
        this.ready = bool;
    }

    /**
     * This act as a method to gain easy access to set certain state true using String only
     * @param state This is the String of state name that we want to set true
     */
    public void setState (String state) {
        if (state.equals("IDLE")) idleState(true);
        else if (state.equals("ATTACK")) attackState(true);
        else if (state.equals("INVINCIBLE")) invincibleState(true);
        else if (state.equals("READY")) readyState(true);

    }




}
