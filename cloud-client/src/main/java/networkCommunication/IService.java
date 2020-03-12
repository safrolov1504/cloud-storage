package networkCommunication;

import java.io.IOException;

public interface IService {
    void sendMessage(String message);

    void processRetrievedMessage(String message);

    default void close() throws IOException {

    }

}
