package cn.nukkit.network.process;

public enum SessionState {
    START(),

    LOGIN(),

    RESOURCE_PACK(),

    ENCRYPTION(),

    CLIENT_CACHE(),

    PRE_SPAWN(),

    IN_GAME(),

    DEATH()
}
