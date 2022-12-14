package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class UserActivation {

    @Id
    private int id;

    @OneToOne
    private User user;
    private Date date;
    private int lifespan;

    public UserActivation() {
    }

    public UserActivation(int id, User user, Date date, int lifespan) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.lifespan = lifespan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
}
