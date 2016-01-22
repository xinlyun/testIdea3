package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class TestServer  extends Thread{
	
	ServerSocket ss;
	public ArrayList<SimpleProcessor> simpleProcessors ;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void main() throws IOException {

		if(ss==null) {
			simpleProcessors = new ArrayList<SimpleProcessor>();
			ss = new ServerSocket(65430);
		}
		Socket s = ss.accept();
		SimpleProcessor simpleProcessor = new SimpleProcessor(s);
		simpleProcessor.start();
		simpleProcessors.add(simpleProcessor);
		main();
	}
	public void send(String cmd){
		for(SimpleProcessor s:simpleProcessors){
			s.send(cmd);
		}
	}

	class SimpleProcessor extends Thread {
		private Socket socket;
		private OutputStream out;
		private InputStream in;
		private volatile boolean running = true;

		public SimpleProcessor(Socket s) throws IOException {
			this.socket = s;
			in = s.getInputStream();
			out = socket.getOutputStream();
//			out = s.getOutputStream();
		}

		public void run() {
//			while (running) {
//				try {
//					BufferedReader is = new BufferedReader(
//							new InputStreamReader(in));
//					String tmp = is.readLine();
//					System.out.println("" + tmp);
////					send("ok\n");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			try {
//				if (socket != null) {
//					socket.close();
//					simpleProcessors.remove(this);
//				}
//			} catch (IOException e) {
//			}
		}

		public void send(String m)  {
			try {

				out.write(m.getBytes());
				out.flush();
//				out.close();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				try {
					if(socket!=null)socket.close();
					running = false;
					simpleProcessors.remove(this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					if(socket!=null)socket.close();
					running = false;
					simpleProcessors.remove(this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		}
	}
}
