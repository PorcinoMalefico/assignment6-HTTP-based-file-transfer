import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {

	/*
	 * Parametri:
	 * int port : porta sul quale il server si mette in ascolto.
	 * Accetta richieste per file con estensione .txt, .htm, .html, .jpg, .jpeg, .png e .gif
	 */
	public static void main(String[] args) {
		
		int port = Integer.parseInt(args[0]);
		
		ExecutorService pool = Executors.newSingleThreadExecutor();
		
		pool.execute(new Server(port));

	}

}
