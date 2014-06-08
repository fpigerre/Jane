package utilitytests;

import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.security.SecureRandom;
import java.util.Random;

import static org.mockito.Mockito.validateMockitoUsage;

public class SecurityTest {

    @Test
    public void testSecureFileDeletion() {
        try {
            File file = new File("SecurityTest");
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SecurityTest"), "utf-8"));
            StringBuilder stringBuilder = new StringBuilder();
            Random writeRandom = new Random();
            for (int i = 0; i < writeRandom.nextInt(500); i++) {
                stringBuilder.append(writeRandom.nextLong() + "\n");
            }
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();

            assert file.getTotalSpace() > file.getFreeSpace();

            if (file.exists()) {
                long length = file.length();
                SecureRandom random = new SecureRandom();
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
                randomAccessFile.seek(0);
                randomAccessFile.getFilePointer();
                byte[] data = new byte[64];
                int pos = 0;
                while (pos < length) {
                    random.nextBytes(data);
                    randomAccessFile.write(data);
                    pos += data.length;
                }
                randomAccessFile.close();
                file.delete();
            }
        } catch (IOException ex) {
            System.out.println("An IOException occurred while running a SecureFileDeletion test!");
            ex.printStackTrace();
        }
    }

    @After
    public void validate() {
        assert !new File("SecurityTest").exists();
        validateMockitoUsage();
    }
}
