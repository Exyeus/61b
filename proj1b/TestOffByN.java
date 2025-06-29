import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN = new OffByN(3);

    @Test
    public void testEqualChars() {
        assertTrue(offByN.equalChars('a', 'd'));
        assertFalse(offByN.equalChars('a','a'));
        assertFalse(offByN.equalChars('a', 'f'));
    }
}
