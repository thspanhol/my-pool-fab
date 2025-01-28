package my.pool.api.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteDecorator implements DeleteInterface{

    private final UserFacade userFacade;

    @Override
    public Mono<Void> delete(String userId) {
        System.out.println("Starting user delete...");
        Mono<Void> method = userFacade.delete(userId);
        System.out.println("Finishing user delete.");
        return method;
    }
}
