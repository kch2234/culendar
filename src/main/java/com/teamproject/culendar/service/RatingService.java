package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.enumFiles.RatingType;
import com.teamproject.culendar.domain.program.Rating;
import com.teamproject.culendar.dto.RatingDTO;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.repository.ProgramRepository;
import com.teamproject.culendar.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;

    public RatingType getAvgRatingStr(Long programId) {
        log.info("** RatingService - getAvgRatingStr - programId: {}", programId);
        // DB에서 평점 평균 가져와 평점 평균 구하기
        Double avgRating = ratingRepository.getAvgRating(programId);
        log.info("avgRating: {}", avgRating);
        if (avgRating == null) {
            avgRating = 0.0;
        }
        // 평균 값 반올림 하기
        Long avgRatingInt = Math.round(avgRating);
        // rating enum으로 변환
        RatingType avgRatingEnum = ratingIntToEnum(avgRatingInt);

        return avgRatingEnum;
    }

    public RatingType getRating(Long memberId, Long programId) {
        log.info("** RatingService - getRating - memberId: {}, programId: {}", memberId, programId);
        // DB에서 평점 가져와
        Rating ratingEntity = ratingRepository.findByMemberIdAndProgramId(memberId, programId);
        Long rating;
        if (ratingEntity == null) {
            rating = 0L;
        } else {
            RatingDTO ratingDTO = new RatingDTO(ratingEntity);
            rating = ratingDTO.getRating();
        }
        // 평점 값 반올림 하기

        RatingType ratingEnum = ratingIntToEnum(rating);

        return ratingEnum;
    }

    private RatingType ratingIntToEnum(Long avgRatingInt) {

        if (avgRatingInt == 0) {
            return RatingType.NONE;
        } else if (avgRatingInt == 1) {
            return RatingType.VERYBAD;
        } else if (avgRatingInt == 2) {
            return RatingType.BAD;
        } else if (avgRatingInt == 3) {
            return RatingType.NORMAL;
        } else if (avgRatingInt == 4) {
            return RatingType.GOOD;
        } else if (avgRatingInt == 5) {
            return RatingType.VERYGOOD;
        } else {
            return RatingType.NONE;
        }
    }

    public boolean addRating(RatingDTO ratingDTO) {
        log.info("** RatingService - addRating - ratingDTO: {}", ratingDTO);
        // DTO를 Entity로 변환

        // 중복 체크
        Rating alreadyRated = ratingRepository.findByMemberIdAndProgramId(ratingDTO.getMemberId(), ratingDTO.getProgramId());
        if (alreadyRated != null) {
            alreadyRated.setRating(ratingDTO.getRating());
            return false;
        }
        ratingDTO.setProgram(programRepository.findById(ratingDTO.getProgramId()).get());
        ratingDTO.setMember(memberRepository.findById(ratingDTO.getMemberId()).get());
        // DB에 저장
        ratingRepository.save(ratingDTO.toEntity());
        return true;
    }

}
