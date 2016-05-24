package sketch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public final class ApproximateTopK<T> {

    private final int k;
    private final Map<T, Long> topK;
    private final CountMinSketch<T> countMinSketch;

    public ApproximateTopK(final int k) {
        this.k = k;
        topK = new HashMap<>();
        countMinSketch = new CountMinSketch<>();
    }

    public void add(final T element) {
        countMinSketch.add(element);
        long freq = countMinSketch.freq(element);
        if (topK.size() > k) {
            long minFreq = Long.MAX_VALUE;
            T minEntry = null;
            for (Map.Entry<T, Long> entry: topK.entrySet()) {
                if (minFreq > entry.getValue()) {
                    minFreq = entry.getValue();
                    minEntry = entry.getKey();
                }
            }
            topK.remove(minEntry);
        } else {
                topK.put(element, freq);
        }
    }

    public List<T> getTopK() {
        return Lists.newArrayList(topK.keySet());
    }

    public static void main(String[] args) {
        ApproximateTopK<Integer> c = new ApproximateTopK<>(5);
//        for (int i = 0; i < 1_000_000; i++) {
//            int r = (int)(Math.random() * 100);
//            c.add(r);
//        }

        c.add(1);
        c.add(1);
        c.add(2);
        c.add(2);
        c.add(2);
        c.add(2);
        c.add(3);
        c.add(3);
        c.add(3);
        c.add(3);
        c.add(4);
        c.add(5);
        c.add(6);
        c.add(7);
        c.add(8);
        System.out.println(c.getTopK());
    }

}
