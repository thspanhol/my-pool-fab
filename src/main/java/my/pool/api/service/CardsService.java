package my.pool.api.service;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.*;
import my.pool.api.repository.FindPoolByIdRepository;
import my.pool.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardsService {

    private final UserRepository userRepository;
    private final FindPoolByIdRepository findPoolByIdRepository;
    private final RestTemplate restTemplate;

    public List<Card> getAll(){
        String url = "https://cards.fabtcg.com/api/search/v1/cards";

        List<Card> listaCompleta = new ArrayList<>();

        while (url != null) {
            CardsResponse response = restTemplate.getForObject(url, CardsResponse.class);

            if (response == null) break;

            listaCompleta.addAll(response.getResults());
            url = response.getNext();
        }
        return listaCompleta;
    }

    public List<Card> findByName(String name){
        String url = "https://cards.fabtcg.com/api/search/v1/cards/?q=";

        List<Card> listaCompleta = new ArrayList<>();

        while (url != null) {
            CardsResponse response = restTemplate.getForObject(url + name, CardsResponse.class);

            if (response == null) break;

            listaCompleta.addAll(response.getResults());
            url = response.getNext();
        }
        return listaCompleta;
    }

    public void addPool(String id, PoolDTO poolDTO){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getPools().add(new Pool(poolDTO));
        userRepository.save(user);
    }

    public Pool findPoolById(String id){
        return findPoolByIdRepository.findPoolByPoolId(id);
    }
}
