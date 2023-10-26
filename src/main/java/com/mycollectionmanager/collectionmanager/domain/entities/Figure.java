package com.mycollectionmanager.collectionmanager.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "figures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Figure {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "figure_id_generator"
    )
    private Long id;

    @Column(
            length = 100,
            nullable = false
    )
    private String name;

    @Lob
    private String image;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "figures_categories",
            joinColumns = @JoinColumn(name = "figure_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")

    )
    private List<Category> categories;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @CreationTimestamp
    private Date createdAt;
}
