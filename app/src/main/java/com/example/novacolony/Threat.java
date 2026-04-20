package com.example.novacolony;

public class Threat {

    private int health;
    private int damage;

    public Threat(int missionCount) {
        this.health = 50 + (missionCount * 5);
        this.damage = 10 + (missionCount * 2);
    }

    public void takeDamage(int dmg) {
        health -= dmg;
    }

    public int attack() {
        return damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }
}