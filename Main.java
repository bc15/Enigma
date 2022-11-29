package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Bond Chaiprasit
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine e = readConfig();
        if (!_input.hasNextLine()) {
            throw new EnigmaException("no settings");
        }
        String temp = _input.nextLine();
        while (_input.hasNextLine()) {

            String nextLn = temp;
            if (nextLn.contains("*")) {
                setUp(e, nextLn);
                nextLn = _input.nextLine();
                while (!nextLn.contains("*")) {
                    String result = "";
                    String [] nextS = nextLn.split(" ");
                    for (String a : nextS) {
                        result = result + e.convert(a);
                    }
                    printMessageLine(result);

                    if (_input.hasNextLine()) {
                        _output.println();
                        temp = nextLn = _input.nextLine();
                    } else {
                        _output.println();
                        break;
                    }

                }
            } else {
                throw new EnigmaException("Wrong format need asterik");
            }
        }
        if (temp.contains("*")) {
            setUp(e, temp);
        }








    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            if (!_config.hasNext()) {
                throw new EnigmaException("no config");
            }
            String alpha = _config.next();
            if (alpha.contains("(")) {
                throw new EnigmaException("not an alphabet");
            }
            char [] alph = alpha.toCharArray();

            _alphabet = new Alphabet(alpha);
            if (!_config.hasNextInt()) {
                throw new EnigmaException("Need int");
            }
            int numR = _config.nextInt();
            if (!_config.hasNextInt()) {
                throw new EnigmaException("Need int");
            }
            int numP = _config.nextInt();
            rame = _config.next();
            while (_config.hasNext()) {
                rName = rame;
                state = _config.next();
                Rotor r = readRotor();
                _rotors.add(r);
            }

            return new Machine(_alphabet, numR, numP, _rotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {

            String permutation = "";

            rame = _config.next();
            while (_config.hasNext() && rame.contains(")")
                    && rame.contains("(")) {
                permutation = permutation + rame + " ";
                rame = _config.next();
            }
            if (!_config.hasNext()) {
                permutation = permutation + rame;
            }

            if (state.charAt(0) == 'M') {
                return new MovingRotor(rName, new Permutation(permutation,
                        _alphabet), state.substring(1));
            } else if (state.charAt(0) == 'N') {
                return new FixedRotor(rName,
                        new Permutation(permutation, _alphabet));
            } else if (state.charAt(0) == 'R') {
                return new Reflector(rName,
                        new Permutation(permutation, _alphabet));
            } else {
                throw new EnigmaException("not valid rotor");
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }



    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        int num = M.numRotors();

        String [] sets = settings.split(" ");
        String [] rotorsNames = new String[num];
        if (!sets[0].contains("*")) {
            throw new EnigmaException("need asterik in front");
        }
        for (int i = 1; i < num + 1; i++) {
            if (!rIn(sets[i])) {
                throw new EnigmaException("Rotor not in collection");
            }
        }
        int numRotates = 0;
        for (int c = 1; c < M.numRotors() + 1; c++) {
            if (M.rNew(sets[c]).rotates()) {
                numRotates++;
            }
        }
        if (M.numPawls() < numRotates) {
            throw new EnigmaException("too many moving rotors");
        }

        for (int i = 1; i < num + 1; i++) {
            rotorsNames[i - 1] = sets[i];
        }

        M.insertRotors(rotorsNames);
        if (num > sets.length - 2) {
            throw new EnigmaException("setting too short");
        }

        if (!((Rotor) M.rotorsNew()[num - 1]).rotates()) {
            throw new EnigmaException("last must rotate ");

        }
        if (!((Rotor) M.rotorsNew()[0]).reflecting()) {
            throw new EnigmaException("first must reflect");

        }
        M.setRotors(sets[num + 1]);

        String cycles = "";

        for (int i = num + 2; i < sets.length; i++) {
            cycles = cycles + sets[i];
        }
        Permutation p = new Permutation(cycles, _alphabet);
        M.setPlugboard(p);



    }
    /** Check of rotor name is in _rotors. @return is boolean.
     * @param name is name. */
    boolean rIn(String name) {
        for (Rotor r: _rotors) {
            if (r.name().equals(name)) {
                return true;
            }

        }
        return false;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {

        for (int i = 0; i < msg.length(); i += 5) {
            _output.print(msg.substring(i,
                    Math.min((i + 5), msg.length())) + " ");


        }

    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** State of current rotor we are at. */
    private String state;
    /** Collection of rotors as array. */
    private Collection<Rotor> _rotors = new ArrayList<>();
    /** Name of the rotor we are adding. */
    private String rName;
    /** temporary variable to store cycles and name. */
    private String rame;
}
