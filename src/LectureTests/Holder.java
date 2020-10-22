package LectureTests;

public class Holder {
    private int n;

    private Holder(int n) {
        this.n = n;
    }

    private void my_test() {
        if(n != n) {
            throw new ExceptionInInitializerError("This statement is false!"); // This message will not be printed
        }
    }

    public static void main(String[] args) {
        Holder holder = new Holder(42);
        holder.my_test();
    }
}
