package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


public class Clorus extends Creature {

    private int r;

    private int g;

    private int b;

    private static final double ENERGY_MOVE = 0.03;


    private static final double ENERGY_STAY = 0.01;


    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Color color() {
        return color(r, g, b);
    }

    public void move() {
        energy = energy - ENERGY_MOVE;
    }

    public void stay() {
        energy = energy - ENERGY_STAY;
    }

    public void attack(Creature C) {
        energy += C.energy();
    }

    public Clorus replicate() {
        double ene = energy / 2;
        energy = ene;
        return new Clorus(ene);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> p = new ArrayDeque<>();


        for (Direction x : neighbors.keySet()) {
            if (neighbors.get(x).name().equals("plip")) {
                p.addFirst(x);
            }
        }

        for (Direction x : neighbors.keySet()) {
            Occupant y = neighbors.get(x);
            if (y.name().equals("empty")) {
                emptyNeighbors.add(x);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (p.size() > 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(p));
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }
}
