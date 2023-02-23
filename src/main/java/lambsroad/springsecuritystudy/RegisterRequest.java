package lambsroad.springsecuritystudy;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  private String name;
  private String pw;
  private String email;
}
