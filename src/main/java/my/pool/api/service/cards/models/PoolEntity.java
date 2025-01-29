package my.pool.api.service.cards.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//@Data
@Document(collection = "pools")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class PoolEntity {

    @Id
    private String id;
    private String name;
    private Boolean isPublic;
    private String creatorId;
    private List<String> poolCards;

    public PoolEntity() {}

    public PoolEntity(String name, Boolean isPublic, String creatorId, List<String> poolCards) {
        this.name = name;
        this.isPublic = isPublic;
        this.creatorId = creatorId;
        this.poolCards = poolCards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<String> getPoolCards() {
        return poolCards;
    }

    public void setPoolCards(List<String> poolCards) {
        this.poolCards = poolCards;
    }
}
