package com.example.demo.entities;

import com.example.demo.enums.RelationEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "first_word", referencedColumnName = "id")
    private Word firstWord;

    @ManyToOne
    @JoinColumn(name = "second_word", referencedColumnName = "id")
    private Word secondWord;

    private RelationEnum relation;

}
