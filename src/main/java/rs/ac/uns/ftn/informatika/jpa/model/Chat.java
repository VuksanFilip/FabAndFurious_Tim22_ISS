package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ChatMessagesDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseChatDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public Chat(User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    public ResponseChatDTO parseToDTO(Long myId, Long otherId, String otherName){
        List<ChatMessagesDTO> messagesDTOS = new ArrayList<>();
        for (Message m : messages){
            messagesDTOS.add(m.parseToDTO());
        }
        return new ResponseChatDTO(id, myId, otherId, otherName, messagesDTOS);
    }
}
