package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.RatingType;
import com.teamproject.culendar.dto.RatingDTO;
import com.teamproject.culendar.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Transactional
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("getAvgRating/{id}")
    public ResponseEntity<String> getAvgRating(@PathVariable("id") Long programId) {
        log.info("** RatingController GET /rating/getAvgRating - programId: {}", programId);
        RatingType avgRatingEnum = ratingService.getAvgRatingStr(programId);

        return ResponseEntity.ok(avgRatingEnum.getValue());

    }

    @GetMapping("getRating/{memberId}/{programId}")
    public ResponseEntity<String> getRating(@PathVariable("memberId") Long memberId, @PathVariable("programId") Long programId) {
        log.info("** RatingController GET /rating/getRating - userId: {}, programId: {}", memberId, programId);
        RatingType ratingEnum = ratingService.getRating(memberId, programId);

        return ResponseEntity.ok(ratingEnum.getValue());
    }

    @PostMapping("addRating")
    public ResponseEntity<String> addRating(@RequestBody RatingDTO ratingDTO) {
        log.info("** RatingController POST /rating/addRating - ratingDTO: {}", ratingDTO);

        boolean result = ratingService.addRating(ratingDTO);

        if (!result) {
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 회원 문장형 평가 최신순으로 무한 스크롤 조회
    @GetMapping("/findMyRatingWithPaging/{memberId}/{start}")
    public ResponseEntity<List<RatingDTO>> findMyRatingByMemberIdWithPaging(@PathVariable("memberId") Long memberId, @PathVariable("start") Long start){

        List<RatingDTO> ratingByMemberId = ratingService.findMyRatingByMemberIdWithPaging(memberId, start);

        return ResponseEntity.ok().body(ratingByMemberId);
    }
}
