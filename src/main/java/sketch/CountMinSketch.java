package sketch;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public final class CountMinSketch<T> {

    private final static HashFunction H1 = Hashing.adler32();
    private final static HashFunction H2 = Hashing.sha1();
    private final static HashFunction H3 = Hashing.murmur3_32();
    private final static HashFunction H4 = Hashing.md5();
    private final static HashFunction H5 = Hashing.crc32();
    private final static HashFunction H6 = Hashing.sipHash24();
    private final static HashFunction H7 = Hashing.sha512();

    private final static HashFunction[] H_FUNC = {H1, H2, H3, H4, H5, H6, H7};
    private final static long[][] TABLES = new long[H_FUNC.length][100];

    public long freq(T element) {
        long freq = Integer.MAX_VALUE;
        for (int i = 0; i < H_FUNC.length; i++) {
            int h = H_FUNC[i].newHasher().putInt(element.hashCode()).hash().asInt();
            freq = Math.min(TABLES[i][Math.abs(h % TABLES[i].length)], freq);
        }
        return freq;
    }

    public void add(T element) {
        for (int i = 0; i < H_FUNC.length; i++) {
            int h = H_FUNC[i].newHasher().putInt(element.hashCode()).hash().asInt();
            TABLES[i][Math.abs(h % TABLES[i].length)]++;
        }
    }

    public static void main(String[] args) {
        CountMinSketch<Integer> c = new CountMinSketch<Integer>();
        for (int i = 0; i < 1_000_000; i++) {
            int r = (int)(Math.random() * 100);
            c.add(r);
        }
        System.out.println(c.freq(1));
        System.out.println(c.freq(2));
        System.out.println(c.freq(3));
        System.out.println(c.freq(4));

    }
}
