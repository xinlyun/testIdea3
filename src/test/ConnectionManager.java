package test;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConnectionManager {

	private volatile static long activeCycle = 1000;


	private static Set<Connection> pool = Collections
			.synchronizedSet(new HashSet<Connection>());


	private static ConnectActiveMonitor monitor = new ConnectActiveMonitor();

	static {
		monitor.start();
	}

	public static Connection createConnection(String host, int port)
			throws IOException {
		Connection conn = new Connection(host, port);
		pool.add(conn);
		return conn;
	}

	public static Connection createConnection(Socket socket) throws IOException {
		Connection conn = new Connection(socket);
		pool.add(conn);
		return conn;
	}

	public static void removeConnection(Connection conn) {
		pool.remove(conn);
	}

	static class ConnectActiveMonitor extends Thread {
		private volatile boolean running = true;

		public void run() {
			while (running) {
				long time = System.currentTimeMillis();
				for (Connection con : pool) {
					try {
						if (con.getLastActTime() + activeCycle < time){
							con.send("ok\n");
							System.out.println(con.receive());
						}
							
					} catch (IOException e) {
						removeConnection(con);
					} catch (Exception e) {
					}
				}
				yield();
			}
		}

		void setRunning(boolean b) {
			running = b;
		}
	}
}