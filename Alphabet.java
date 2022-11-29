package enigma;



/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Bond Chapiprasit
 */
class Alphabet {
    /** String representing alphabet. */
    private String alpha;
    /** A new alphabet containing CHARS. The K-th character has index
     *  K (numbering from 0). No character may be duplicated. */

    Alphabet(String chars) {


        alpha = chars;

    }
    /** Returns the alphabet. */
    String alpha() {
        return alpha;
    }


    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return alpha.length();
    }

    /**Returns a number so its not out of bound. @param p is input */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        char[] alphachar = alpha.toCharArray();
        for (char c: alphachar) {
            if (c == ch) {
                return true;
            }
        }
        return false;


    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return alpha.charAt(wrap(index));
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        return alpha.indexOf(ch);
    }

}
