import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("car"));
        assertFalse(palindrome.isPalindrome("September"));
        assertFalse(palindrome.isPalindrome("Racecar"));
    }

    @Test
    public void testIsPalindrome1() {
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
        assertTrue(palindrome.isPalindrome("afijeb", new OffByOne()));
        assertFalse(palindrome.isPalindrome("racecar", new OffByOne()));
    }


    public static void main(String[] args) {

        TestPalindrome test = new TestPalindrome();
        test.testWordToDeque();
        test.testIsPalindrome();
        test.testIsPalindrome1();

    }
}
