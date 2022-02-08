package platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import platform.model.Code;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {
    @NotBlank(message = "Code must be not black or null!")
    private String code;
    private String date;
    private Long time;
    private Long views;


    public CodeDto(Code code) {
        if (code == null) {
            this.code = "";
            this.date = "";
            this.time = 0L;
            this.views = 0L;

        } else {
            if (code.getCode() == null) {
                this.code = "";
            } else {
                this.code = code.getCode();
            }
            if (code.getDate() == null) {
                this.date = "";
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                this.date = formatter.format(code.getDate());
            }
            if (code.getTime() == null) {
                this.time = 0L;
            } else {
                this.time = code.getTime();
            }
            if (code.getViews() == null) {
                this.views = 0L;
            } else {
                this.views = code.getViews();
            }
        }
    }
}