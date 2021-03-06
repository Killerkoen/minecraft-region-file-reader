package mca.io;

import java.util.Arrays;

/**
 * A {@code ChunkLocationTable} holds the references to all ChunkTimestamp objects.
 * @author Killerkoen
 */
public class ChunkTimestampTable {
    /**
     * Array of chunk timestamps.
     */
    private final ChunkTimestamp[] chunkTimestamps;

    /**
     * Constructs an instance of a {@code ChunkTimestampTable} object from the given array of bytes that holds the data
     * of all chunk timestamps.
     * @param bytes an array of bytes to convert to chunk timestamps
     */
    public ChunkTimestampTable(byte[] bytes) {
        final int numberOfTimestamps = 1024;
        final int bytesPerTimestamp = 4;
        this.chunkTimestamps = new ChunkTimestamp[numberOfTimestamps];

        for (int i = 0, j = 0; i < numberOfTimestamps; i++, j += bytesPerTimestamp) {
            byte[] timestampBytes = Arrays.copyOfRange(bytes, j, j + bytesPerTimestamp);
            this.chunkTimestamps[i] = new ChunkTimestamp(timestampBytes);
        }
    }

    /**
     * Get the number of timestamps in this table.
     * @return The number of timestamps in this table
     */
    public int getNumberOfChunkTimestamps() {
        return this.chunkTimestamps.length;
    }

    /**
     * Get a chunk timestamp at the given index.
     * @param i index of the target chunk timestamp
     * @return The chunk timestamp that was found at the given index
     */
    public ChunkTimestamp getChunkTimestampAtIndex(int i) {
        return this.chunkTimestamps[i];
    }
}
