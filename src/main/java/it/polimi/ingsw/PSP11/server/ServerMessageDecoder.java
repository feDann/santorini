package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.messages.*;

public class ServerMessageDecoder {

    public static void decodeMessage(Server server, ClientSocketConnection clientSocket,Message message) {

        if (message instanceof NicknameMessage) {
            String tmpNickname = message.getMessage();
            if(!server.insertInWaitingList(clientSocket,tmpNickname)){
                clientSocket.send(new DuplicateNicknameMessage());
            }else{
                clientSocket.setNickname(tmpNickname);
                clientSocket.send(new ConnectionMessage());
            }
        }
        else if(message instanceof PlayerSetupMessage){
            int numOfPlayers = Integer.parseInt(message.getMessage());
            //choose the correct method
            if(numOfPlayers == 2){
                server.lobbyForTwoPlayer(clientSocket.getNickname(), clientSocket);
            }else{ //in this case numOfPlayers == 3
                server.lobbyForThreePlayer(clientSocket.getNickname(), clientSocket);
            }
        }
        else{
            clientSocket.notify(message);
        }


    }
}
