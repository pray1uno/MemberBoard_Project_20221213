package com.its.memberboard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_profile_table")
public class MemberProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memberProfile; // 있으면 Y 없으면 N

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static MemberProfileEntity toSaveMemberProfileEntity(MemberEntity entity, String originalFileName, String storedFileName) {
        MemberProfileEntity memberProfileEntity = new MemberProfileEntity();

        memberProfileEntity.setOriginalFileName(originalFileName);
        memberProfileEntity.setStoredFileName(storedFileName);
        memberProfileEntity.setMemberEntity(entity);

        return memberProfileEntity;
    }
}
