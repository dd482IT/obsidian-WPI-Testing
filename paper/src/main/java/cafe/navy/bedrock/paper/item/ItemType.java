package cafe.navy.bedrock.paper.item;

import cafe.navy.bedrock.paper.item.context.*;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface ItemType {

    @NonNull String id();

    void handleEvent(final @NonNull ItemEvent event);

    @NonNull Item createItem();

}
