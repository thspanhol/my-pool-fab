package my.pool.api.service.users;

import reactor.core.publisher.Mono;

public interface DeleteInterface {
    Mono<Void> delete(String userId);
}
