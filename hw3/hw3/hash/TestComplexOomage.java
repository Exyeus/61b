package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TO DO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
        List<Integer> argList1 = new ArrayList<>();
        argList1.add(0); // 0b11111111 x 4
        argList1.add(0);
        argList1.add(0);
        argList1.add(7);
        List<Integer> argList2 = new ArrayList<>();
        argList2.add(99); // 1111111111111111
        argList2.add(0); // 0b11111111000000000000000011111111
        argList2.add(0);
        argList2.add(0);
        argList2.add(7);
        List<Integer> argList3 = new ArrayList<>();
        argList3.add(128); // 0b11111111111111111111111111111111
        argList3.add(0);
        argList3.add(0);
        argList3.add(7);
        List<Integer> argList4 = new ArrayList<>();
        argList4.add(0); // 0b1111
        argList4.add(0); // 0b11111111111111111111000011111111
        argList4.add(1);
        argList4.add(1);
        ComplexOomage cmxo1 = new ComplexOomage(argList1);
        ComplexOomage cmxo2 = new ComplexOomage(argList2);
        ComplexOomage cmxo3 = new ComplexOomage(argList3);
        ComplexOomage cmxo4 = new ComplexOomage(argList4);
        deadlyList.add(cmxo1);
        deadlyList.add(cmxo2);
        deadlyList.add(cmxo3);
        deadlyList.add(cmxo4);
        /*
        Thinking: x 256 (2^8) means moving the binary number
        to left by 8 figures, and fill the right with 0.
        Different x's bring the same final answer?
        So, x's leading to the same binary tail sequence satisfy?
        round 1: 0->0->x
        round 2: x_r8 + x
        round 3: (x_r8 + x)r8
        Another way is to choose extremely large x's in smaller number.

        ! Restricted! x must be within [0, 255]!
        But considering the mod arithmetic, perhaps
        I can exploit the 0x7fffffff, which is
        0b01111111111111111111111111111111


         */
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
