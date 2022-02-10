import java.util.Arrays;
import java.lang.Thread;

public class Main {
    static final int size = 2000000;
    static final int half = size/2;

    public static void first() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] + Math.sin(0.2f + i/5) + Math.cos(0.2f + i/5) + Math.cos(0.2f + i/2));
        }
        System.out.println("Время работы 1 метода: "+(System.currentTimeMillis()-a));
    }
    public static void second() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        float[] a1 = new float[size];
        float[] a2 = new float[size];
        long a = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    for (int i = 0; i < half; i++)
                        a1[i] = (float)(a1[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/5));
                }
            });
        thread.run();
        for (int i = 0; i < half; i++) {
            int j = i + half;
            a2[i] = (float)(a2[i] * Math.sin(0.2f + j/5) * Math.cos(0.2f + j/5) * Math.cos(0.4f + j/5));
        }
        synchronized (thread) {
            System.arraycopy(a1, 0, arr, 0, half);
            System.arraycopy(a2, 0, arr, half, half);
            System.out.println("Время работы 2 метода: "+(System.currentTimeMillis()-a));
        }
    }
    public static void main(String[] args) {
        first();
        second();
    }
}