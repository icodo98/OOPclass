package gh2;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHeroLite {
    public static final double CONCERT_A = 440.0;
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final int keyboardSize = 37;
    public static void main(String[] args) {
        Instruments[] InstrumentsArray = new Instruments[0];
        //InstrumentsArray = CreateGuitarString(InstrumentsArray);
        //InstrumentsArray = CreateHarpString(InstrumentsArray);
        InstrumentsArray = CreateDrums(InstrumentsArray);
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                try {
                    InstrumentsArray[keyboard.indexOf(key)].pluck();
                } catch (ArrayIndexOutOfBoundsException e){
                    continue;
                }

            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (Instruments x: InstrumentsArray
                 ) {
                sample = x.sample() + sample;
                x.tic();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

        }
    }

    /**
     * Create GuitarString Array with 37 elements with right frequency.
     */
    private static Instruments[] CreateGuitarString(Instruments[] a){
        a = new GuitarString[keyboardSize];
        for (int i = 0; i < a.length ; i++) {
            a[i] = new GuitarString(CONCERT_A * Math.pow(2, (i-24) / 12.0));
        }
        return a;
    }
    private static Instruments[] CreateHarpString(Instruments[] a){
        a = new HarpStrings[keyboardSize];
        for (int i = 0; i < a.length ; i++) {
            a[i] = new HarpStrings(CONCERT_A * Math.pow(2, (i-24) / 12.0));
        }
        return a;
    }
    private static Instruments[] CreateDrums(Instruments[] a){
        a = new Drums[keyboardSize];
        for (int i = 0; i < a.length ; i++) {
            a[i] = new Drums(CONCERT_A * Math.pow(2, (i-24) / 12.0));
        }
        return a;
    }
}

