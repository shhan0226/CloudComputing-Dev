package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import def.Worker;
import def.viewData;
import management.ServerResManager;

public class ServerView extends JPanel implements ActionListener {

	private static ServerView serverView = new ServerView();
	private ServerResManager serverResManager;
	private MainStatusView mainStatusView;

	private JButton server_on, server_off, refreash_btn;
	private JLabel serverIp_lb, serverPort_lb;
	private JTextField serverIP_tf, serverPort_tf;
	private DefaultTableModel model;
	private JTable WorkerTable;
	private JScrollPane Tscroll;
	private String[] header = new String[] { "Name", "IP", "CPU_U(%)", "CPU_C(ms)", "Mem_U(GB)", "Mem_C(GB)" };

	ArrayList<Worker> workerList;

	String arr[];
	ServerTableView serverTableView;
		
	public ServerView() {
		System.out.println("[ServerView.java] ServerView()");
		this.setLayout(null);
		this.setLocation(2, 25);
		this.setSize(viewData.SUB_WIDTH, viewData.SUB_HEIGHT);

		initializeComponent();
	}

	public void initializeComponent() {
		System.out.println("[ServerView.java] initializeComponent()");
		serverResManager = ServerResManager.getInstance();
		mainStatusView = MainStatusView.getInstance();

		serverIp_lb = new JLabel("Server IP : ");
		serverIp_lb.setBounds(5, 5, 110, 25);
		serverIp_lb.setVisible(true);
		this.add(serverIp_lb);

		serverPort_lb = new JLabel("Server Port : ");
		serverPort_lb.setBounds(175, 5, 110, 25);
		this.add(serverPort_lb);

		serverPort_tf = new JTextField("32000");
		serverPort_tf.setBounds(245, 5, 70, 25);
		serverPort_tf.setFocusable(true);
		this.add(serverPort_tf);

		server_on = new JButton("Server On");
		server_on.setBounds(410, 5, 90, 25);
		server_on.addActionListener(this);
		server_on.setFocusable(false);
		this.add(server_on);

		server_off = new JButton("Server Off");
		server_off.setBounds(500, 5, 90, 25);
		server_off.addActionListener(this);
		server_off.setFocusable(false);
		server_off.setEnabled(false);
		this.add(server_off);

		refreash_btn = new JButton("Refreash");
		refreash_btn.setBounds(0, 256, 590, 25);
		refreash_btn.addActionListener(this);
		this.add(refreash_btn);

		model = new DefaultTableModel(header, 0);
		WorkerTable = new JTable(model);
		Tscroll = new JScrollPane(WorkerTable);
		Tscroll.setBounds(0, 30, 590, 230);
		this.add(Tscroll);

		serverIP_tf = new JTextField(serverResManager.getServerIP().getHostAddress());
		serverIP_tf.setBounds(60, 5, 110, 25);
		serverIP_tf.setFocusable(false);
		serverIP_tf.setEnabled(false);
		this.add(serverIP_tf);
	}

	public static ServerView getInstance() {
		return serverView;
	}
	
	public void ServerViewOn(){				
		mainStatusView.setVisible(true);
		mainStatusView.setLocation(200, 330);		
	}
	
	public void ServerViewOff(){				
		serverIp_lb.setVisible(false);
		serverPort_lb.setVisible(false);
		serverPort_tf.setVisible(false);
		server_on.setVisible(false);
		server_off.setVisible(false);
		refreash_btn.setVisible(false);
		Tscroll.setVisible(false);
		serverIP_tf.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(server_on)) {
			System.out.println("[ServerView.java] Server Button");
			server_on.setEnabled(false);
			server_off.setEnabled(true);
			serverPort_tf.setEnabled(false);

			serverResManager.serverStart(serverPort_tf.getText());
			serverTableView = new ServerTableView(model);
			serverTableView.start();

		} else if (e.getSource().equals(server_off)) {
			System.out.println("[ServerView.java] Cancel Button");
			server_on.setEnabled(true);
			server_off.setEnabled(false);
			serverPort_tf.setEnabled(true);

			serverResManager.serverStop();
			serverTableView.stoped();			

		} else if (e.getSource().equals(refreash_btn)) {
			System.out.println("[ServerView.java] Refreash Button");
			model.setNumRows(0);
			serverResManager = ServerResManager.getInstance();
			workerList = serverResManager.getWorkerList();
			for (int i = 0; i < workerList.size(); i++) {
				String str_arr[] = { workerList.get(i).getHOST_NAME(), workerList.get(i).getIP(),
						workerList.get(i).getCPU_USAGE(), workerList.get(i).getCPU_CAPA(),
						workerList.get(i).getMEMORY_USAGE(), workerList.get(i).getMEMORY_CAPA() };

				model.addRow(str_arr);
			}

		}

	}

}
