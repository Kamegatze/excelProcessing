package com.shirayev.excel_processing.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Sheets extends Essence {

    @Column
    private String title;

    @Column
    @OneToMany(mappedBy = "sheet")
    private Set<PeoplePassage> peoplePassages;

}
