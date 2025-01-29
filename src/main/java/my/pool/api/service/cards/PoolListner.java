package my.pool.api.service.cards;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PoolListner {

    @EventListener
    public void onPoolCreated(PoolCreatedEvent event){
        System.out.println("Pool with id " + event.getPool().getId() + " created");
    }
}
