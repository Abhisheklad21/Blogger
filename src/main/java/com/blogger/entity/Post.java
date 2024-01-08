package com.blogger.entity;

//import jakarta.persistence.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private String content;
    private String title;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Comment> comments= new ArrayList<>();
}
