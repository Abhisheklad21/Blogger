package com.blogger.controller;


import com.blogger.payload.PostDto;
import com.blogger.service.PostService;
import com.blogger.service.impl.PostServiceImpl;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostServiceImpl postSerImp;

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        postService.createPost(postDto);
        return new ResponseEntity<>("Post is created", HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post is Deleted!!!", HttpStatus.OK);
    }

    //http://localhost:8080/api?pageNo=0&pageSize=5&sortBy=title&sortDir=ASC
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(name="pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name="pageSize", defaultValue ="3", required = false) int pageSize,
             @RequestParam(name="sortBy", defaultValue="id", required=false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        List<PostDto> postDtos= postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<> (postDtos, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<PostDto> updatePost(
            @RequestParam("postId") long postId,
            @RequestBody PostDto postDto
    ){
        PostDto dto = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
}
