import java.util.concurrent.Semaphore;

public class Calc extends Thread {

    private double[] total_pi;
    private long inicio;
    private long fim;
    Semaphore mutex;
    Semaphore lock;


    public Calc(Semaphore mutex, Semaphore lock, double[] total_pi, long inicio, long fim) {
        this.total_pi = total_pi;
        this.inicio = inicio;
        this.fim = fim;
        this.mutex = mutex;
        this.lock = lock;
    }

    public void run(){

        double pi = 0;
        double num =1.0;

        //System.out.println("Thread "+this.getName()+" iniciada");

        for (long i = inicio; i < fim; ++i) {

            pi += num/(2*i+1);
            num = -num;
        }

        while(!mutex.tryAcquire()){
            continue;
        }

        total_pi[0] += pi;

        mutex.release();
        lock.release(1);

        //System.out.println("Thread "+this.getName()+" finalizada");
        

    }
    
}
