package sketch;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * keep adding element, returns a sample of the added element in which element has the same probability of being returned
 */
public final class SampleSet<T> {

    private final int sampleSize;
    private final List<T> elements;
    private long numberOfElements;

    public SampleSet(final int sampleSize) {
        this.sampleSize = sampleSize;
        elements = new ArrayList<>(sampleSize);
        numberOfElements = 0L;
    }

    public void add(T element) {
        numberOfElements++;
        if (elements.size() < sampleSize) {
            elements.add(element);
            return;
        }
        double r = Math.random();
        double probabilityOfSelection = (sampleSize / new Double(numberOfElements));
        if (r < probabilityOfSelection) {
            int randomIndex = ((int)(Math.random() * 10)) % sampleSize;
            elements.remove(randomIndex);
            elements.add(element);
        }
    }

    public List<T> getSample() {
        return Lists.newArrayList(elements);
    }
}
