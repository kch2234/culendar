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
        Interest savedInterest = interestRepository.save(entity);
        return savedInterest.getId();
    }
}
