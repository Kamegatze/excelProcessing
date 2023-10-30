package com.shirayev.statistics_people_passage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sheets extends Essence {

    @Column
    private String title;

    @Column
    @OneToMany(mappedBy = "sheet")
    private List<StatisticsPeoplePassage> statisticsPeoplePassages;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

}
