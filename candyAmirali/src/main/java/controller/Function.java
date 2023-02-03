package controller;

import java.util.Random;

/**
 * random func
 */
public class Function {
    private final Random randomobj;

    private int cnt = 0;

    public Function(int seed) {
        randomobj = new Random();
        randomobj.setSeed(seed);
    }

    public int nextInt() {
        cnt++;
        if (cnt % 2 == 0)
            return randomobj.nextInt(3);
        else
            return randomobj.nextInt(3) + 3;
    }
}
