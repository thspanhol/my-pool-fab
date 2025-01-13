package my.pool.api.service.users.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> pools;


    public UserEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.pools = builder.pools;
    }

    public static class Builder {
        private String id;
        private String name;
        private String email;
        private String password;
        private List<String> pools;

        public Builder id(String id) {
            if (id != null) {
                this.id = id;
                return this;
            }
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder pools(List<String> pools) {
            if (pools != null) {
                this.pools = pools;
                return this;
            }
            this.pools = new ArrayList<>();
            return this;
        }
        public UserEntity build() {
            return new UserEntity(this);
        }
    }

}
