import static org.unix.__compar_fn_t.*;
import static org.unix.stdlib_h.*;

import java.lang.foreign.*;
import java.lang.invoke.*;

/**
 * jextract --output classes -t org.unix <absolute path to stdlib.h>
 * of met de source
 * jextract --source --output src -t org.unix <absolute path to stdlib.h>
 * <p>
 * Run: `java --enable-preview --source 19 QsortMain.java`
 */
public class QsortMain {

    public static void main(String[] args) {

        int[] unsortedArray = new int[]{0, 9, 3, 4, 6, 5, 1, 8, 2, 7};

        try (MemorySession session = MemorySession.openConfined()) {

            // Allocate off-heap memory and store unsortedArray in it
            MemorySegment array = session.allocateArray(
                    ValueLayout.JAVA_INT,
                    unsortedArray);

            // Create upcall for comparison function
            MemorySegment comparFunc = allocate(
                    (addr1, addr2) ->
                            Integer.compare(
                                    addr1.get(ValueLayout.JAVA_INT, 0),
                                    addr2.get(ValueLayout.JAVA_INT, 0)),
                    session);

            // Call qsort
            qsort(array, (long) unsortedArray.length, 4L, comparFunc);

            // Dereference off-heap memory
            int[] sortedArray = array.toArray(ValueLayout.JAVA_INT);

            for (int num : sortedArray) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}