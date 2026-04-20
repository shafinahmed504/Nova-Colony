package com.example.novacolony;

public class Pilot extends CrewMember {

    public Pilot(int id, String name) {
        super(id, name, "Pilot");
        skill = 10;
        maxEnergy = 120;
        energy = maxEnergy;
    }
}