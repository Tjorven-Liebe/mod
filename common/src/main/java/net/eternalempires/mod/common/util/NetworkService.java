package net.eternalempires.mod.common.util;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@Singleton
public final class NetworkService {

    @Nullable
    private String lastServerAddress = null;
}
