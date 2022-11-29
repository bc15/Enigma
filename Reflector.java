package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a reflector in the enigma.
 *  @author Bond Chaiprasit
 */
class Reflector extends FixedRotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM. */
    Reflector(String name, Permutation perm) {
        super(name, perm);

    }



    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("reflector has only one position");
        }
    }

    /** Return if this rotor is refelcting. */
    boolean reflecting() {
        return true;
    }

    /** ConvertForward for reflector. @return is int. @param p is int.*/
    int convertForward(int p) {
        return this.permutation().permute(p);

    }



}
