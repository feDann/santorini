package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.messages.*;

/**
 * Class for decode message sent by the client
 */
public class ServerMessageDecoder {
    /**
     * Called by {@link ClientSocketConnection#run()}
     * Decode the message sent by the client and, if it's possible call {@link ClientSocketConnection#send(Object)}, otherwise {@code notify()} the message to the {@link it.polimi.ingsw.PSP11.controller.Controller}
     * @param server the server
     * @param clientSocket the {@link ClientSocketConnection} object of the client
     * @param message the message sent by the client
     */

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
