package cafe.navy.bedrock.paper.target;

import cafe.navy.bedrock.paper.message.Message;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface MessageTarget extends Target {

    void sendMessage(final @NonNull Message message);

}
