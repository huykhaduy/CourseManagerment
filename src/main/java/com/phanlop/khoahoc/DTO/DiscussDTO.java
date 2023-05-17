package com.phanlop.khoahoc.DTO;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class DiscussDTO {
    private MessageType type;
    private UUID courseID;
    private long userID;
    private String userName;
    private String userAvt;
    private String content;
    private Instant time;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
