import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

	private int port;
	
	ExecutorService pool = Executors.newCachedThreadPool();
	
	public Server(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		// Istanzia il ServSocket sulla porta port
		try(ServerSocket serversocket = new ServerSocket(port)) {
			
			System.out.println("Server listening on port "+port);
			
			while(true) {
				// Aspetta una connessione, la accetta e passa il Socket creato ad un thread Worker
				Socket socket = serversocket.accept();
				pool.execute(new Worker(socket));
			} 
		}catch (IOException e) {
			e.printStackTrace();	
		}
		
	}

}
