import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class Worker implements Runnable {

	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
	public Worker (Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		String msg;
		String req;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			msg = in.readLine();
			
			// Estrae l'entry relativa all'oggetto HTTP richiesto
			req = msg.split(" ")[1];
			req = req.substring(1);
			System.out.println(msg);
			
			// Apertura FileChannel con try-with-resources
			try(FileChannel fc = FileChannel.open(Paths.get(req), StandardOpenOption.READ)){
				// Utilizzo di un BufferedOutputStream per l'invio di dati binari
				BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream());
				
				String contentType = extractContentType(req);
				int contentLength = (int) fc.size();
				
				ByteBuffer content = ByteBuffer.allocate(contentLength);
				fc.read(content);
				
				// Scrittura risposta HTTP con flush dei buffer per assicurare l'invio
				out.write("HTTP/1.1 200 OK\n");
				out.write("Server: Java HTTP Poor man's server\n");
				out.write("Date: "+ new Date()+"\n");
				out.write("Content-Type: "+contentType+"\n");
				out.write("Content-Length: "+content.array().length+"\n\n");
				out.flush();
				dataOut.write(content.array(), 0, contentLength);
				dataOut.flush();
				

			} catch(NoSuchFileException e) {
				out.write("HTTP/1.1 404 File Not Found\n");
				out.write("Server: Java HTTP Poor man's server\n");
				out.write("Date: "+ new Date()+"\n");
				out.flush();
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String extractContentType(String req) {
		if(req.endsWith(".txt")) {
			return "text/plain";
		} else if(req.endsWith(".html") || req.endsWith(".htm")) {
			return "text/html";
		} else if(req.endsWith(".jpg") || req.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if(req.endsWith(".png")) {
			return "image/png";
		} else if(req.endsWith(".gif")) {
			return "image.gif";
		}
		
		return "text/plain";
	}
	

}
