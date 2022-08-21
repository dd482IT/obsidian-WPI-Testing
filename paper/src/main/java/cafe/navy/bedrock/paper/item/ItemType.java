package cafe.navy.bedrock.paper.item;

import cafe.navy.bedrock.paper.item.context.HotbarEquipContext;
import cafe.navy.bedrock.paper.item.context.ItemPlaceContext;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface ItemType {

    @NonNull String id();

    default void handleItemPlace(final @NonNull ItemPlaceContext context) {

    }

    default void handleHotbarEquip(final @NonNull HotbarEquipContext context) {

    }

    @NonNull Item createItem();

}
