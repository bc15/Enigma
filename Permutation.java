package enigma;


import java.util.Arrays;



/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Bond Chaiprasit
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        cycles = cycles.trim().replaceAll("[()]", " ");
        _cycles = cycles.split(" ");

    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        String t = cycle;
        String[] newCycles = t.trim().split(" ");
        for (int i = 0; i < _cycles.length; i++) {
            if (_cycles[i].equals(newCycles[0])) {
                String [] temp = Arrays.copyOfRange
                        (_cycles, i + 1, _cycles.length);
                System.arraycopy(newCycles, 0, _cycles,
                        i, newCycles.length);
                System.arraycopy(temp, 0, _cycles,
                        i + newCycles.length, temp.length);
                break;

            }

        }


    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char c = this._alphabet.alpha().charAt(wrap(p));
        char res = c;
        for (int i = 0; i < _cycles.length; i++) {
            for (int x = 0; x < _cycles[i].length(); x++) {
                if (c == _cycles[i].charAt(x)) {
                    res = _cycles[i].charAt((x + 1) % _cycles[i].length());
                    break;
                }
            }
        }
        return this._alphabet.alpha().indexOf(res);

    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char p = this._alphabet.alpha().charAt(wrap(c));
        char res = p;
        for (int i = 0; i < _cycles.length; i++) {
            for (int x = 0; x < _cycles[i].length(); x++) {
                if (p == _cycles[i].charAt(x)) {
                    if (x - 1 >= 0) {
                        res = _cycles[i].charAt((x - 1) % _cycles[i].length());
                        break;
                    } else {
                        res = _cycles[i].charAt(_cycles[i].length() - x - 1);
                        break;
                    }

                }
            }
        }
        return this._alphabet.alpha().indexOf(res);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        char c = p;
        char res = c;
        for (int i = 0; i < _cycles.length; i++) {
            for (int x = 0; x < _cycles[i].length(); x++) {
                if (c == _cycles[i].charAt(x)) {
                    res = _cycles[i].charAt((x + 1) % _cycles[i].length());
                    break;
                }
            }
        }
        return res;

    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        char p = c;
        char res = p;
        for (int i = 0; i < _cycles.length; i++) {
            for (int x = 0; x < _cycles[i].length(); x++) {
                if (p == _cycles[i].charAt(x)) {
                    if (x - 1 >= 0) {
                        res = _cycles[i].charAt((x - 1) % _cycles[i].length());
                        break;
                    } else {
                        res = _cycles[i].charAt(_cycles[i].length() - 1);
                        break;
                    }

                }
            }
        }
        return res;
    }


    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        int index = 0;
        while (index != this._alphabet.alpha().length()) {
            for (int i = 0; i < _cycles.length; i++) {
                for (int x = 0; x < _cycles[i].length(); x++) {
                    if (_alphabet.alpha().charAt(index)
                            == _cycles[i].charAt(x)) {
                        index++;
                        break;
                    } else if (_alphabet.alpha().charAt(index)
                            != _cycles[i].charAt(x) && i == _cycles.length - 1
                            && x == _cycles[i].length() - 1) {
                        return false;
                    }

                }
            }

        }
        return true;


    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    /** Cycles of this permutation. */
    private String[] _cycles;

}
