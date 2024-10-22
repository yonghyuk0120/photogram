package com.practice.photogram.service;


import com.practice.photogram.domain.subscribe.Subscribe;
import com.practice.photogram.domain.subscribe.SubscribeRepository;
import com.practice.photogram.domain.user.User;
import com.practice.photogram.domain.user.UserRepository;
import com.practice.photogram.handler.ex.CustomApiException;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.web.dto.CMRespDto;
import com.practice.photogram.web.dto.subscribe.SubscribeDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	private final UserRepository userRepository;
	private final EntityManager em;

	// Repository는 EntityManager를 구현해서 만들어져 있는 구현체
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){


		List<SubscribeDto> subscribeDtos =  subscribeRepository.findSubscribersByPageUserId(principalId,pageUserId);
		return subscribeDtos;
	}
	
	
	@Transactional
	public ResponseEntity<?> 구독하기(int fromUserId, int toUserId) {
		try {
			User fromUser = userRepository.findById(fromUserId).orElseThrow(() ->
					new CustomValidationApiException("찾을 수 없는 id입니다."));
			User toUser = userRepository.findById(toUserId).orElseThrow(() ->
					new CustomValidationApiException("찾을 수 없는 id입니다."));

			Subscribe subscribe = new Subscribe(fromUser, toUser);
			subscribeRepository.save(subscribe);
			return new ResponseEntity<>(new CMRespDto<>(1, "구독하기 성공", null), HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public  ResponseEntity<?> 구독취소하기(int fromUserId, int toUserId) {

		subscribeRepository.deleteByFromUserIdAndToUserId(fromUserId,toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독취소하기 성공", null), HttpStatus.OK);

	}
}
