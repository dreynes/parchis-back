package miw.tfm.parchis.models;

public class Dice {


    private int value;
    public Dice(){}

    public int roll(){
        this.value = (int) (Math.random() * 6) + 1;
        return this.value;
    }
    public int getValue(){
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
