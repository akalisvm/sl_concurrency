package LectureTests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    /* A thread safe counter made by CAS (Compare And Swap) */
    private void safeCount() {
        for(;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if(suc) break;
        }
    }

    /* A non thread safe counter */
    private void count() {
        i++;
    }

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    cas.count();
                    cas.safeCount();
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
        // Waiting for all threads complete
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("non thread safe counter result: " + cas.i);
        System.out.println("CAS thread safe counter result: " + cas.atomicI.get());
        System.out.println("Time consumed: " + (System.currentTimeMillis() - start) + "ms");
    }
}


