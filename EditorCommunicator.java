import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * Handles communication to/from the server for the editor
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE

			String line;
			Sketch sketch = editor.getSketch();

			if((line = in.readLine()).contains("#")) {
				sketch.fromString(line);
			}

			while((line = in.readLine())!= null){

				String[] details = line.split("\\s");

				if(details[0].equals("draw")){
					editor.id = sketch.addShape(Integer.parseInt(details[1]), details[2], Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]), Integer.parseInt(details[6]), Integer.parseInt(details[7]));
				}

				if(details[0].equals("recolor")){
					sketch.recolorShape(Integer.parseInt(details[1]), Integer.parseInt(details[2]), Integer.parseInt(details[3]), Integer.parseInt(details[4]));
				}

				if(details[0].equals("delete")){
					sketch.removeShape(Integer.parseInt(details[1]));
				}

				if(details[0].equals("move")){
					sketch.moveShape(Integer.parseInt(details[1]), Integer.parseInt(details[2]), Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]));
				}

				if(details[0].equals("draw-drag")){
					sketch.setShapeCorners(details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]), Integer.parseInt(details[6]));
				}

				editor.repaint();
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}

	// Send editor requests to the server
	// TODO: YOUR CODE HERE


}
