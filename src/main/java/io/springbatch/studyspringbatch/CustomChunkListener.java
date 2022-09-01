package io.springbatch.studyspringbatch;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

public class CustomChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext context) {
        System.out.println("Before Chunk");
    }

    @AfterChunk
    public void afterChunk(ChunkContext context) {
        System.out.println("After Chunk");

    }

    @AfterChunkError
    public void afterChunkError(ChunkContext context) {
        System.out.println("After Chunk Error");

    }
}
