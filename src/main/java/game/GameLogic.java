package game;

import engine.script.JisoScript;

public class GameLogic extends JisoScript {
    private int coins = 1000;

    @Override
    public void onStart() {

    }

    @Override
    public void onDrawFrame() {

    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public boolean removeCoins(int amount) {
        if(coins - amount < 0) return false;
        coins -= amount;
        return true;
    }

    public void setCoins(int amount) {
        if(amount > 0) {
            coins = amount;
        }
    }

    public int getCoins() {
        return coins;
    }
}
