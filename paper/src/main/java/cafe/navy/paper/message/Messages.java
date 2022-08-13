package cafe.navy.paper.message;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Messages {

    @NonNull Message NO_PLAYER = Message
            .create()
            .text("Hey! ")
            .text("Only players can use this command.");

    @NonNull Message BROADCAST_STRETCH = Message
            .create()
            .text("Don't forget to stretch!", Colours.Light.BLUE)
            .newline()
            .text("Your body will thank you :^)");

}
