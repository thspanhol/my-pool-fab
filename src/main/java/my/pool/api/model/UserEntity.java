package my.pool.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private List<Pool> pools;

    public UserEntity(UserDTO userDTO) {
        this.password = userDTO.password();
        this.email = userDTO.email();
        this.name = userDTO.name();
        this.pools = new ArrayList<>();
    }
}
