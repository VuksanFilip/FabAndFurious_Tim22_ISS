package rs.ac.uns.ftn.informatika.jpa.dto.request;

public class RequestEditApprovalDTO {

    private boolean approval;

    public RequestEditApprovalDTO() {
    }

    public RequestEditApprovalDTO(boolean approval) {
        this.approval = approval;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }
}
