package cn.nukkit.level.format;

import java.util.List;

/**
 * Chunk container for Client Chunk Cache.
 *
 * <p>This class holds chunk data in various forms:
 * <ul>
 *     <li>blockEntityData: Block entity NBT data (always sent)</li>
 *     <li>blobHashes: Hashes of each blob (for client cache verification)</li>
 *     <li>blobs: Actual sub-chunk data (sent on cache miss)</li>
 *     <li>fullData: Full chunk data (used when cache is disabled)</li>
 * </ul>
 * </p>
 *
 * @param blockEntityData Block entity data (NBT encoded)
 *                        always sent regardless of cache status
 * @param subChunkCount   Number of sub-chunks
 * @param blobHashes      Array of xxhash hashes for each blob
 *                        [sub-chunk 0, sub-chunk 1, ..., biomes] order
 * @param blobs           List of actual blob data
 *                        same order as blobHashes
 * @param fullData        Full chunk data (used when cache is disabled)
 *                        All sub-chunks + biomes + block entities included
 */
public record ChunkDataWithBlobs(byte[] blockEntityData, int subChunkCount, long[] blobHashes, List<byte[]> blobs,
                                 byte[] fullData) {

    public int getBlobCount() {
        return blobHashes.length;
    }

    /**
     * Returns the blob data at the specified index.
     *
     * @param index Index of the blob to retrieve.
     * @return blob data.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public byte[] getBlob(int index) {
        if (index < 0 || index >= blobs.size()) {
            throw new IndexOutOfBoundsException("Blob index " + index + " out of bounds for size " + blobs.size());
        }
        return blobs.get(index);
    }

    /**
     * Finds and returns the blob data by its hash.
     *
     * @param hash Hash of the blob to find.
     * @return blob data if found, null otherwise.
     */
    public byte[] findBlobByHash(long hash) {
        for (int i = 0; i < blobHashes.length; i++) {
            if (blobHashes[i] == hash) {
                return blobs.get(i);
            }
        }
        return null;
    }
}