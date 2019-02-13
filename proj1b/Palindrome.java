public class Palindrome {

    public Deque<Character> wordToDeque(String word) {

        Deque<Character> a = new LinkedListDeque<>();

        for (int i = 0; i < word.length(); i++) {
            a.addLast(word.charAt(i));
        }
        return a;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        } else {
            Deque<Character> a = wordToDeque(word);
            boolean test = true;
            while (a.size() > 1) {
                if (a.removeFirst() != a.removeLast()) {
                    test = false;
                }
            }
            return test;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator a) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        } else {
            boolean test = true;

            for (int i = 0; i < (int) (word.length() / 2); i++) {
                if (!a.equalChars(word.charAt(i), word.charAt(word.length() - i - 1))) {
                    test = false;
                }
            }
            return test;
        }
    }
}
