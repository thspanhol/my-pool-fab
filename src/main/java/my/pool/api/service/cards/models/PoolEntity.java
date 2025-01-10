package my.pool.api.service.cards.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "pools")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoolEntity {

    @Id
    private String id;
    private String name;
    private Boolean isPublic;
    private String creatorId;
    private List<String> poolCards;

}
