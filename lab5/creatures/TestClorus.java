package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class
 *  @authr FIXME
 */

public class TestClorus {


    @Test
    public void testReplicate() {
        // TODO
        Clorus c = new Clorus(2);
        Clorus rep = c.replicate();
        assertNotEquals(c, rep);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Plip(1));
        topEmpty.put(Direction.BOTTOM, new Empty());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Impassible());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Impassible());
        allEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.REPLICATE, Direction.BOTTOM);

        assertEquals(unexpected, actual);


        // Energy < 1; move.
        p = new Clorus(.99);

        actual = p.chooseAction(allEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);

        assertEquals(expected, actual);


        // We don't have Cloruses yet, so we can't test behavior for when they are nearby right now.
    }
}
