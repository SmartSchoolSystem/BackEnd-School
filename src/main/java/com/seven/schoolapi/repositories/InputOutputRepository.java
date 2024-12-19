package com.seven.schoolapi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.seven.schoolapi.entity.InputOutput;

public interface InputOutputRepository extends JpaRepository<InputOutput, Long> {

    // Busca por horário de entrada
    List<InputOutput> findByHorarioEntrada(String horarioEntrada);

    // Busca por horário de saída
    List<InputOutput> findByHorarioSaida(String horarioSaida);

    // Busca por horário de entrada e saída
    List<InputOutput> findByHorarioEntradaAndHorarioSaida(String horarioEntrada, String horarioSaida);

    // Busca por classroom (referência à entidade Class)
    List<InputOutput> findByClassroom_IdClass(Long idClass);

    // Busca por makerClassroom (referência à entidade MakerClassroom)
    List<InputOutput> findByMakerClassroom_Id(Long idMakerClassroom);

    // Busca pelo nome
    List<InputOutput> findByName(String name);
}
