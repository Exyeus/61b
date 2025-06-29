// import static org.junit.Assert.assertEquals;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> result = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return (Deque<Character>) result;
    }
    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word); // O(N) to build deque
        while (d.size() > 1) { // 只要还有至少两个字符
            if (!d.removeFirst().equals(d.removeLast())) { // O(1) removals and comparison
                return false;
            }
        }
        return true; // 队列为空或只剩一个字符时，表示是回文

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
        // 无需创建完整的 reversedWord
        // 直接从两端比较
        Deque<Character> d = wordToDeque(word); // O(N) to build deque
        while (d.size() > 1) { // 只要还有至少两个字符
            if (!cc.equalChars(d.removeFirst(), d.removeLast())) {
                return false;
            }
        }
        return true;



    }
}
