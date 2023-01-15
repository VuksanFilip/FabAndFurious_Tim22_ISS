package rs.ac.uns.ftn.informatika.jpa.dto.request;

public class RequestUserResetPasswordDTO {

    private String newPassword;
    private String code;

    public RequestUserResetPasswordDTO() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
