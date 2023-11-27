package com.example.Service_system.service;
import com.example.Service_system.entity.Comments;
import com.example.Service_system.exception.CustomException;
import com.example.Service_system.repository.CommentsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentsRepository commentsRepository;

    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void save(Comments comments) {
        commentsRepository.save(comments);
    }

    public void delete(Comments comments) throws CustomException {
        if (commentsRepository.findById(comments.getId()).isPresent()) {
            commentsRepository.delete(comments);
        }else
            throw new CustomException("error");
    }

    public Comments findById(Long id) throws CustomException {
        Optional<Comments> byId = commentsRepository.findById(id);
        if (byId.isPresent())
            return byId.get();
        else
            throw new CustomException("the offer not found");
    }

    public void update(Comments comments) throws CustomException {
        if (commentsRepository.findById(comments.getId()).isPresent()) {
            commentsRepository.save(comments);
        }else
            throw new CustomException("error");
    }
}
