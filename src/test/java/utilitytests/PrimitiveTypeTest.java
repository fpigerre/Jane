package utilitytests;

import io.github.psgs.jane.utilities.NumberToWords;
import io.github.psgs.jane.utilities.StringUtils;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.mockito.Mockito.*;

public class PrimitiveTypeTest {

    TestVerify verify = mock(TestVerify.class);

    @Test
    public void checkNumberToWord() {
        for (int i = 1; i < 1000; i++) {
            switch (i) {
                case 1:
                    assert NumberToWords.convert(i).equalsIgnoreCase("one");
                    break;
                case 5:
                    assert NumberToWords.convert(i).equalsIgnoreCase("five");
                    break;
                case 22:
                    assert NumberToWords.convert(i).equalsIgnoreCase("twenty two");
                    break;
                case 563:
                    assert NumberToWords.convert(i).equalsIgnoreCase("five hundred sixty three");
                    break;
                case 847:
                    assert NumberToWords.convert(i).equalsIgnoreCase("eight hundred forty seven");
                    break;
            }
        }
    }

    @Test
    public void checkSuffixing() {
        for (int i = 0; i <= 20; i++) {
            int randomInt = new Random().nextInt(999);
            char[] randomArray = String.valueOf(randomInt).toCharArray();
            String suffixedInt = StringUtils.attachSuffix(randomInt);

            switch (randomArray[randomArray.length - 1]) {
                case 1:
                    if (suffixedInt.endsWith("st")) verify.testVerify();
                case 2:
                    if (suffixedInt.endsWith("nd")) verify.testVerify();
                case 3:
                    if (suffixedInt.endsWith("rd")) verify.testVerify();
                default:
                    if (suffixedInt.endsWith("th")) verify.testVerify();
            }
        }
        verify(verify, atLeast(12)).testVerify();
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}

@Ignore
class TestVerify {
    public void testVerify() {
    }
}
