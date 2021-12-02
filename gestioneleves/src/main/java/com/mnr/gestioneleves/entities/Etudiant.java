package com.mnr.gestioneleves.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Etudiant implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    //definir longuer =>varchar(30)
    @Column(length=30)
    @NotEmpty
    @Size(min=2, max=30)
    private String nom;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateNaissance;

    @NotEmpty
    @Email(message="Please provide a valid email address")
    private String email;

    //stoquer foto ds base de données=>tableau de byte
    //private byte[] photo;
    //pr les fotosstoqués dans un dossier
    private String photo;

    //constructeur sans params pour JPA
    public Etudiant() {
    }


    //constructeur avec params sans id=>car il est auto-incrementé
    public Etudiant(String nom, Date dateNaissance, String email, String photo) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
