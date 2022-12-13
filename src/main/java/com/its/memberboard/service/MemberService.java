package com.its.memberboard.service;

import com.its.memberboard.DTO.MemberDTO;
import com.its.memberboard.entity.MemberEntity;
import com.its.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberSaveEntity(memberDTO);
        Long savedId = memberRepository.save(memberEntity).getId();

        return savedId;
    }

    public String duplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);

        if (optionalMemberEntity.isPresent()) {
            return null;
        } else {
            return "Y";
        }

    }
}
