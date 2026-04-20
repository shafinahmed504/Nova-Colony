package com.example.novacolony;

public class Soldier extends CrewMember {

    public Soldier(int id, String name) {
        super(id, name, "Soldier");
        skill = 10;
        maxEnergy = 85;
        energy = maxEnergy;
    }
}