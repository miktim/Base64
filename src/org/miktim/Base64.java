/*
 *  Base64 encoder/decoder. MIT (c) 2019,2021 miktim@mail.ru
 *  Notes:
 *  - encoded as single line;
 *  - decoder removes CRLF.
 */
package org.miktim;

import java.util.Arrays;

public class Base64 {

    private static final String B64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final byte[] B64_BYTES = B64_CHARS.getBytes();

    public static final String encode(byte[] b) {
        byte[] s = new byte[((b.length + 2) / 3 * 4)];
        Arrays.fill(s, (byte) '=');
        int bi = 0;
        int si = 0;
        while (bi < b.length) {
            int k = Math.min(3, b.length - bi);
            int bits = 0;
// for (int shift = 16, end = 8 * (3 - k); shift >= end; shift -= 8) {            
            for (int j = 0, shift = 16; j < k; j++, shift -= 8) {
                bits += ((b[bi++] & 0xFF) << shift);
            }
// for (int shift = 18, end = 6 * (4 - (k + 1)); shift >= end; shift -= 6) {
            for (int j = 0, shift = 18; j <= k; j++, shift -= 6) {
                s[si++] = B64_BYTES[(bits >> shift) & 0x3F];
            }
        }
        return new String(s);
    }

    public static final byte[] decode(String s) {
        s = s.replaceAll("[\r\n]", "").replaceAll("=+$", ""); // remove CRLF and padding
        byte[] b = new byte[(s.length() / 4 * 3)
                + Math.max(s.length() % 4 - 1, 0)]; // 0=0, 1=0, 2=1, 3=2
        int bi = 0;
        int si = 0;
        int bits = 0;
        while (si < s.length()) {
            int ci = B64_CHARS.indexOf(s.charAt(si++));
            if (ci < 0) {
                throw new IllegalArgumentException();
            }
            bits = (bits << 6) + ci;
            if (si % 4 == 0) {
                for (int shift = 16; shift >= 0; shift -= 8) {
                    b[bi++] = (byte) (bits >> shift);
                }
            }
        }
        bits <<= (4 - (si % 4)) * 6;
        for (int shift = 16; bi < b.length; shift -= 8) {
            b[bi++] = (byte) (bits >> shift);
        }
        return b;
    }

}
