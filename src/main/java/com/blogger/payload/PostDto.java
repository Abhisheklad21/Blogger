package com.blogger.payload;

//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostDto {
    private long id;

    @NotEmpty
    @Size(min= 4, message = "Description should be at least 4 characters!")
    private String description;
    @NotEmpty
    @Size(min= 4, message = "Description should be at least 4 characters!")
    private String content;
    @NotEmpty
    @Size(min =2, message="Title should be at least 2 characters!")
    private String title;
}
