package mmcv.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import mmcv.model.Cuenta;
import mmcv.model.Transaccion;

@Stateless
public class TransaccionDAO {
	
	@Inject
	private EntityManager em;
	
	public boolean insertTransaccion(Transaccion t) {
		boolean estado;
		try {
			em.persist(t);
			estado = true;
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		}
		return estado;
	}
	
	public Cuenta selectCuenta(String numero) {
		return em.find(Cuenta.class, numero);
	}
	
	public boolean updateCuenta(Cuenta c) {
		boolean estado;
		try {
			em.merge(c);
			estado = true;
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		}
		return estado;
	}
	
	public List<Transaccion> getTransacciones(String numero){
		Cuenta cuenta = selectCuenta(numero);
		String jpql ="SELECT t FROM Transaccion t WHERE t.cuenta =: cuenta ORDER BY t.id";
		Query q = em.createQuery(jpql, Transaccion.class);
		q.setParameter("cuenta", cuenta);
		List<Transaccion> transacciones = q.getResultList();
		return transacciones;
	}

}
