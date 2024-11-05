package my.pool.api.repository;

import my.pool.api.model.Pool;

public interface CustomUserRepository {
    Pool findPoolByPoolId(String poolId);
}

