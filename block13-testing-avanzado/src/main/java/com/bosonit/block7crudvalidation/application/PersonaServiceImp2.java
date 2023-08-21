package com.bosonit.block7crudvalidation.application;

import com.bosonit.block7crudvalidation.controller.dto.*;
import com.bosonit.block7crudvalidation.domain.*;
import com.bosonit.block7crudvalidation.repository.EstudianteRepository;
import com.bosonit.block7crudvalidation.repository.PersonaRepository;
import com.bosonit.block7crudvalidation.repository.ProfesorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonaServiceImp2 implements PersonaService {

    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PersonaOutputDto addPersona(PersonaInputDto persona) {
        checkPersonaNulls(persona);
        return personaRepository.save(new Persona(persona))
                .personaToPersonaOutputDto();
    }

    public PersonaOutputDto getPersonaById(int id) {
        Optional<Persona> oPersona = personaRepository.findById(id);
        if (oPersona.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            return oPersona.get().personaToPersonaOutputDto();
        }
    }

    @Override
    public PersonaDTOInterface getPersonaTypeById(int id) {
        Optional<Persona> oPersona = personaRepository.findById(id);
        if (oPersona.isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            Optional<Estudiante> oEstudiante = estudianteRepository.findByPersona_id(oPersona.get().getId());
            if (oEstudiante.isPresent()) {
                return new PersonaEstudianteODTO(oEstudiante.get());
            }
            Optional<Profesor> oProfesor = profesorRepository.findByPersona(oPersona.get());
            if (oProfesor.isPresent()) {
                Profesor profesor = oProfesor.get();
                return new PersonaProfesorODTO(profesor,
                        estudianteRepository.findByProfesor(profesor));
            }
            return oPersona.get().personaToPersonaOutputDto();
        }
    }

    @Override
    public List<PersonaOutputDto> getPersonaByUsuario(String name, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personaRepository.findByUsuario(name, pageRequest).stream().map(Persona::personaToPersonaOutputDto).toList();
    }

    @Override
    public PersonaOutputDto updatePersona(int id, PersonaInputDto persona) {
        checkPersonaNulls(persona);
        if (personaRepository.findById(id).isPresent()) {
            personaRepository.deleteById(id);
            Persona persona1 = new Persona(persona);
            persona1.setId(id);
            return personaRepository.save(persona1)
                    .personaToPersonaOutputDto();
        } else {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        }
    }

    @Override
    public void deletePersonaById(int id) {
        if (personaRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Registro con id " + id + " no encontrado");
        } else {
            personaRepository.deleteById(id);
        }
    }

    @Override
    public List<PersonaOutputDto> getAllPersonas(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personaRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Persona::personaToPersonaOutputDto).toList();
    }

    public List<PersonaOutputDto> getPersonQuery(HashMap<String, Object> conditions) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> query = cb.createQuery(Persona.class);
        Root<Persona> root = query.from(Persona.class);

        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field, value) -> {
            switch (field) {
                case "usuario", "name", "surname" -> predicates.add(cb.like(root.get(field),
                        "%" + value + "%"));
                case "createdDate" -> {
                    String dateCondition = (String) conditions.get("dateCondition");
                    switch (dateCondition) {
                        case "GREATER_THAN" -> predicates.add(cb.greaterThan(root.<Date>get(field), (Date) value));
                        case "LESS_THAN" -> predicates.add(cb.lessThan(root.<Date>get(field), (Date) value));
                        case "EQUALS" -> predicates.add(cb.equal(root.<Date>get(field), value));
                    }
                }
                case "orderBy" ->{
                    String orderBy = (String)conditions.get("orderBy");
                    switch (orderBy) {
                        case "user" -> query.orderBy(cb.asc(root.get("usuario")));
                        case "name" -> query.orderBy(cb.asc(root.get("name")));
                    }
                }
            }
        });
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        int pageSize = (int)conditions.get("pageSize");
        return entityManager
                .createQuery(query)
                .setMaxResults(pageSize)
                .setFirstResult((int)conditions.get("pageNumber")*pageSize)
                .getResultList()
                .stream()
                .map(Persona::personaToPersonaOutputDto)
                .toList();
    }

    public void checkPersonaNulls(PersonaInputDto persona) {
        if (persona.getUsuario().length() > 10) {
            throw new UnprocessableEntityException("Longitud de usuario no puede ser superior a 10 caracteres");
        }
        if (persona.getUsuario().length() < 6) {
            throw new UnprocessableEntityException("Longitud de usuario no puede ser inferior a 6 caracteres");
        }
        if (persona.getUsuario().isBlank()) {
            throw new UnprocessableEntityException("Usuario no puede ser null");
        }
        if (persona.getPassword().isBlank()) {
            throw new UnprocessableEntityException("Password no puede ser null");
        }
        if (persona.getName().isBlank()) {
            throw new UnprocessableEntityException("Name no puede ser null");
        }
        if (persona.getCompanyEmail().isBlank()) {
            throw new UnprocessableEntityException("Company_email no puede ser null");
        }
        if (persona.getPersonalEmail().isBlank()) {
            throw new UnprocessableEntityException("Personal_email no puede ser null");
        }
        if (persona.getCity().isBlank()) {
            throw new UnprocessableEntityException("City no puede ser null");
        }
    }
}

