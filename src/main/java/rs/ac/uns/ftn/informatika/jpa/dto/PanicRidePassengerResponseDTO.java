package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class PanicRidePassengerResponseDTO {

    private Long id;
    private String email;

    public PanicRidePassengerResponseDTO(Long id, String email) {
        this.id = id;
        this.email = email;
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

