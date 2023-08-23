package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
