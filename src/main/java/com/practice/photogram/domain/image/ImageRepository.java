package com.practice.photogram.domain.image;

import com.practice.photogram.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {


    // 페이징 처리 이전
//	@Query("select i from Image i left join fetch i.user where i.user.id in(select s.toUser.id from Subscribe s where s.fromUser.id = :principalId) order by i.id desc")
//	List<Image> findImages(@Param("principalId") int principalId);


    // 페이징 처리 이후
    @Query("select i from Image i left join fetch i.user where i.user.id in(select s.toUser.id from Subscribe s where s.fromUser.id = :principalId) order by i.id desc")
    Page<Image> findImages(@Param("principalId") int principalId, Pageable pageable);

//	@Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId) ORDER BY id DESC", nativeQuery = true)
//	Page<Image> mStory(@Param("principalId") int principalId, Pageable pageable);
	/*
	select i
	from Image i
	where i.user.id in
		(select s.toUser.id from Subscribe s where s.fromUser.id = :principalId)
	 order by i.id desc
	*/





    @Query("select i from Image i inner join Likes l on i.id = l.image.id order by (select count(l) from Likes l where i = l.image) desc")
    List<Image> mPopular();


    //	@Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) likeCount FROM likes GROUP BY imageId) c ON i.id = c.imageId ORDER BY likeCount DESC", nativeQuery = true)
//	Page<Image> mPopular();


//	@Query("select i from Image i inner join (SELECT l.imageId, COUNT(l.imageId) likeCount FROM likes l GROUP BY l.imageId) C ON i.id = c.imageId ORDER BY likeCount DESC")
//	List<Image> mPopular();
// 조인절에 테이블이 들어갈수 없는듯.. jqpl은 객체가 들어와야..
// 따라서 jqpl은 join절에서 서브쿼리 사용 불가라고 생각함.


}

