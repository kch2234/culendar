package com.teamproject.culendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FollowFollowing {
    private List<MemberDTO> followingList;
}
