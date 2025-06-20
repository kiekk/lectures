package handlebar.study.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private int age;
    private boolean isActive;
    private UserDetail detail;

}
