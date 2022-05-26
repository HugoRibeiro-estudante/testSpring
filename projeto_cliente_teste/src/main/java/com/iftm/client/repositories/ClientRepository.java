package com.iftm.client.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	/*
	 * Métodos propostos nas atividades de JUNIT
	 */

	public void deleteByCpf(String cpfExistente);

	public Optional<Client> findByCpf(String string);
	

	public void deleteByIncomeGreaterThan(double salarioI);

	public List<Client> findByIncomeGreaterThan(double salarioI);

	public List<Client> findByCpfLike(String parteCpf);

	public List<Client> findByCpfStartingWith(String parteCpf);
	
	/*
	 * Métodos propostos para trabalhar com novas funcionalidades na atividade de Mockito
	 */
	Page<Client> findByIncome(Double income, Pageable pageable);
	
}
