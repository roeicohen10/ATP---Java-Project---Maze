package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    private int len;

    public MyCompressorOutputStream(OutputStream o) {
        out = o;
        len = 0;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
        len++;
    }

    public int getLen() {
        return len;
    }

    public void write(byte[] b) throws IOException {
        if (b == null)
            throw new NullPointerException();
        if (b.length < 13)
            throw new ArrayIndexOutOfBoundsException();

        for (int i = 0; i < 12; i++)
            write(b[i]); //write the initial data (sizes and positions)
        if (b[12] != 0)
            write(0); //if maze[0][0] is a wall

        //run on b and check the following conditions
        for (int i = 12, counter = 1; i < b.length; i++) {
            if (i < b.length - 1) {     // is there a next slot
                if (b[i] == b[i + 1]) { // is the next slot the same
                    if (counter == 255) {   // if we passed 255 (max byte value)
                        write(counter);     // write 255
                        counter = 0;
                        write(counter);     // then 0
                    }
                    counter++;              // and keep on counting
                } else {
                    write(counter);         // if the next slot is different then write the counter
                    counter = 1;            //     and start counting the other value
                }
            } else
                write(counter);             // if its the last slot write the counter
        }
    }
}
