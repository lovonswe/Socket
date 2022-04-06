/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientHandler extends Thread {

    public String userName;
    public Socket socket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    public BufferedReader inConsole;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        inConsole = new BufferedReader(new InputStreamReader(System.in));

    }

    @Override
    public void run() {
        try {
            userName = dataInputStream.readUTF();
            System.out.println(userName + " Connected");

            new Thread(() -> {
                while (true) {
                    try {
                        receiveMessage();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }).start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void receiveMessage() throws IOException {

        String msgIn = dataInputStream.readUTF();

        System.out.println(msgIn);
    }

}
