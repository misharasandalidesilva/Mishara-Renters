package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersTm {
    private String O_id;

    private String Qty;

    private String Status;

    private String C_id;

    private String E_Code;
}
