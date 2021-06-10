package com.fh.shop.service;

import com.fh.shop.common.ServerResponse;

public interface MemberService {

    ServerResponse memberLogin(String memberName, String password);

    ServerResponse selectMemberName(String memberName);

    Integer selectSumStock(Long id);
}





