package com.example.uploadingfiles.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "fichero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fichero{
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Date fechaSubida;
    @Column(nullable = false)
    private String categoria;
}