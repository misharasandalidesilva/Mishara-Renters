package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private String O_id;

    private String Qty;

    private String Status;

    private String C_id;

    private String E_Code;
}
