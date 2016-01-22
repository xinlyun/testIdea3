package test;

import java.io.IOException;

public class TestClient {

	public static void main(String[] args) throws IOException {
		ConnectionManager.createConnection("localhost", 65430);
	}

}