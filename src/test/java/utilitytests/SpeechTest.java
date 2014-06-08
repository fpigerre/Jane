package utilitytests;

import io.github.psgs.jane.utilities.AudioUtils;
import org.junit.After;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.mockito.Mockito.validateMockitoUsage;

public class SpeechTest {

    @Test
    public void outputSpeech() {
        try {
            AudioUtils.talk("This is a test!");
        } catch (FileNotFoundException ex) {
            System.out.println("A result couldn't be spoken!!");
        }
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
