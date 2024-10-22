package com.practice.photogram.domain.subscribe;

import com.practice.photogram.web.dto.subscribe.SubscribeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

//	@Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!
//	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
//	void mSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);
//
//	@Modifying
//	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
//	void mUnSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);

//	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
//	int mSubscribeState(@Param("principalId") int principalId, @Param("pageUserId") int pageUserId);

//	@Query("select count(*) from Subscribe s where s.fromUser.id= :pageUserId")
//	int mSubscribeCount(@Param("pageUserId") int pageUserId);



	long countByFromUserId(Integer fromUserId);
	boolean existsByFromUserIdAndToUserId(Integer fromUserId, Integer toUserId);

	void deleteByFromUserIdAndToUserId(Integer fromUserId, Integer toUserId);


	@Query("SELECT new com.practice.photogram.web.dto.subscribe.SubscribeDto(" +
			"u.id, u.username, u.profileImageUrl, " +
			"(SELECT COUNT(s2) > 0 FROM Subscribe s2 WHERE s2.fromUser.id = :fromUserId AND s2.toUser.id = u.id), " +
			"(CASE WHEN u.id = :fromUserId THEN true ELSE false END)) " +
			"FROM Subscribe s " +
			"JOIN s.toUser u " +
			"WHERE s.fromUser.id = :pageUserId")
	List<SubscribeDto> findSubscribersByPageUserId(@Param("fromUserId") int fromUserId, @Param("pageUserId") int pageUserId);
}
