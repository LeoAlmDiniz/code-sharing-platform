package platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import platform.model.Code;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UuidDto {
    @NotBlank(message = "Code must be not black or null!")
    private UUID id;

    public UuidDto(Code code) {
        this.id = code.getId();
    }
}