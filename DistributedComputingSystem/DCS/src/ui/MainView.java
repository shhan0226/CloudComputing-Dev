package ui;

import java.awt.Dimension;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import def.viewData;
import management.WorkerResManager;

public class MainView extends JFrame implements ActionListener {

	private static MainView mainView = new MainView();
	// component
	private JPanel jp = null;
	private JButton server_btn;
	private JButton worker_btn;
	private MainMenuBar MenuBar;
	private MainStatusView mainStatusView;
	private WorkerResManager workerResManager;
	private ServerView serverView;
	private WorkerView workerView;

	public MainView() {
		System.out.println("[MainView.java] MainView()");
		this.setContentPane(getContentJPane());
		// 버튼을 컨탠츠팬을 통해 추가하는 코드
		this.setBounds(0, 0, viewData.FRAME_WIDTH, viewData.FRAME_HEIGHT);
		this.setTitle("DCS");
		// 프레임 이름

		initializeComponent();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		this.setResizable(false);
		// 창 크기 재설정 금지
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// x를 누를 때 프로그램 종료
		this.setVisible(true);
		// 화면 보이게 하기
		this.setLocationRelativeTo(null);
		// 모니터 가운데 뜨기

	}

	public static MainView getInstance() {
		return mainView;
	}

	public void initializeComponent() {
		System.out.println("[MainView.java] initializeComponent()");

		server_btn = new JButton("SERVER");
		server_btn.setBounds(100, 50, 400, 100);
		server_btn.addActionListener(this);
		this.add(server_btn);

		worker_btn = new JButton("WORKER");
		worker_btn.setBounds(100, 150, 400, 100);
		worker_btn.addActionListener(this);
		this.add(worker_btn);

		this.MenuBar = MainMenuBar.getInstance();
		this.add(MenuBar);

		mainStatusView = MainStatusView.getInstance();
		workerResManager = WorkerResManager.getInstance();
		workerResManager.update_start();
		this.add(mainStatusView);

		serverView = ServerView.getInstance();
		serverView.setVisible(false);
		this.add(serverView);

		workerView = WorkerView.getInstance();
		workerView.setVisible(false);
		this.add(workerView);

	}

	public JPanel getContentJPane() {

		if (jp == null) {
			jp = new JPanel();
			jp.setLayout(null);
			jp.setSize(viewData.FRAME_WIDTH, viewData.FRAME_HEIGHT);
		}
		return jp;
	}

	public void actionPerformed(ActionEvent click_event) {

		if (click_event.getSource().equals(server_btn)) {
			System.out.println("[MainView.java] Server Button");
			setEnableComponent(false);
			serverView.setVisible(true);
			serverView.ServerViewOn();

		} else if (click_event.getSource().equals(worker_btn)) {
			System.out.println("[MainView.java] Worker Button");
			setEnableComponent(false);
			workerView.setVisible(true);
			workerView.WorkerViewOn();
		}
	}
	
	public void viewOn(){
		
	}

	private void setEnableComponent(boolean mode) {
		server_btn.setVisible(mode);
		worker_btn.setVisible(mode);
		mainStatusView.setVisible(mode);
	}
	
}
