package LectureTests;

public class Terminator extends Thread {
    private boolean crush = false;

    public void run() {
        while(true) {
            if(crush) {
                System.out.println("I see crush == true... I will be back!");
                break;
            }
            System.out.print("I am just doing what I was programmed to do...\n");
        }
    }

    public void crush() {
        crush = true;
    }

    public static void main(String[] args) throws InterruptedException {
        Terminator robot = new Terminator();
        long startTime = System.currentTimeMillis();
        robot.start();

        Thread.sleep(5000); // The robot runs 5 seconds
        System.out.println("Crushing the robot...\n");
        robot.crush();
        long runningTime = System.currentTimeMillis() - startTime;
        System.out.println("The running time is " + runningTime/1000 + " seconds.");
    }
}
