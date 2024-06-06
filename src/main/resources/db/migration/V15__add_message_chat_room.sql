
CREATE SEQUENCE IF NOT EXISTS chat_room_seq START WITH 1 INCREMENT BY 10;

CREATE SEQUENCE IF NOT EXISTS message_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE chat_rooms
(
    id       BIGINT NOT NULL,
    user1_id BIGINT NOT NULL,
    user2_id BIGINT NOT NULL,
    CONSTRAINT pk_chat_rooms PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id             BIGINT       NOT NULL,
    sender_id      BIGINT       NOT NULL,
    receiver_id    BIGINT       NOT NULL,
    content        VARCHAR(255) NOT NULL,
    chat_room_id   BIGINT       NOT NULL,
    timestamp      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    edit_timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);


ALTER TABLE chat_rooms
    ADD CONSTRAINT FK_CHAT_ROOMS_ON_USER1 FOREIGN KEY (user1_id) REFERENCES users (id);

ALTER TABLE chat_rooms
    ADD CONSTRAINT FK_CHAT_ROOMS_ON_USER2 FOREIGN KEY (user2_id) REFERENCES users (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_CHAT_ROOM FOREIGN KEY (chat_room_id) REFERENCES chat_rooms (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_RECEIVER FOREIGN KEY (receiver_id) REFERENCES users (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_SENDER FOREIGN KEY (sender_id) REFERENCES users (id);

