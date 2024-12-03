package my.pool.api.model;

import lombok.*;
import my.pool.api.integration.Integration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoolEntityResponse {

    private String id;
    private String name;
    private Boolean isPublic;
    private String creatorId;
    private List<Card> poolCards;

    public PoolEntityResponse(PoolEntity poolEntity, Integration integration) {
        id = poolEntity.getId();
        name = poolEntity.getName();
        isPublic = poolEntity.getIsPublic();
        creatorId = poolEntity.getCreatorId();
        poolCards = integration.getDataPool(poolEntity.getPoolCards());
    }
}
