package net.eternalempires.mod.common.util;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Singleton
public class NetworkService {

    private String lastServerAddress = null;
}
