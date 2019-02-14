public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        if (Character.isLetter(x) && Character.isLetter((y))) {
            return Math.abs(x - y) == 1;
        } else {
            return false;
        }
    }
}
