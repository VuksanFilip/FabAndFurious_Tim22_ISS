package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseNoteDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Date date;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Note() {
    }

    public Note(String message, Date date, User user) {
        this.message = message;
        this.date = date;
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResponseNoteDTO parseToResponse(){
        return new ResponseNoteDTO(this.id, this.date, this.message);
    }
}
