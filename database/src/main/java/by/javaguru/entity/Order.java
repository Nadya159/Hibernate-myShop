package by.javaguru.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "items"})
@Builder
@Entity
@Table(name = "orders")
public class Order implements BaseEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Item> items;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}

