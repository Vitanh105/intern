package vanh.demo15.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Gift {
    @Id
    private String giftId;
    private String giftName;
    private int quantity;
}