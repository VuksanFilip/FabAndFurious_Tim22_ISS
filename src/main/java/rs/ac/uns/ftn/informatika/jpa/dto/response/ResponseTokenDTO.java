package rs.ac.uns.ftn.informatika.jpa.dto.response;

import lombok.Data;

@Data
public class ResponseTokenDTO {
    String accessToken;
    String refreshToken;

    public ResponseTokenDTO(String s, String s1) {
        this.accessToken = s;
        this.refreshToken = s1;
    }
}