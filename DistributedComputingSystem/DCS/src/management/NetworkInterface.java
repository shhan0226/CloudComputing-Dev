package management;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkInterface extends Thread {
	private ServerResManager serverResManager;
	ObjectInputStream ois;
	ServerSocket serverSocket;
	private int PORT;
	boolean flag;
	ArrayList<WorkerSocket> workerSocketList = new ArrayList<>();

	public NetworkInterface() {
		System.out.println("[WorkersInterface.java] WorkersInterface()");
		flag = true;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	public void serverCreate() {

		try {
			System.out.println("[NetworkInterface.java] serverCreate()");
			serverSocket = new ServerSocket(this.PORT);
			System.out.println("[NetworkInterface.java] (Port:" + Integer.toString(PORT) + ")");

		} catch (Exception e) {
			// TODO: handle exception
			try {
				if (serverSocket != null)
					serverSocket.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("[NetworkInterface.java] Server Socket Err");
				e2.printStackTrace();
			}
			System.out.println("[NetworkInterface.java] Server Socket Close");
			e.printStackTrace();
		}
	}

	public void serverStop() {

		if (serverSocket != null && !serverSocket.isClosed()) {

			try {

				if (this.isAlive()) {
					this.interrupt();
				}

				for (int i = 0; i < workerSocketList.size(); i++) {
					WorkerSocket wc = workerSocketList.get(i);
					wc.getOIS().close();
					wc.getSocket().close();
				}

				serverSocket.close();
				serverSocket = null;

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("[NetworkInterface.java] Server Socket Stop");
				e.printStackTrace();
			}
		}

	}

	public void run() {
		while (!this.interrupted()) {
			serverAccept();
		}
	}

	public void serverAccept() {
		try {
			Socket socket = serverSocket.accept();
			System.out.println("[NetworkInterface.java] Server Accept Start");
			WorkerSocket ws = new WorkerSocket(socket);
			workerSocketList.add(ws);
			ws.start();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("[NetworkInterface.java] Server Accept Err");
			e.printStackTrace();
		}
	}

}
