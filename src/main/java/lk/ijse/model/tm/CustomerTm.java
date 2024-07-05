package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTm extends EmployeeTm {
    private String C_id;

    private  String C_name;

    private String C_contact;

    private String C_address;
}
