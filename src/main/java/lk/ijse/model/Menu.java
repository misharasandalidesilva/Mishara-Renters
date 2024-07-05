package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private String M_Code;

    private String M_name;

    private String Description;

    private String F_id;

  private String O_id;
}
