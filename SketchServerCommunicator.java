import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) throws IOException {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg message to send
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");

			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);


			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE
			Sketch sketch = server.getSketch();
			server.broadcast(sketch.toString());

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
            String line; Shape shape;
           while((line = in.readLine())!= null || !sketch.isEmpty()){
			   assert line != null;
			   String[] details = line.split("\\s");

               // communicates a draw action to the master server
			   if(details[0].equals("draw")){
				   sketch.addShape(Integer.parseInt(details[1]), details[2], Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]), Integer.parseInt(details[6]), Integer.parseInt(details[7]));
			   }

			   // communicates a recolor action to the master server
			   if(details[0].equals("recolor")){
					sketch.recolorShape(Integer.parseInt(details[1]), Integer.parseInt(details[2]), Integer.parseInt(details[3]), Integer.parseInt(details[4]));
			   }

			   // communicates a delete action to the master server
			   if(details[0].equals("delete")){
			   	sketch.removeShape(Integer.parseInt(details[1]));
			   }

			   // communicates a move action to the master server
			   if(details[0].equals("move")){
			   	sketch.moveShape(Integer.parseInt(details[1]), Integer.parseInt(details[2]), Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]));
			   }

			   // communicates a dragging-drawing action to the master server
			   if(details[0].equals("drag-draw")){
			   	sketch.setShapeCorners(details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]), Integer.parseInt(details[6]));
			   }
			   server.broadcast(line);

           }

			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
