package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTm {
    private String M_Code;

    private String M_name;

    private String Description;

    private String F_id;

    private String O_id;
}

