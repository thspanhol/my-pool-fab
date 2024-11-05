package my.pool.api.model;

import java.util.List;

public record PoolDTO(
        String name,
        List<Card>poolCards
) {

}
