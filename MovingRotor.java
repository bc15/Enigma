package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Bond Chaiprasit
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;

    }


    @Override
    boolean rotates() {
        return true;
    }


    @Override
    void advance() {
        this.set((this.setting() + 1) % p().alphabet().size());

    }
    /** check if Rotor is at notch. @return is boolean */
    boolean atNotch() {
        char [] notch = _notches.toCharArray();
        for (char n : notch) {
            if (n == this.alphabet().toChar(this.setting())) {
                return true;
            }
        }
        return false;
    }



    /** Notches of the rotor. */
    private String _notches;
}
