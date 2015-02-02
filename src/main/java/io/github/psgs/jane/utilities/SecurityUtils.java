package io.github.psgs.jane.utilities;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;

public class SecurityUtils {

    /**
     * Securely deletes a file. When the program is closed, no remaining data should expose the contents of the file
     *
     * @param file A file to delete
     * @throws IOException
     */
    public static void secureDelete(File file) throws IOException {
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
    }
}
