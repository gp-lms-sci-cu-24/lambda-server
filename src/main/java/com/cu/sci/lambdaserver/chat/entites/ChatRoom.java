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
//import java.util.Collection;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "chat_rooms")
//public class ChatRoom {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_room_seq")
//    @SequenceGenerator(name = "chat_room_seq", sequenceName = "chat_room_seq", allocationSize = 10)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user1_id", nullable = false)
//    private User firstUser;
//
//    @ManyToOne
//    @JoinColumn(name = "user2_id", nullable = false)
//    private User secondUser;
//
//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Collection<Message> messages;
//
//
//}
