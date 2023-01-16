package rs.ac.uns.ftn.informatika.jpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessagePageDTO {
    public int totalCount;
    public Set<ResponseMessageDTO> results;
}
