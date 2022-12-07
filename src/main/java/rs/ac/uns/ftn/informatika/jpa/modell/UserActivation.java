package rs.ac.uns.ftn.informatika.jpa.modell;

import java.util.Date;

public class UserActivation {

    private int id;
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
