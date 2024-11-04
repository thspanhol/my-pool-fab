package my.pool.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardsResponse {
    private int count;
    private String next;
    private String previous;
    private List<Card> results;
    //private ErrorsDTO errors;
}
