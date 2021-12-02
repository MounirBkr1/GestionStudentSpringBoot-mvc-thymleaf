package com.mnr.gestioneleves.repos;

import com.mnr.gestioneleves.entities.Etudiant;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public  interface  EtudiantRepository extends JpaRepository<Etudiant,Long>{
    //là vs avez un grand nombre de methode comme: save,add,delete,find...

    //chercer eleve par nom; on oit la definir ici car il n existe pas
    //public List<Etudiant> findByNom(String n);

    //selectionner page par page => Pageable ; query hql
    public Page<Etudiant> findByNom(String n, Pageable pageable);


    @Query("select e from Etudiant e where e.nom like :x")
    public Page<Etudiant> chercherEtudiants(@Param("x") String mc, Pageable pageable);

    @Query("select e from Etudiant e where e.dateNaissance> :x and e.dateNaissance<:y")
    public Page<Etudiant> chercherEtudiants(@Param("x")Date d1, @Param("y") Date d2, Pageable pageable);




}
