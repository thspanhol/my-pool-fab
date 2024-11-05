package my.pool.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pool")
public class Pool {

    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private List<Card> poolCards;

    public Pool(PoolDTO poolDTO) {
        this.name = poolDTO.name();
        this.poolCards = poolDTO.poolCards();
    }
}
