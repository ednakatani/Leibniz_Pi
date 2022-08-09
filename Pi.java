import java.util.concurrent.Semaphore;

public class Pi {

    
    public static void main(String[] args) throws InterruptedException {

        double pi;
        double[] total_pi = new double[1];
        int n_threads = Runtime.getRuntime().availableProcessors();
        long iteracoes = 5_000_000_000L;

        Semaphore mutex = new Semaphore(1);
        Semaphore lock = new Semaphore(-(n_threads-1));

        long t0 = System.nanoTime();

        for (int t = 0; t < n_threads; ++t) {
            Calc c = new Calc(mutex, lock, total_pi, t * iteracoes / n_threads, (t + 1) * iteracoes / n_threads);
            c.start();
        }
       
        lock.acquire();
        pi = 4*total_pi[0];

        long d = System.nanoTime() - t0;

        System.out.println("PI:"+ pi);
        System.out.println("Tempo de execução: " + d/1.0E9D+"s");

    }
}
