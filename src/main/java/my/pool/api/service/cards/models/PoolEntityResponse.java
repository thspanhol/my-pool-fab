package my.pool.api.service.cards.models;

import lombok.*;
import my.pool.api.integration.models.Card;

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

    public static PoolEntityResponse toResponse(PoolEntity poolEntity, List<Card> poolList) {
        return PoolEntityResponse.builder()
                .id(poolEntity.getId())
                .name(poolEntity.getName())
                .isPublic(poolEntity.getIsPublic())
                .creatorId(poolEntity.getCreatorId())
                .poolCards(poolList)
                .build();
    }
}
