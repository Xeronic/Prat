package chat_server;

public class StartServer {
	public static void main(String[] args){
		PratServerController servercontroller = new PratServerController();
		new PratServer(servercontroller, 3520);
	}
}
