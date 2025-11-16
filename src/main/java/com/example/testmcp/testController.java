package com.example.testmcp;


import com.example.testmcp.entity.Blog;
import com.example.testmcp.repositories.BlogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {

    private final BlogRepository blogRepository;

    public testController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @GetMapping("")
    public List<Blog> getAll() {
        return blogRepository.findAll();
    }
}
