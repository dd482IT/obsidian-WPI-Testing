package cafe.navy.bedrock.paper.exception;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ClientEntityException extends Exception {

    public ClientEntityException(final @NonNull String ex) {
        super(ex);
    }

    public ClientEntityException(final @NonNull Exception ex) {
        super(ex);
    }

}


