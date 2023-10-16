package com.shirayev.excel_processing.entities;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Table
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PeoplePassage extends Essence{
    
    @Column
    private String last_name;

    @Column
    private String first_name;

    @Column
    private String patronymic;

    @Column
    private Integer age;

    @Column(name = "actions")
    private String action;

    @Column
    private Time timeAction;

    @ManyToOne
    @JoinColumn(name = "sheet_id", nullable = false)
    private Sheets sheet;

}
