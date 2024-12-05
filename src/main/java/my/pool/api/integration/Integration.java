package my.pool.api.integration;

import lombok.RequiredArgsConstructor;
import my.pool.api.integration.models.Card;
import my.pool.api.integration.models.CardsResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Integration {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public List<Card> fullApi(){

        List<Card> completeList = new ArrayList<>();
        String url = baseUrl;

        while (url != null) {
            CardsResponse response = restTemplate.getForObject(url, CardsResponse.class);

            if (response == null) break;

            completeList.addAll(response.getResults());
            url = response.getNext();
        }
        return completeList;
    }

    public List<Card> api(String name){

        CardsResponse response = restTemplate.getForObject(baseUrl + name, CardsResponse.class);

        assert response != null;

        return new ArrayList<>(response.getResults());
    }

    public List<Card> getDataPool(List<String> poolCards){

        List<Card> completeList = new ArrayList<>();

        poolCards.forEach(card -> {
            CardsResponse response = restTemplate.getForObject(baseUrl + card, CardsResponse.class);
            assert response != null;
            completeList.add(response.getResults().getFirst());
        });

        return completeList;
    }
}
