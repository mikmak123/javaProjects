package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private DoubleMapPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private LinkedList<Vertex> sol;
    private AStarGraph in;
    private Vertex st;
    private Vertex en;
    private SolverOutcome out;
    private double time;
    private int numStates;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch clock = new Stopwatch();
        pq = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        in = input;
        sol = new LinkedList<>();
        st = start;
        en = end;
        edgeTo = new HashMap<>();


        pq.add(start, 0 + in.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        edgeTo.put(start, null);

        while (pq.size() != 0 && !pq.getSmallest().equals(end)) {
            if (clock.elapsedTime() > timeout) {
                out = SolverOutcome.TIMEOUT;
                time = clock.elapsedTime();
                return;
            }
            Vertex p = pq.removeSmallest();
            numStates++;

            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge x : neighborEdges) {
                relax(x);
            }
        }

        if (pq.size() == 0) {
            out = SolverOutcome.UNSOLVABLE;
            time = clock.elapsedTime();
            return;
        }
        if (pq.getSmallest().equals(en)) {
            out = SolverOutcome.SOLVED;
            time = clock.elapsedTime();
            sol();
            return;
        }
    }

    private void relax(WeightedEdge p) {

        Vertex e = (Vertex) p.from();
        Vertex q = (Vertex) p.to();
        double w = p.weight();

        if (!distTo.containsKey(q)) {
            distTo.put(q, distTo.get(e) + w);
            pq.add(q, distTo.get(q) + in.estimatedDistanceToGoal(q, en));
            edgeTo.put(q, e);
        } else {
                if (distTo.get(e) + w < distTo.get(q)) {
                    distTo.put(q, distTo.get(e) + w);
                    pq.changePriority(q, distTo.get(q) + in.estimatedDistanceToGoal(q, en));
                    edgeTo.put(q, e);
                }
            }
    }


    /**
     private double h(Vertex v) {

        if (goal.containsKey(v)) {
            return goal.get(v);
        } else {
            List<WeightedEdge<Vertex>> neighborEdges = in.neighbors(v);
            double min = Double.MAX_VALUE;
            for (WeightedEdge w : neighborEdges) {
                if (w.weight() < min) {
                    min = w.weight();
                }
            }
            goal.put(v, min);
            return min;
        }
    }
     */


    @Override
    public double explorationTime() {
        return time;
    }

    @Override
    public double solutionWeight() {
        if (outcome().equals(SolverOutcome.TIMEOUT) || outcome().equals(SolverOutcome.UNSOLVABLE)) {
            return 0;
        } else {
            return distTo.get(en);
        }
    }

    @Override
    public int numStatesExplored() {
        return numStates;
    }

    private void sol() {
        Vertex n = en;
        sol.addFirst(n);
        n = edgeTo.get(n);
        while (!n.equals(st)) {
            sol.addFirst(n);
            n = edgeTo.get(n);
        }
        sol.addFirst(st);
    }

    @Override
    public List<Vertex> solution() {
        if (outcome().equals(SolverOutcome.TIMEOUT) || outcome().equals(SolverOutcome.UNSOLVABLE)) {
            return new ArrayList<>();
        } else {
            return sol;
        }
    }


    @Override
    public SolverOutcome outcome() {
        return out;
    }
}
