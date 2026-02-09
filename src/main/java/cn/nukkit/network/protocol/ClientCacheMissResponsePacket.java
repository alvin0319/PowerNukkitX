package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClientCacheMissResponsePacket extends DataPacket {
    public List<CacheBlob> blobs = new ArrayList<>();

    @Override
    public void decode(HandleByteBuf byteBuf) {
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(blobs.size());
        for (CacheBlob blob : blobs) {
            byteBuf.writeLongLE(blob.hash);
            byteBuf.writeByteArray(blob.payload);
        }
    }

    @Override
    public int pid() {
        return ProtocolInfo.CLIENT_CACHE_MISS_RESPONSE_PACKET;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }

    public static class CacheBlob {
        public long hash;
        public byte[] payload;

        public CacheBlob(long hash, byte[] payload) {
            this.hash = hash;
            this.payload = payload;
        }
    }
}