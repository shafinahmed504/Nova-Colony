package com.example.novacolony;

public abstract class CrewMember {

    protected int id;
    protected String name;
    protected String specialization;

    protected int skill;
    protected int energy;
    protected int maxEnergy;
    protected int xp;

    public CrewMember(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.xp = 0;
    }

    public void train() {
        xp += 2;
        skill += 2;
        energy -= 10;
        if (energy < 0) energy = 0;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void restoreEnergy() {
        energy = maxEnergy;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void takeDamage(int damage) {
        energy -= damage;
        if (energy < 0) energy = 0;
    }

    public int attack() {
        return skill + (int)(Math.random() * 5);
    }

    public String getName() { return name; }
    public int getEnergy() { return energy; }
    public int getSkill() { return skill; }
    public int getXp() { return xp; }
}