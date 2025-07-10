package org.example.postservice.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;


@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {


}