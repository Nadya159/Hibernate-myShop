package by.javaguru.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Order> orders;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "password", nullable = false)
    private String password;

}
