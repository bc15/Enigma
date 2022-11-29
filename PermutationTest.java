package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author
 */
public class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    private Permutation getNewPermutation(String cycles, Alphabet alphabet) {
        Permutation p = new Permutation(cycles, alphabet);
        return p;
    }

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    private Alphabet getNewAlphabet(String chars) {
        Alphabet a = new Alphabet(chars);
        return a;

    }

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    private Alphabet getNewAlphabet() {
        Alphabet b = new Alphabet();
        return b;
    }

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
    }
    @Test
    public void testInvertChar() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABECD"));
        assertEquals('B', p.invert('A'));
        assertEquals('D', p.invert('B'));
        assertEquals('E', p.invert('E'));


    }

    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        p.invert('F');
    }

    @Test
    public void testLongCycle() {
        Permutation p = getNewPermutation("(BACD) (ZRQ)",
                getNewAlphabet("ABCDZRQ"));
        assertEquals('C', p.permute('A'));
        assertEquals('B', p.permute('D'));
        assertEquals('R', p.permute('Z'));
        assertEquals('Z', p.permute('Q'));

    }

    @Test
    public void testPermuteChar() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCDZ"));
        assertEquals('C', p.permute('A'));
        assertEquals('B', p.permute('D'));
        assertEquals('Z', p.permute('Z'));

    }

    @Test
    public void testPermuteInt() {
        Permutation p = getNewPermutation("(BACD)",
                getNewAlphabet("ABCD"));
        assertEquals(2, p.permute(0));
        assertEquals(2, p.permute(4));
        assertEquals(1, p.permute(3));
        Permutation c = getNewPermutation("(BACD)(EGF)",
                getNewAlphabet("ABCDEFG"));
        assertEquals(6, c.permute(4));
        assertEquals(4, c.permute(-2));


    }

    @Test
    public void testInvertInt() {
        Permutation p = getNewPermutation("(BACD)",
                getNewAlphabet("ABCD"));
        assertEquals(1, p.invert(0));
        assertEquals(1, p.invert(4));
        assertEquals(2, p.invert(3));
        assertEquals(3, p.invert(1));
        assertEquals(0, p.invert(-2));
    }
    @Test
    public void sizeAlphabet() {
        Permutation p = getNewPermutation("(BACD)",
                getNewAlphabet("ABCD"));
        assertEquals(4, p.size());


    }

    @Test
    public void derangementTest() {
        Permutation p = getNewPermutation("(BACD)",
                getNewAlphabet("ABCD"));
        assertTrue(p.derangement());
        Permutation o = getNewPermutation("(BACD)",
                getNewAlphabet("ABCDZ"));
        assertFalse(o.derangement());
    }

    @Test
    public void addCycleTest() {
        Permutation p = getNewPermutation("(BACD)",
                getNewAlphabet("ABCD"));

    }


}
