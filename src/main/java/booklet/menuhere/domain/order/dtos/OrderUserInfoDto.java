package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class OrderUserInfoDto {

    private String username;
    @Nullable
    private Address address;
    @Nullable
    private String phone;
    @Nullable
    private String email;
}
