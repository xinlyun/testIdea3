package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connection {
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private long lastActTime = 0;

	Connection(String host, int port) throws IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress(host, port));
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	Connection(Socket socket) throws IOException {
		this.socket = socket;
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	public void send(String m) throws IOException {
		lastActTime = System.currentTimeMillis();
		out.write(m.getBytes("utf8"));
		out.flush();
	}
	
	public String receive() throws IOException {
		lastActTime = System.currentTimeMillis();
		BufferedReader is = new BufferedReader(
				new InputStreamReader(in));
		return is.readLine();
	}

	public synchronized void close() throws IOException {
		lastActTime = System.currentTimeMillis();
		ConnectionManager.removeConnection(this);
		if (socket != null)
			socket.close();
		if (in != null)
			in.close();
		if (out != null)
			out.close();
	}

	public synchronized long getLastActTime() {
		return lastActTime;
	}
}