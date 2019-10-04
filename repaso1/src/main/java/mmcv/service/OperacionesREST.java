package mmcv.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import mmcv.model.Cuenta;
import mmcv.model.Transaccion;
import mmcv.modelREST.TransaccionREST;
import mmcv.on.TransaccionON;

@Path("/operaciones")
public class OperacionesREST {
	
	@Inject
	private TransaccionON ton;

	@GET
	@Path("/depositar")
	@Produces("application/json")
	public boolean depositar(@QueryParam("numero")String num, @QueryParam("monto")double mon) {
		boolean estado;
		try {
			if(ton.deposito(num, mon)==true) {
				estado = true;
			}else {
				estado = false;
			}
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		}
		return estado;
	}
	
	@GET 
	@Path("/retirar")
	@Produces("application/json")
	public boolean retirar(@QueryParam("numero")String num, @QueryParam("monto")double mon) {
		boolean estado;
		try {
			if(ton.retiro(num, mon)==true) {
				estado = true;
			}else {
				estado = false;
			}
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		}
		return estado;
	}
	
	@GET
	@Path("/transacciones")
	@Produces("application/json")
	public List<TransaccionREST> getTransacciones(@QueryParam("numeroCuenta")String numero){
		
		List<Transaccion> trans = ton.getTransacciones(numero);
		List<TransaccionREST> transacciones = new ArrayList<>();
		
		TransaccionREST tra = new TransaccionREST();
		
		for(int i=0;i<trans.size();i++) {
			tra = new TransaccionREST();
			tra.setId(trans.get(i).getId());
			tra.setFecha(trans.get(i).getFecha());
			tra.setTipo(trans.get(i).getTipo());
			if(trans.get(i).getTipo().equals("DEPOSITO")) {
				tra.setMonto("+ "+trans.get(i).getMonto());
			}
			if(trans.get(i).getTipo().equals("RETIRO")) {
				tra.setMonto("- "+trans.get(i).getMonto());
			}
			tra.setSaldo(trans.get(i).getSaldo());
			
			transacciones.add(tra);
			
		}
		return transacciones;
	}
	
	@GET
	@Path("/cuenta")
	@Produces("application/json")
	public Cuenta getCuenta(@QueryParam("numeroCuenta")String numero){
		Cuenta cue = new Cuenta();
		cue = ton.getCuenta(numero);
		System.out.println(cue.getNumero()+" "+cue.getNombres()+" "+cue.getFechaApertura());
		return cue;
	}
	
}

