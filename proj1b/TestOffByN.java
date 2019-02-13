import org.junit.Test;
import static org.junit.Assert.*;


public class TestOffByN {

    OffByN test = new OffByN(5);

    @Test
    public void testEqualChars() {

        assertTrue(test.equalChars('a', 'f'));
        assertTrue(test.equalChars('f', 'a'));
        assertFalse(test.equalChars('f', 'h'));
        assertFalse(test.equalChars('f', 'e'));

    }

    public static void main(String[] args) {
        OffByN offBy5 = new OffByN(5);
        offBy5.equalChars('a', 'f');
        offBy5.equalChars('f', 'a');
        offBy5.equalChars('f', 'h');

    }


}
