package net.eternalempires.mod.common.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
@RequiredArgsConstructor
public class EternalEmpiresClient {

    @NotNull
    public static Injector init() {
        log.debug("Client Init.");

        return Guice.createInjector();
    }
}