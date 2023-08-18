package org.example.visit.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.office.model.Office;
import org.example.post.model.Post;

import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "index", referencedColumnName = "index")
    private Office office;
    @Column(name = "arrival_date")
    private LocalDateTime arrival;
    @Column(name = "departure_date")
    private LocalDateTime departure;

}
