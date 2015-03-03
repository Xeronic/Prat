package chat_server;

public class StartServer {
	public static void main(String[] args){
		PratServer server = new PratServer(3520);
		PratServerController servercontroller = new PratServerController();
	}
}
