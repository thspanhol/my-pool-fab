package my.pool.api.integration;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.Card;
import my.pool.api.model.CardsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Integration {

    private final RestTemplate restTemplate;

    public List<Card> api(String name){
        String url = "https://cards.fabtcg.com/api/search/v1/cards/?q=";

        List<Card> completeList = new ArrayList<>();

        while (url != null) {
            CardsResponse response = restTemplate.getForObject(url + name, CardsResponse.class);

            if (response == null) break;

            completeList.addAll(response.getResults());
            url = response.getNext();
        }
        return completeList;
    }
}
