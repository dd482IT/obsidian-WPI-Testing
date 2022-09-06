package cafe.navy.bedrock.core.target;

import cafe.navy.bedrock.core.message.Message;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface MessageTarget extends Target {

    void sendMessage(final @NonNull Message message);

}
