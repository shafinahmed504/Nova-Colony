//package com.example.novacolony;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class ColonyManager {
//
//    private HashMap<Integer, CrewMember> crewMap;
//    private int idCounter;
//
//    public ColonyManager() {
//        crewMap = new HashMap<>();
//        idCounter = 0;
//    }
//
//    public void recruit(String name, String specialization) {
//
//        CrewMember crew;
//
//        switch (specialization) {
//            case "Pilot":
//                crew = new Pilot(idCounter, name);
//                break;
//            case "Engineer":
//                crew = new Engineer(idCounter, name);
//                break;
//            case "Medic":
//                crew = new Medic(idCounter, name);
//                break;
//            case "Scientist":
//                crew = new Scientist(idCounter, name);
//                break;
//            case "Soldier":
//                crew = new Soldier(idCounter, name);
//                break;
//            default:
//                crew = new Pilot(idCounter, name);
//        }
//
//        crewMap.put(idCounter, crew);
//        idCounter++;
//    }
//
//    public ArrayList<CrewMember> getAllCrew() {
//        return new ArrayList<>(crewMap.values());
//    }
//
//    public CrewMember getCrewById(int id) {
//        return crewMap.get(id);
//    }
//
//    public void restoreAllEnergy() {
//        for (CrewMember c : crewMap.values()) {
//            c.restoreEnergy();
//        }
//    }
//
//    public int getCrewCount() {
//        return crewMap.size();
//    }
//}


package com.example.novacolony;

import java.util.ArrayList;
import java.util.HashMap;

public class ColonyManager {

    private HashMap<Integer, CrewMember> crewMap;
    private int idCounter;

    public ColonyManager() {
        crewMap = new HashMap<>();
        idCounter = 0;
    }

    public void recruit(String name, String specialization) {

        CrewMember crew;

        switch (specialization) {
            case "Pilot":
                crew = new Pilot(idCounter, name);
                break;
            case "Engineer":
                crew = new Engineer(idCounter, name);
                break;
            case "Medic":
                crew = new Medic(idCounter, name);
                break;
            case "Scientist":
                crew = new Scientist(idCounter, name);
                break;
            case "Soldier":
                crew = new Soldier(idCounter, name);
                break;
            default:
                crew = new Pilot(idCounter, name);
        }

        crewMap.put(idCounter, crew);
        idCounter++;
    }

    public ArrayList<CrewMember> getAllCrew() {
        return new ArrayList<>(crewMap.values());
    }

    public CrewMember getCrewById(int id) {
        return crewMap.get(id);
    }

    public void restoreAllEnergy() {
        for (CrewMember c : crewMap.values()) {
            c.restoreEnergy();
        }
    }

    public int getCrewCount() {
        return crewMap.size();
    }

    public void removeDeadCrew() {
        crewMap.values().removeIf(c -> !c.isAlive());
    }
}