package chat_server;

public class StartConnection {
	public static void main(String[] args) {
		TestConnection test = new TestConnection("127.0.0.1", 3520, "Jonas");
		TestConnection test2 = new TestConnection("127.0.0.1", 3520, "Anton");
		TestConnection test3 = new TestConnection("127.0.0.1", 3520, "Mårten");
		TestConnection test4 = new TestConnection("127.0.0.1", 3520, "Jerry");
	}
}
