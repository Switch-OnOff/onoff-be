package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.dto.ChatMainResponseDto;

public interface ChatMainService {

    ChatMainResponseDto getMainChats(Long userId);
}
