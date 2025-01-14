package com.practice.photogram.service;

import com.practice.photogram.domain.Likes.Likes;
import com.practice.photogram.domain.Likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;


    @Transactional
    public void 좋아요(int imageId, int principalId){
//    Likes like = new Likes();
//    like.setImage( 이미지 객체 찾아 넣기 );
//    like.setUser(유저 객체 찾아 넣기 );
//    likesRepository.save(like)
//    이미지, 유저 아이디를 받는 것이므로, 아이디로 유저객체, 이미지 객체 새로 찾아서 넣는
//    이런 방식은 비추한다.(이 강의에서는)- 참고로 subscribe는 내가 따로 비추 방식으로 구현 함
//    그러지 말고 그냥 네이티브 쿼리를 날려보자

        likesRepository.mLikes(imageId,principalId);

    }


    @Transactional
    public void 좋아요취소(int imageId, int principalId){
        likesRepository.mUnLikes(imageId,principalId);

    }
}
