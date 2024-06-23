//package com.cu.sci.lambdaserver.chat.entites;
//
//
//import com.cu.sci.lambdaserver.user.User;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "messages")
//public class Message {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
//    @SequenceGenerator(name = "message_seq", sequenceName = "message_seq", allocationSize = 10)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "sender_id", nullable = false)
//    private User sender;
//
//    @ManyToOne
//    @JoinColumn(name = "receiver_id", nullable = false)
//    private User receiver;
//
//    @Column(nullable = false)
//    private String content;
//
//    @ManyToOne
//    @JoinColumn(name = "chat_room_id", nullable = false)
//    private ChatRoom chatRoom;
//
//    @Column(nullable = false)
//    private LocalDateTime timestamp = LocalDateTime.now();
//
//    @Column
//    private LocalDateTime editTimestamp;
//
//
//}
