package com.shirayev.excel_processing.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.List;

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
    private List<PeoplePassage> peoplePassages;

}
