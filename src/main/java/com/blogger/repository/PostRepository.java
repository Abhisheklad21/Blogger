package com.blogger.repository;

import com.blogger.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository  extends JpaRepository<Post, Long>{

        }

