package com.example.novacolony;

public class MissionSystem {

    public static String runMission(CrewMember a, CrewMember b, int difficulty) {

        int enemyHealth = 50 + difficulty * 10;

        String log = "Mission Start\n";

        while (enemyHealth > 0 && (a.isAlive() || b.isAlive())) {

            if (a.isAlive()) {
                int dmg = a.attack();
                enemyHealth -= dmg;
                log += a.getName() + " hits enemy for " + dmg + "\n";
            }

            if (enemyHealth <= 0) break;

            if (b.isAlive()) {
                int dmg = b.attack();
                enemyHealth -= dmg;
                log += b.getName() + " hits enemy for " + dmg + "\n";
            }

            if (enemyHealth <= 0) break;

            int damage = 10 + difficulty * 2;

            if (a.isAlive()) {
                a.takeDamage(damage);
                log += a.getName() + " takes " + damage + " damage\n";
            }

            if (b.isAlive()) {
                b.takeDamage(damage);
                log += b.getName() + " takes " + damage + " damage\n";
            }
        }

        if (enemyHealth <= 0) {
            if (a.isAlive()) a.train();
            if (b.isAlive()) b.train();
            log += "MISSION SUCCESS";
        } else {
            log += "MISSION FAILED";
        }

        return log;
    }
}