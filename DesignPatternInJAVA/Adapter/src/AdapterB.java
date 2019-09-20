public class AdapterB {
    private Socket socket;

    public AdapterB(Socket socket) {
        this.socket = socket;
    }
    public void out18V(){
        this.socket.externalSocket();
        System.out.println("out 18V");
    }
}
