package cn.nukkit.network.process.handler;

import cn.nukkit.network.connection.BedrockSession;
import cn.nukkit.network.process.SessionState;
import cn.nukkit.network.protocol.ClientCacheStatusPacket;
import cn.nukkit.network.protocol.PacketHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class ClientCacheStatusHandler implements PacketHandler {

    private BedrockSession session;

    @Override
    public void handle(ClientCacheStatusPacket pk) {
        log.debug("Player {} client cache support status: {}", session.getPeer().getSocketAddress().toString(), pk.isSupported());
        session.setClientCacheSupported(pk.isSupported());
        session.getMachine().fire(SessionState.RESOURCE_PACK);
    }
}
