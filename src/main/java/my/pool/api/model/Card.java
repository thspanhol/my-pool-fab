package my.pool.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {
    private String card_id;
    private String card_type;
    private String display_name;
    private String name;
    private String pitch;
    private String cost;
    private String defense;
    private String life;
    private String intellect;
    private String power;
    private String object_type;
    private String text;
    private String text_html;
    private String typebox;
    private String url;
    //private Image image;
}
