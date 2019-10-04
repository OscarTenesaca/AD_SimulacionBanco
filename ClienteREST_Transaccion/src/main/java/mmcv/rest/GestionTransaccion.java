package mmcv.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import mmcv.modelREST.Cuenta;
import mmcv.modelREST.TransaccionREST;

public class GestionTransaccion {
	
	private String WS_GET_DEPOSITO="http://localhost:8080/repaso1/server/operaciones/depositar?";
	private String WS_GET_RETIRO="http://localhost:8080/repaso1/server/operaciones/retirar?";
	private String WS_GET_TRANSACCIONES="http://localhost:8080/repaso1/server/operaciones/transacciones?numeroCuenta=";
	private String WS_GET_CUENTA="http://localhost:8080/repaso1/server/operaciones/cuenta?numeroCuenta=";
	
	public List<TransaccionREST> getTransacciones(String numero){
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(WS_GET_TRANSACCIONES+numero);
		List<TransaccionREST> transacciones = target.request().get(new GenericType<List<TransaccionREST>>() {});
		
		client.close();
		
		return transacciones;
	}
	
	public Cuenta getCuenta(String numero) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(WS_GET_CUENTA+numero);
		Cuenta cuenta = target.request().get(Cuenta.class);
		client.close();
		return cuenta;
	}
	
	public boolean depositar(String numero, double monto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(WS_GET_DEPOSITO+"numero="+numero+"&monto="+monto);
		boolean resp = target.request().get(boolean.class);
		System.out.println(resp);
		return resp;
	}

	public boolean retirar(String numero, double monto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(WS_GET_RETIRO+"numero="+numero+"&monto="+monto);
		boolean resp = target.request().get(boolean.class);
		System.out.println(resp);
		return resp;
	}
}
