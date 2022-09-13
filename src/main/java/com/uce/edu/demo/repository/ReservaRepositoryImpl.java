package com.uce.edu.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.uce.edu.demo.modelo.Reserva;

@Repository
@Transactional
public class ReservaRepositoryImpl implements IReservaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insertar(Reserva reserva) {
		// TODO Auto-generated method stub
		this.entityManager.persist(reserva);
	}

	@Override
	@Transactional
	public void actualizar(Reserva reserva) {
		// TODO Auto-generated method stub
		this.entityManager.merge(reserva);
	}

	@Override
	public Reserva buscar(Integer id) {
		// TODO Auto-generated method stub
		return this.entityManager.find(Reserva.class, id);
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		this.entityManager.remove(this.buscar(id));
	}

	@Override
    public Reserva buscarReservaDisponibleFechas(String placa, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // TODO Auto-generated method stub
        try { 
            TypedQuery<Reserva> myQuery = this.entityManager.createQuery("SELECT r FROM Reserva r JOIN r.vehiculo v WHERE v.placa = :placa AND "
            		+ "((r.fechaInicio < :fechaInicio) AND (r.fechaFin > :fechaFin)) OR "
            		+ "((r.fechaInicio BETWEEN :fechaInicio AND :fechaFin) OR (r.fechaFin BETWEEN :fechaInicio AND :fechaFin))", Reserva.class);
            myQuery.setParameter("placa", placa);
            myQuery.setParameter("fechaInicio", fechaInicio);
            myQuery.setParameter("fechaFin", fechaFin);
            return myQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
	
	@Override
	public Reserva buscarPorNumero(String numero) {
		// TODO Auto-generated method stub
		TypedQuery<Reserva> myQuery = this.entityManager.createQuery("SELECT r FROM Reserva r WHERE r.numero = :numero", Reserva.class);
		myQuery.setParameter("numero", numero);
		return myQuery.getSingleResult();
	}
	
	@Override
	public List<Reserva> reporteReservas() {
		// TODO Auto-generated method stub
		TypedQuery<Reserva> myQuery = this.entityManager.createQuery("SELECT r FROM Reserva r", Reserva.class);
		return myQuery.getResultList();
	}
	
}
