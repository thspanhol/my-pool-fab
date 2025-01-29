package my.pool.api.service.cards;

import my.pool.api.service.cards.models.PoolEntity;
import org.springframework.context.ApplicationEvent;

public class PoolCreatedEvent extends ApplicationEvent {
    private final PoolEntity pool;

    public PoolCreatedEvent(Object source, PoolEntity pool) {
        super(source);
        this.pool = pool;
    }

    public PoolEntity getPool() {
        return pool;
    }
}
