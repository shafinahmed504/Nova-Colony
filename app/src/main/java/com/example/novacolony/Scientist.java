package com.example.novacolony;

public class Scientist extends CrewMember {

    public Scientist(int id, String name) {
        super(id, name, "Scientist");
        skill = 9;
        maxEnergy = 90;
        energy = maxEnergy;
    }
}