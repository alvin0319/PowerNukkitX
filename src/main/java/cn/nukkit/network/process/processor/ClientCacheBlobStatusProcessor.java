package cn.nukkit.network.process.processor;

import cn.nukkit.Player;
import cn.nukkit.PlayerHandle;
import cn.nukkit.network.connection.BedrockSession;
import cn.nukkit.network.process.DataPacketProcessor;
import cn.nukkit.network.protocol.ClientCacheBlobStatusPacket;
import cn.nukkit.network.protocol.ClientCacheMissResponsePacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ClientCacheBlobStatusProcessor extends DataPacketProcessor<ClientCacheBlobStatusPacket> {
    @Override
    public void handle(@NotNull PlayerHandle playerHandle, @NonNull ClientCacheBlobStatusPacket pk) {
        handlePacket(playerHandle.player.getSession(), pk);
    }

    public static void handlePacket(BedrockSession session, ClientCacheBlobStatusPacket pk) {
        Player player = session.getPlayer();

        if (player != null) {
            for (long hash : pk.hitHashes) {
                player.handleBlobHit(hash);
            }
        }

        if (pk.missHashes.length > 0) {
            ClientCacheMissResponsePacket response = new ClientCacheMissResponsePacket();

            if (player != null) {
                for (long hash : pk.missHashes) {
                    byte[] data = player.getBlobData(hash);
                    if (data != null) {
                        response.blobs.add(new ClientCacheMissResponsePacket.CacheBlob(hash, data));
                    }
                }
            }
            session.sendPacket(response);
        }
    }

    @Override
    public int getPacketId() {
        return ProtocolInfo.CLIENT_CACHE_BLOB_STATUS_PACKET;
    }
}
