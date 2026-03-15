package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {

    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {

        RaidResult result = new RaidResult();

        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            result.setWinner("Invalid input");
            result.addLine("Raid failed: null input");
            return result;
        }

        int rounds = 0;
        int maxRounds = 50;

        result.addLine("Raid started: " + teamA.getName() + " vs " + teamB.getName());

        while (teamA.isAlive() && teamB.isAlive() && rounds < maxRounds) {

            rounds++;
            result.addLine("Round " + rounds);

            // Team A attacks
            teamASkill.cast(teamB);
            result.addLine(teamA.getName() + " uses " + teamASkill.getSkillName());

            if (!teamB.isAlive()) {
                break;
            }

            // Team B attacks
            teamBSkill.cast(teamA);
            result.addLine(teamB.getName() + " uses " + teamBSkill.getSkillName());
        }

        result.setRounds(rounds);

        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }

        result.addLine("Raid finished. Winner: " + result.getWinner());

        return result;
    }
}