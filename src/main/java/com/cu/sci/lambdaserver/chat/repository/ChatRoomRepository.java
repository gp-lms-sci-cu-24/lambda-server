//package com.cu.sci.lambdaserver.chat.repository;
//
//import com.cu.sci.lambdaserver.chat.entites.ChatRoom;
//import com.cu.sci.lambdaserver.user.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//
//    Optional<ChatRoom> findByFirstUserAndSecondUser(User firstUser, User secondUser);
//
//}
