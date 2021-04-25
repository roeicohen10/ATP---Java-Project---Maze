package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream inputStream) {
        in = inputStream;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }


    public int read(byte[] b) throws IOException {
        if (b == null)
            throw new NullPointerException();
        if (b.length < 13)
            throw new ArrayIndexOutOfBoundsException();

        int i;
        for (i = 0; i < 12; i++)
            b[i] = (byte) read();

        for (int counter = read() , evenIndicator = -1, wallOrPass; counter != -1; counter = read()) {
            evenIndicator++;
            wallOrPass = (evenIndicator % 2 == 0) ? 0 : 1;
            for (; counter > 0; i++, counter--) {
                b[i] = (byte) wallOrPass;
            }
        }
        return 0;
    }
}
