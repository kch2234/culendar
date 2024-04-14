package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.dto.InterestForm;
import com.teamproject.culendar.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    public Long saveInterest(InterestForm interestForm) {
        Interest entity = interestForm.toEntity();
        // 회원이 선택한 관심사들을 interestList에 담아서 저장
        for (int i = 0; i < interestForm.getInterestList().size(); i++) {
            entity.setInterest(interestForm.getInterestList().get(i));
            interestRepository.save(entity);
        }
//        Interest saveInterest = interestRepository.save(entity);
        return entity.getId();
    }
}
