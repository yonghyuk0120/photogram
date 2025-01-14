package com.practice.photogram.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

//    @Modifying // 네이티브 수정쿼리에 반드시 적어주기
//    @Query(value = "INSERT INTO comment(content,imageId,userId,createDate) VALUES(:content, :imageId, :userId, now())", nativeQuery = true)
//    int mSave(String content, int imageId, int userId);
    // 사실 Comment를 반환하지 않는다 int를 반환한다.





}
