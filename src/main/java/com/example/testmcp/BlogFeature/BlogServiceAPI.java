package com.example.testmcp.BlogFeature;


import com.example.testmcp.BlogFeature.DTO.BlogDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

/*
Since my blog is uploaded and deployed to the Cloud - we will test this way, instead of setting up the database again.
As the application is delpoyed on a server with the DB, we cannot access the same data, but its also available online
When deployed we will connect it to the backend instead of fetching the data for consistency and perfomance
 */

@Service
public class BlogServiceAPI {



    public List<BlogDTO> getAllBlogPost() {
        final String getAllBlog = "http://www.zaak.dk/api/v1/blog/getallblogpost";
        List<BlogDTO> blog = null;
        try {
            blog = RestClient.builder().build()
                    .get()
                    .uri(getAllBlog)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<BlogDTO>>(){});
        } catch (Exception e) {
            throw new RuntimeException("There was an error fetching the blog data: " + e.getMessage());
        }
        return blog;
    }

    public List<BlogDTO> getAllByAuthor(String author) {
        String getAllBlogByAuthor = "http://www.zaak.dk/api/v1/blog/getbyusername/";
        try {
            return RestClient.builder().build()
                    .get()
                    .uri(getAllBlogByAuthor + author)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<BlogDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("There was a issue fetching the data by an author: " + e.getMessage());
        }

    }
}
