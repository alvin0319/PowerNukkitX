package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientCacheBlobStatusPacket extends DataPacket {
    public long[] missHashes;
    public long[] hitHashes;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        int missedCount = byteBuf.readUnsignedVarInt();
        int hitCount = byteBuf.readUnsignedVarInt();
        this.missHashes = new long[missedCount];
        this.hitHashes = new long[hitCount];
        for (int i = 0; i < missedCount; i++) {
            this.missHashes[i] = byteBuf.readLongLE();
        }
        for (int i = 0; i < hitCount; i++) {
            this.hitHashes[i] = byteBuf.readLongLE();
        }
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
    }

    @Override
    public int pid() {
        return ProtocolInfo.CLIENT_CACHE_BLOB_STATUS_PACKET;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}