//package base64;

/*
 * Base64Test. MIT (c) 2021 miktim@mail.ru
 * Need jdk8 or newer!
 */

import java.util.Arrays;
import org.miktim.Base64;

public class Base64Test {

    static void log(String s) {
        System.out.println(s);
    }

    public static void main(String args[]) {
        byte[] src = new byte[255];
        for (int i = 0; i < src.length; i++) {
            src[i] = (byte) i;
        }
        for (int i = 0; i <= src.length; i++) {
            byte[] b1 = Arrays.copyOf(src, i);
            String b64 = Base64.encode(b1);

            if (!b64.equals(java.util.Base64.getEncoder().encodeToString(b1))) {
                log("Wrong encode: " + i);
                log(b64);
                return;
            }

            byte[] b2 = Base64.decode(b64);
            if (!Arrays.equals(b1, b2)) {
                log("Wrong decode: " + i);
                log(b64);
                return;
            }
        }
        try {
            Base64.encode(null);
            log("Encode: no NullPointerException");
            return;
        } catch (NullPointerException e) {
        }
        try {
            Base64.decode(null);
            log("Decode: no NullPointerException");
            return;
        } catch (NullPointerException e) {
        }
        log("OK");
    }

}
