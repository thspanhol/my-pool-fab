package my.pool.api.model;

public record UserDTO(
        String name,
        String email,
        String password
) {
    public UserEntity retornaUser(String id) {
        return UserEntity.builder()
                .id(id)
                .name(this.name())
                .email(this.email())
                .password(this.password())
                .build();
    }
}
