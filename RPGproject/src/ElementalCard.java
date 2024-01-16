//Minigame#5, Back-end class by Kai C.

public class ElementalCard {
    private int value;
    private int element;

    public final static int fire = 0;
    public final static int water = 1;
    public final static int snow = 2;

    ElementalCard() {
        value = (int)(Math.random()*5 + 1);

        int randElement = (int)(Math.random()*3);
        if(randElement==fire)
            element = fire;
        else if(randElement==water)
            element = water;
        else
            element = snow;
    }
    public int getValue() {
        return value;
    }
    public int getElement() {
        return element;
    }
}
