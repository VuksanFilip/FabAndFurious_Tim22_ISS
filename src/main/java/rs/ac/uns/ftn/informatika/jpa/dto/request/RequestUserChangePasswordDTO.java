package rs.ac.uns.ftn.informatika.jpa.dto.request;

public class RequestUserChangePasswordDTO {

    private String newPassword;
    private String oldPassword;

    public RequestUserChangePasswordDTO() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
