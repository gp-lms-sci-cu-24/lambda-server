package com.cu.sci.lambdaserver.announcement.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ISseService {

    SseEmitter subscribe();



}
