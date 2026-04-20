package com.example.novacolony;

public class Medic extends CrewMember {

    public Medic(int id, String name) {
        super(id, name, "Medic");
        skill = 6;
        maxEnergy = 95;
        energy = maxEnergy;
    }
}