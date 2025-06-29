public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(Character.toLowerCase(x)
                - Character.toLowerCase(y)) == 1;
    }
}
