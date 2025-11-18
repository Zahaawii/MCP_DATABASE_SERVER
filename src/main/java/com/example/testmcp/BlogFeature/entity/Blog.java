package com.example.testmcp.BlogFeature.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "blog")

public class Blog {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long blogId;

    private String subject;

    @Lob
    private String body;

    private String category;

    private Date publishDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserInfo userInfo;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comments> comments;

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", category='" + category + '\'' +
                ", publishDate=" + publishDate +
                ", user=" + userInfo +
                '}';
    }
}
