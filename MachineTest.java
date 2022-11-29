package enigma;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;



import static enigma.TestUtils.*;



public class MachineTest {
    private ArrayList<Rotor> rotors = new ArrayList<>();
    private Machine a = new Machine(UPPER, 5, 3, rotors);

    @Test
    public void insertRotorsTest() {
        Reflector r = new Reflector("B", new Permutation("(AE) (BN) (CK) "
                + "(DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", UPPER));
        FixedRotor f = new FixedRotor("BETA", new Permutation("(ALBEVF"
                + "CYODJWUGNMQTZSKPR) (HIX)", UPPER));
        MovingRotor m1 = new MovingRotor("I", new Permutation("(AELTPHQXRU)"
                + " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", UPPER), "Q");
        MovingRotor m2 = new MovingRotor("II", new Permutation("(FIXVYOMW)"
                + " (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", UPPER), "E");
        MovingRotor m3 = new MovingRotor("III", new Permutation("(ABDHPEJT) "
                + "(CFLVMZOYQIRWUKXSG) (N)", UPPER), "V");
        MovingRotor m4 = new MovingRotor("IV", new Permutation("(AEPLIYWCO"
                + "XMRFZBSTGJQNH) (DV) (KU)", UPPER), "J");
        String[] insert = {"B", "BETA", "III", "IV", "I"};
        rotors.add(r);
        rotors.add(f);
        rotors.add(m1);
        rotors.add(m2);
        rotors.add(m3);
        rotors.add(m4);
        a.insertRotors(insert);
        assertEquals("Wrong rotor at 0", rotors.get(0), a.rotorsNew()[0]);
        assertEquals("Wrong rotor at 4", rotors.get(2), a.rotorsNew()[4]);
        assertEquals("Wrong rotor at 4", rotors.get(4), a.rotorsNew()[2]);
    }

    @Test
    public void setRotorsTest() {

        Reflector r = new Reflector("B", new Permutation("(AE) (BN) (CK) "
                + "(DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", UPPER));
        FixedRotor f = new FixedRotor("BETA", new Permutation("(ALBEVF"
                + "CYODJWUGNMQTZSKPR) (HIX)", UPPER));
        MovingRotor m1 = new MovingRotor("I", new Permutation("(AELTPHQXRU)"
                + " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", UPPER), "Q");
        MovingRotor m2 = new MovingRotor("II", new Permutation("(FIXVYOMW)"
                + " (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", UPPER), "E");
        MovingRotor m3 = new MovingRotor("III", new Permutation("(ABDHPEJT) "
                + "(CFLVMZOYQIRWUKXSG) (N)", UPPER), "V");
        MovingRotor m4 = new MovingRotor("IV", new Permutation("(AEPLIYWCO"
                + "XMRFZBSTGJQNH) (DV) (KU)", UPPER), "J");
        String[] insert = {"B", "BETA", "III", "IV", "I"};

        rotors.add(r);
        rotors.add(f);
        rotors.add(m1);
        rotors.add(m2);
        rotors.add(m3);
        rotors.add(m4);
        a.insertRotors(insert);
        a.setRotors("AZCD");


        assertEquals(0, ((Rotor) a.rotorsNew()[1]).setting());
        assertEquals(25, ((Rotor) a.rotorsNew()[2]).setting());
        assertEquals(3, ((Rotor) a.rotorsNew()[4]).setting());
    }

    @Test
    public void convertTest() {
        Reflector r = new Reflector("B", new Permutation("(AE) (BN) (CK) "
                + "(DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", UPPER));
        FixedRotor f = new FixedRotor("BETA", new Permutation("(ALBEVF"
                + "CYODJWUGNMQTZSKPR) (HIX)", UPPER));
        MovingRotor m1 = new MovingRotor("I", new Permutation("(AELTPHQXRU)"
                + " (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", UPPER), "Q");
        MovingRotor m2 = new MovingRotor("II", new Permutation("(FIXVYOMW)"
                + " (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", UPPER), "E");
        MovingRotor m3 = new MovingRotor("III", new Permutation("(ABDHPEJT) "
                + "(CFLVMZOYQIRWUKXSG) (N)", UPPER), "V");
        MovingRotor m4 = new MovingRotor("IV", new Permutation("(AEPLIYWCO"
                + "XMRFZBSTGJQNH) (DV) (KU)", UPPER), "J");

        String[] insert = {"B", "BETA", "I", "II", "III"};



        rotors.add(r);
        rotors.add(f);
        rotors.add(m1);
        rotors.add(m2);
        rotors.add(m3);
        rotors.add(m4);
        a.insertRotors(insert);
        a.setRotors("AAAA");
        a.setPlugboard(new Permutation("", UPPER));
        assertEquals("Wrong convert", "ILBDA", a.convert("HELLO"));




    }
}
