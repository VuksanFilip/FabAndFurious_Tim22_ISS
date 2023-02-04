package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ResponseLoginDTO {

    private String accessToken;
    private String refreshToken;

    public ResponseLoginDTO() {
    }

    public ResponseLoginDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
