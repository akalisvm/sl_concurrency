// A piece of code will cause dead lock
// Both A and B are waiting for each other to release the lock
/*
Found one Java-level deadlock:
=============================
"Thread-0":
  waiting to lock monitor 0x0000015332ce9e80 (object 0x0000000711e9b8f0, a java.lang.String),
  which is held by "Thread-1"
"Thread-1":
  waiting to lock monitor 0x0000015332ce3f80 (object 0x0000000711e9b8c0, a java.lang.String),
  which is held by "Thread-0"

Java stack information for the threads listed above:
===================================================
"Thread-0":
        at DeadLockDemo$1.run(DeadLockDemo.java:47)
        - waiting to lock <0x0000000711e9b8f0> (a java.lang.String)
        - locked <0x0000000711e9b8c0> (a java.lang.String)
        at java.lang.Thread.run(java.base@13.0.2/Thread.java:830)
"Thread-1":
        at DeadLockDemo$2.run(DeadLockDemo.java:57)
        - waiting to lock <0x0000000711e9b8c0> (a java.lang.String)
        - locked <0x0000000711e9b8f0> (a java.lang.String)
        at java.lang.Thread.run(java.base@13.0.2/Thread.java:830)

Found 1 deadlock.
 */
public class DeadLockDemo {
    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    synchronized (A){
                        System.out.println("2");
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
