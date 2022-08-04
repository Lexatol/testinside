package info.the_inside.test.dto;

/*
ДТО для сообщений
 */


import lombok.*;

import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private String name;
    private String description;
    private String createAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createAt, that.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createAt);
    }
}
