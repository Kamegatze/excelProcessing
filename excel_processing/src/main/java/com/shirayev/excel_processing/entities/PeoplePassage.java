package com.shirayev.excel_processing.entities;




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
import lombok.ToString;

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
