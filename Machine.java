package enigma;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;



/** Class that represents a complete enigma machine.
 *  @author Bond Chaiprasit
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        rotorsN = numRotors;
        pawlsN = pawls;
        _rotors = allRotors;
        _rotorsA = _rotors.toArray();
        _rotorsNew = new Rotor[numRotors];

    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return rotorsN;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return pawlsN;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        int index = 0;
        if (!rNew(rotors[0]).reflecting()) {
            throw new EnigmaException("first one must be reflector");
        }
        if (rotorsN - 1 < pawlsN) {
            throw new EnigmaException("more pawls than rotors");
        }
        for (int i = 0; i < rotors.length; i++) {

            Iterator<Rotor> rots = _rotors.iterator();
            while (rots.hasNext() && index != _rotorsNew.length) {
                Rotor next = rots.next();
                String nextName =  next.name();
                if (nextName.equals(rotors[i])) {
                    _rotorsNew[index] = next;
                    index++;
                    break;
                }
            }

        }
        if (rotors.length != numRotors()) {
            throw new EnigmaException("wrong length");
        }
    }







    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("setting wrong length");
        }
        char [] settingc = setting.toCharArray();
        for (int i = 1; i <= settingc.length; i++) {
            if (!_alphabet.contains(settingc[i - 1])) {
                throw new EnigmaException("setting not contained in alphabet");
            }
            ((Rotor) _rotorsNew[i]).set(settingc[i - 1]);
        }



    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        plug = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        boolean [] atNotch = new boolean[_rotorsNew.length];
        Arrays.fill(atNotch, Boolean.FALSE);
        int result = plug.permute(c);

        if (((Rotor) _rotorsNew[_rotorsNew.length - 1]).atNotch()) {
            atNotch[_rotorsNew.length - 1] = true;

        }
        ((Rotor) _rotorsNew[_rotorsNew.length - 1]).advance();
        result = ((Rotor) _rotorsNew[_rotorsNew.length - 1]).
                convertForward(result);

        for (int i = _rotorsNew.length - 2; i > 0; i--) {

            if ((atNotch[i + 1] || (((Rotor) _rotorsNew[i - 1]).rotates()
                    && (((Rotor) _rotorsNew[i]).atNotch())))) {
                if (((Rotor) _rotorsNew[i]).rotates()) {

                    if (((Rotor) _rotorsNew[i]).atNotch()) {
                        atNotch[i] = true;
                    }
                    ((Rotor) _rotorsNew[i]).advance();

                }

            }
            result = ((Rotor) _rotorsNew[i]).convertForward(result);
        }


        result = ((Rotor) _rotorsNew[0]).convertForward(result);

        for (int i = 1; i < _rotorsNew.length; i++) {

            result = ((Rotor) _rotorsNew[i]).convertBackward(result);
        }
        result = plug.invert(result);

        return result;
    }
    /** Returns the rotors of this Machine. */
    Object [] rotorsNew() {
        return _rotorsNew;
    }
    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String result = "";
        char [] message = msg.toCharArray();
        for (char m: message) {
            char c = _alphabet.toChar(convert(_alphabet.toInt(m)));
            result += c;
        }
        return result;
    }
    /** Returns the alphabet. */
    Alphabet rAlphabet() {
        return _alphabet;
    }
    /** Returns a rotor given a name. @param name is name of rotor. */
    Rotor rNew(String name) {
        for (Rotor r: _rotors) {
            if (r.name().equals(name)) {
                return r;
            }

        }
        throw new EnigmaException("rotor not in collection");
    }


    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;
    /** Num of rotors in machine. */
    private int rotorsN;
    /** Num of pawls in machine. */
    private int pawlsN;
    /** Collection of all available rotors. */
    private Collection<Rotor> _rotors;
    /** Plugboard of this machine. */
    private Permutation plug;
    /** Rotors specific to this machine. */
    private Object[] _rotorsNew;
    /** Collection of rotors as array. */
    private Object[] _rotorsA;


}
