package com.example.testmcp.integrationsTest.chroma;


import com.example.testmcp.ChromaDB.ChromaDBConfig;
import com.example.testmcp.mcpService.KBAServiceMCP;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chroma.vectorstore.ChromaApi;
import org.springframework.ai.chroma.vectorstore.ChromaVectorStore;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class KBAIntegrationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    ChromaDBConfig config;

    @Autowired
    KBAServiceMCP MCP;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private ChromaApi chromaApi;

    @Test
    void context() {
        assertNotNull(context);
    }

    @Test
    void addArticleAndDelete() {
        // As we do not want test data stored in our env - Yes we could have a test
        // We combine the test and delete
        String id = "IntegrationTest";
        String content = "Test article for integration test";
        String tagName = "test";
        String tagDescription = "test";
        String expectedAddResult = "Added document to the database";
        String expectedDeleteResult = "Succesfully deleted: "  + id;

        String resultAddArticleToDatabase = MCP.addArticlesToDatabase(id, content, tagName, tagDescription);

        assertNotNull(resultAddArticleToDatabase);
        assertEquals(expectedAddResult, resultAddArticleToDatabase);

        String deleteArticlesFromDatabase = MCP.deleteArticlesFromDatabase(id);

        assertNotNull(deleteArticlesFromDatabase);
        assertEquals(expectedDeleteResult, deleteArticlesFromDatabase);
    }

    @Test
    void restClientBuilderWorks() {
        String header = "X-Chroma-Token";
        String apiToken = System.getenv("CHROMADB_API_KEY");
        String expectedRestClientResult = "web.client";
        String expectedChromaResult = "web.client";

        String restClientConnection = config.restClientBuilder().toString();

        RestClient.Builder chromaAPIConnection = RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory())
                .defaultHeader(header, apiToken);

        //IF the connection doesn't work, we will receive an error, so it is testable like this
        assertNotNull(restClientConnection);
        assertNotNull(chromaAPIConnection);
        assertTrue(restClientConnection.contains(expectedRestClientResult));
        assertTrue(chromaAPIConnection.toString().contains(expectedChromaResult));
    }

    @Test
    void connectToChromaDB() {
        String tenantName = "37f4ff04-8a40-4a5c-ba87-77b9e4b5d60d";
        String databaseNameTest = "Test";
        String colletionNameTest = "test_api";

        assertNotNull(ChromaVectorStore.builder(chromaApi, embeddingModel).tenantName(tenantName)
                .databaseName(databaseNameTest)
                .collectionName(colletionNameTest).initializeSchema(false).build());
    }

}
