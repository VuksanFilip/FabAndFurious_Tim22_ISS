package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

public class ResponsePassengerIdEmailDTO {

    private Long id;
    private String email;

    public ResponsePassengerIdEmailDTO() {
    }

    public ResponsePassengerIdEmailDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public ResponsePassengerIdEmailDTO(Passenger passenger) {
        this.id = passenger.getId();
        this.email = passenger.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
