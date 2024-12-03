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
    private final PoolRepository poolRepository;
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

    public List<Card> getDataPool(String poolId){

        PoolEntity pool = poolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        List<Card> completeList = new ArrayList<>();

        pool.getPoolCards().forEach(card -> {
            CardsResponse response = restTemplate.getForObject(url + card, CardsResponse.class);
            assert response != null;
            completeList.add(response.getResults().getFirst());
        });

        return completeList;
    }
}
