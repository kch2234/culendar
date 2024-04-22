package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.dto.InterestDTO;
import com.teamproject.culendar.dto.InterestForm;
import com.teamproject.culendar.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InterestService {

    private final InterestRepository interestRepository;

    public Long saveInterest(InterestForm interestForm) {
        Interest entity = interestForm.toEntity();
        Interest interest = interestRepository.save(entity);
        return interest.getId();
    }

    public void saveInterestNew(InterestDTO interestDTO) {
        Interest entity = interestDTO.toEntity();
        Interest interest = interestRepository.save(entity);
    }

    public List<Interest> findByMemberId(Long updateId) {
        // 회원의 관심분야 조회
        List<Interest> interestList = interestRepository.findByMemberId(updateId);
        return interestList;
    }

    public void deleteInterest(Long updateId) {
        // 선택한 관심분야 삭제
        interestRepository.deleteById(updateId);
    }
}
