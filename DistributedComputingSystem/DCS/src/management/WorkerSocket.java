package management;

import java.io.ObjectInputStream;
import java.net.Socket;

import def.Worker;

public class WorkerSocket extends Thread {
	Socket worker_Socket;
	boolean flag;
	ObjectInputStream ois;
	ServerResManager serverResManager;
	String deleteName, deleteIP;

	Worker deleteWorker;

	public WorkerSocket(Socket worker_Socket) {
		System.out.println("[WorkerSocket.java] WorkerSocket()");
		this.worker_Socket = worker_Socket;
		flag = true;
	}

	public Socket getSocket() {
		return worker_Socket;
	}

	public ObjectInputStream getOIS() {
		return ois;
	}

	public void run() {

		while (flag) {
			try {
				ois = new ObjectInputStream(worker_Socket.getInputStream());
				Object obj = ois.readObject();
				Worker workerItme = (Worker) obj;
				serverResManager = ServerResManager.getInstance();
				serverResManager.addWorker(workerItme);
				System.out.println("[WorkerSocket.java] " + "Host Name: " + workerItme.getHOST_NAME() + "/ IP: "
						+ workerItme.getIP() + " Connection...");
				deleteName = workerItme.getHOST_NAME();
				deleteIP = workerItme.getIP();

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("[WorkerSocket.java] Worker Interrupt");
				flag = false;				
				e.printStackTrace();
			}

		}
		serverResManager.RemoveWorkerItem(deleteName, deleteIP);
	}

}
