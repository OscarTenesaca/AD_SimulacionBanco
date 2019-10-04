package mmcv.on;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import mmcv.dao.TransaccionDAO;
import mmcv.model.Cuenta;
import mmcv.model.Transaccion;

@Stateless
public class TransaccionON {

	@Inject
	private TransaccionDAO tdao;
	
	public boolean deposito(String numero, double montoRecibido) {
		boolean estado;
		try {
			Cuenta c = tdao.selectCuenta(numero);
			Transaccion t = new Transaccion();
			
			c.setSaldoDisponible(c.getSaldoDisponible()+montoRecibido);
			
			t.setMonto(montoRecibido);
			t.setFecha(new Date()+"");
			t.setSaldo(c.getSaldoDisponible());
			t.setTipo("DEPOSITO");
			t.setCuenta(c);
			
			tdao.insertTransaccion(t);
			tdao.updateCuenta(c);
			
			estado = true;
			
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		}
		return estado;
	}
	
	public boolean retiro(String numero, double montoRecibido) {
		boolean estado;
		try {
			Cuenta c = tdao.selectCuenta(numero);
			Transaccion t = new Transaccion();
			
			double resultado = c.getSaldoDisponible()-montoRecibido;
			
			if(resultado>0) {
				c.setSaldoDisponible(c.getSaldoDisponible()-montoRecibido);
				
				t.setMonto(montoRecibido);
				DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
				t.setFecha(hourdateFormat.format(new Date())+"");
				t.setSaldo(c.getSaldoDisponible());
				t.setTipo("RETIRO");
				t.setCuenta(c);
				
				tdao.insertTransaccion(t);
				tdao.updateCuenta(c);
				
				estado = true;
			}else {
				estado = false;
			}
			return estado;
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		}
		return estado;
	}
	
	public List<Transaccion> getTransacciones(String numero){
		return tdao.getTransacciones(numero);
	}
	
	public Cuenta getCuenta(String numero) {
		return tdao.selectCuenta(numero);
	}
}
