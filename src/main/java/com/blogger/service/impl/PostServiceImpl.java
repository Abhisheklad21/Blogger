package com.blogger.service.impl;


import com.blogger.entity.Post;
import com.blogger.exception.ResourceNotFoundException;
import com.blogger.payload.PostDto;
import com.blogger.repository.PostRepository;
import com.blogger.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

       @Override
    public void createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        postRepo.save(post);
        System.out.println(post);
    }

    @Override
    public void deletePost(long id) {
        Optional<Post> byId = postRepo.findById(id);
       postRepo.findById(id).orElseThrow(
               ()->new ResourceNotFoundException("Post not found with id: "+id)
       );
    postRepo.deleteById(id);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepo.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapDto(post)).collect((Collectors.toList()));
        return dtos;
    }

    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with ID: " + postId)
        );
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        Post save = postRepo.save(post);
        return mapDto(save);
    }

    PostDto  mapDto(Post post){
           PostDto dto= new PostDto();
           dto.setId(post.getId());
           dto.setTitle(post.getTitle());
           dto.setContent(post.getContent());
           dto.setDescription(post.getDescription());
           return dto;
    }


}
