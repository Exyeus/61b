import static org.junit.Assert.assertEquals;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> result = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return (Deque<Character>) result;
    }
    public boolean isPalindrome(String word) {
        if (word.isEmpty() | word.length() == 1) {
            return true;
        }
        Deque<Character> interResult = new LinkedListDeque<>();
        interResult = wordToDeque(word);
        String backwardString = "";
        for (int i = 0; i < word.length(); i++) {
            backwardString += interResult.removeLast();
        }
        if (backwardString.equals(word)) {
            return true;
        }
        return false;
    }
    private String acquireReversedWord(String word) {
        Deque<Character> interResult = new LinkedListDeque<>();
        interResult = wordToDeque(word);
        String backwardString = "";
        for (int i = 0; i < word.length(); i++) {
            backwardString += interResult.removeLast();
        }
        return backwardString;
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        /**
         * The whole function can be reduced into following structure:
         * There is no if-else statement, but instead we use more
         * sophisticated judgement to decide whether to return false.
         */
        String reversedWord = acquireReversedWord(word);
        if (word.length() % 2 == 0) {
            for (int i = 0; i < word.length(); i++) {
                if (!cc.equalChars(word.charAt(i),
                        reversedWord.charAt(i))) {
                    return false;
                }
            }
        } else {
            // word.length() % 2 != 0, and the middle one is allowed
            // for any case!
            for (int i = 0; i < word.length(); i++) {
                if (!cc.equalChars(word.charAt(i),
                        reversedWord.charAt(i))
                        && i != (word.length() + 1) / 2 - 1) {
                    return false;
                }
            }
        }
        return true;

    }
}
