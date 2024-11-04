package my.pool.api.controller;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.*;
import my.pool.api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class userController {


    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable String id) {
        System.out.println(id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/cards")
    public List<Card> getAllCards() {

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

    @GetMapping("/cardsfn")
    public List<Card> getCardsByName(@RequestParam String name) {

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

    @PostMapping
    public void postUser(@RequestBody UserDTO userDTO) {
        userRepository.insert(new UserEntity(userDTO));
    }

    @PutMapping("/{id}")
    public void putUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        System.out.println(id);
        userRepository.findById(id)
                .map(u -> userRepository.save(userDTO.retornaUser(id)))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/cards/{id}")
    public void addPool(@PathVariable String id, @RequestBody Pool pool) {
        System.out.println(id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getPools().add(pool);
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        System.out.println(id);
        userRepository.deleteById(id);
    }

}
