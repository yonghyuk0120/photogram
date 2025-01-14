package com.practice.photogram.service;

import com.practice.photogram.domain.comment.Comment;
import com.practice.photogram.domain.comment.CommentRepository;
import com.practice.photogram.domain.image.Image;
import com.practice.photogram.domain.user.User;
import com.practice.photogram.domain.user.UserRepository;
import com.practice.photogram.handler.ex.CustomApiException;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.web.dto.comment.CommentDto;
import com.practice.photogram.web.dto.image.ImageStoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentDto 댓글쓰기(String content, int imageId, int userId) {

//        유저리포지토리.findById(userId); + orElseThrow
//        이미지리포지토리.findById(imageId); + orElseThrow
//        Comment comment = new Comment(userId, imageId);
//        Comment리포지토리.save(코멘트)
//        이런식으로 두가지 불러온다음. 코멘트 객체를 만들고 save할 수 도 있다.
//        하지만 이 과정 자체가 복잡하므로 깔끔하게 쿼리를 만들어서 날릴 수도 있다.
//        하지만, 이렇게 네이티브쿼리를 쓰면 리포지토리에서 결과 Comment 객체를 받을 수 없다.


//        댓글을 쓰고 바로 결과도 받아와야 하므로 jpa리포지토리의 save를 사용하자.

//        return commentRepository.mSave(content, imageId, userId);
//        mSave는 사실 Comment를 반환할수없다. int만을 반환


// 하지만 위에서 말했듯 db에 여러번 통신하는건 번거로운 일이고, 실제로 우리가 insert에 필요한 것은 imageId, userId만 확실한 comment객체를 만드는 것이기에 여기서 가짜객체를 만든다.


        Image image = new Image();
        image.setId(imageId);

//    User user = new User();
//    user.setId(userId);
// 아니다. 여기서 user객체속 username은 바로 다시 전달하기에 db를 가야한다.
// 이렇게 보내면, 뷰에서 null이 나온다.
// 따라서 이건 그냥 repository를 간다.
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new CustomValidationApiException("찾을 수 없는 id입니다."));


        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        Comment resComment = commentRepository.save(comment);


        return new CommentDto(resComment);

    }


    @Transactional
    public void 댓글삭제(int id) {
//        commentRepository.deleteById(id);

//         만약 에러가 터진다고 가정하면 아래처럼 잡아주면 된다.

        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }

        // api 데이터를 답할 때
        // validation은 어떤 값을 받을 때
        // 그냥 custom 페이지를 리턴할 때

    }
}
