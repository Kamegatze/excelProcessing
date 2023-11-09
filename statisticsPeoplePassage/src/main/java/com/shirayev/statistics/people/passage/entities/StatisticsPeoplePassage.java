package com.shirayev.statistics.people.passage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StatisticsPeoplePassage extends Essence {

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
