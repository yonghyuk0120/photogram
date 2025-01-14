package com.practice.photogram.service;

import com.practice.photogram.domain.subscribe.SubscribeRepository;
import com.practice.photogram.domain.user.User;
import com.practice.photogram.domain.user.UserRepository;
import com.practice.photogram.handler.ex.CustomApiException;
import com.practice.photogram.handler.ex.CustomException;
import com.practice.photogram.handler.ex.CustomValidationApiException;
import com.practice.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscribeRepository subscribeRepository;


    @Value("${file.path}")
    private String uploadFolder;


    @Transactional
    public User 회원프로필사진변경(int principlaId, MultipartFile profileImageFile){
        UUID uuid = UUID.randomUUID(); // uuid
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename(); // 1.jpg
        System.out.println("이미지 파일이름 : " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신, I/O(하드에 기록, 혹은 읽을때) -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 이미지 저장 하기 위해 해당하는 유저 찾기
        User userEntity = userRepository.findProfileById(principlaId).orElseThrow(()-> {
            // throw -> return 으로 변경
            return new CustomApiException("유저를 찾을 수 없습니다.");
        });

        // 유저 테이블에서 url 바꿔주기. 더티체킹으로 이걸로 끝난다.
        userEntity.setProfileImageUrl(imageFileName);

    return userEntity;
    }


    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserId, int principalId) {


        // SELECT * FROM image WHERE userId = :userId;
        User userEntity = userRepository.findProfileById(pageUserId).orElseThrow(()-> {
            // throw -> return 으로 변경
            return new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        UserProfileDto dto = new UserProfileDto();
        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        boolean subscribeState =  subscribeRepository.existsByFromUserIdAndToUserId(principalId, pageUserId);
        long subscribeCount = subscribeRepository.countByFromUserId(pageUserId);

        dto.setSubscribeState(subscribeState);
        dto.setSubscribeCount(subscribeCount);

//        // 좋아요 카운트 추가하기
        userEntity.getImages().forEach((image)->{
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }


    @Transactional
    public User 회원수정(int id, User user) {
        // A. 영속화
        // 1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 익섹션 발동시킬께 orElseThrow()
        User userEntity = userRepository.findById(id).orElseThrow(() ->
             new CustomValidationApiException("찾을 수 없는 id입니다.")
        );

        // B. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    } // 더티체킹이 일어나서 업데이트가 완료됨.

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }


    public boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }


}
