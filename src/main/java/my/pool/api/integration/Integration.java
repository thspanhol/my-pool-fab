package my.pool.api.integration;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.Card;
import my.pool.api.model.CardsResponse;
import my.pool.api.model.PoolEntity;
import my.pool.api.repository.PoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Integration {

    private final RestTemplate restTemplate;
    private String url = "https://cards.fabtcg.com/api/search/v1/cards/?q=";

    public List<Card> api(String name){

        List<Card> completeList = new ArrayList<>();

        while (url != null) {
            CardsResponse response = restTemplate.getForObject(url + name, CardsResponse.class);

            if (response == null) break;

            completeList.addAll(response.getResults());
            url = response.getNext();
        }
        return completeList;
    }

    public List<Card> getDataPool(List<String> poolCards){

        List<Card> completeList = new ArrayList<>();

        poolCards.forEach(card -> {
            CardsResponse response = restTemplate.getForObject(url + card, CardsResponse.class);
            assert response != null;
            completeList.add(response.getResults().getFirst());
        });

        return completeList;
    }
}
