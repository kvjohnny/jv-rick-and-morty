package mate.academy.rickandmorty.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CharacterDto {
    @JsonProperty("id")
    private int externalId;
    private String name;
    private String status;
    private String gender;
}
