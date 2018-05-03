import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        //Checking true statements
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('X', 'Y'));


        //Checking false statements
        assertFalse(offByOne.equalChars('f', 'b'));
        assertFalse(offByOne.equalChars('r', 'b'));
        assertFalse(offByOne.equalChars('x', 's'));

        assertFalse(offByOne.equalChars('A', 'b'));
        assertFalse(offByOne.equalChars('Z', 'a'));

        assertTrue(offByOne.equalChars('(', ')'));
        assertTrue(offByOne.equalChars('=', '>'));
        assertTrue(offByOne.equalChars('@', 'A'));

        assertFalse(offByOne.equalChars('&', ')'));
        assertFalse(offByOne.equalChars('!', '?'));
    }
}
