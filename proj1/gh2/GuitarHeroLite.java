package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHeroLite {
    public static final double CONCERT_A = 440.0;
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static GuitarString[] GuitarStringArray = new GuitarString[37];

    public static void main(String[] args) {
        CreateGuitarString();
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                try {
                    GuitarStringArray[keyboard.indexOf(key)].pluck();
                } catch (ArrayIndexOutOfBoundsException e){
                    continue;
                }

            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString x: GuitarStringArray
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
    private static void CreateGuitarString(){
        for (int i = 0; i < GuitarStringArray.length ; i++) {
            GuitarStringArray[i] = new GuitarString(CONCERT_A * Math.pow(2, (i-24) / 12.0));
        }
    }
}

