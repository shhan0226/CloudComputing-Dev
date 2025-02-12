package management;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Heartbeat extends Thread {
	private Socket serverSocket;
	ObjectOutputStream oos;

	public Heartbeat(Socket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void run() {
		System.out.println("[Heartbeat.java] run() - Server Heartbeat start !");

		while (true) {

			try {
				System.out.println("[Heartbeat.java] Heartbeat...");
				oos = new ObjectOutputStream(serverSocket.getOutputStream());
				oos.writeObject(WorkerResManager.getInstance().getWorker());
				sleep(3000);

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("[Heartbeat.java] Server Heartbeat Err");
			}

		}

	}

}
