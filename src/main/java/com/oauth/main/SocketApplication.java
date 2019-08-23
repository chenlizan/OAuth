package com.oauth.main;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

public class SocketApplication {

    protected static class ClientMsg {
        private String[] terminalPhone;

        public ClientMsg() {
        }

        public ClientMsg(String[] terminalPhone) {
            super();
            this.terminalPhone = terminalPhone;
        }

        public String[] getTerminalPhone() {
            return terminalPhone;
        }

        public void setTerminalPhone(String[] terminalPhone) {
            this.terminalPhone = terminalPhone;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9099);

        final SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println(client);
            }
        });

        server.addEventListener("client_event", ClientMsg.class, new DataListener<ClientMsg>() {
            @Override
            public void onData(SocketIOClient client, ClientMsg data, AckRequest ackSender) throws Exception {
                System.out.println(data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();

    }

}
