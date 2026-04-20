package com.example.novacolony;

public class Engineer extends CrewMember {

    public Engineer(int id, String name) {
        super(id, name, "Engineer");
        skill = 7;
        maxEnergy = 110;
        energy = maxEnergy;
    }
}