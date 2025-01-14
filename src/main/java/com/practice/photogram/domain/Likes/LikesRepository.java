package com.practice.photogram.domain.Likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Integer> {


    @Modifying // 네이티브 쿼리로 변경 할 땐 반드시 modifying 추가
    @Query(value = "insert into likes(imageId,userId, createDate) values(:imageId, :principalId, now())", nativeQuery = true)
    //네이티브 쿼리 사용시 PrePersist가 안 먹으므모 직접 createDate와 now를 적어줘야한다.
    int mLikes(int imageId, int principalId);

    @Modifying // 네이티브 쿼리로 변경 할 땐 반드시 modifying 추가
    @Query(value = "delete  from likes where imageId = :imageId and userId = :principalId", nativeQuery = true)
    int mUnLikes(int imageId, int principalId);
}
