package my.pool.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Pool {
    private String name;
    private List<Card> poolCards;
}
