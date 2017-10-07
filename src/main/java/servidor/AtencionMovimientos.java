package servidor;

import com.google.gson.Gson;

import estados.Estado;
import mensajeria.Comando;
import mensajeria.PaqueteDeMovimientos;
import mensajeria.PaqueteNpcs;

public class AtencionMovimientos extends Thread {
	
	private final Gson gson = new Gson();

	public AtencionMovimientos() {
		
	}

	public void run() {

		synchronized(this){
		
			try {
	
				while (true) {
			
					// Espero a que se conecte alguien
					wait();
					
					// Le reenvio la conexion a todos
					for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
					
						if(conectado.getPaquetePersonaje().getEstado() == Estado.estadoJuego){
						
							PaqueteDeMovimientos pdp = (PaqueteDeMovimientos) new PaqueteDeMovimientos(Servidor.getUbicacionPersonajes()).clone();
							pdp.setComando(Comando.MOVIMIENTO);
							PaqueteNpcs npcs = (PaqueteNpcs) new PaqueteNpcs(Servidor.getNpcs()).clone();
							npcs.setComando(Comando.ACTUALIZARNPCS);
							Servidor.log.append(npcs.getNpcs().get(1).getNombre() +"->"+npcs.getNpcs().get(1).getPosX() +"/"+ npcs.getNpcs().get(1).getPosY());
							synchronized (conectado) {
								conectado.getSalida().writeObject(gson.toJson(pdp));	
								conectado.getSalida().writeObject(gson.toJson(npcs));
							}
						}
					}
				}
			} catch (Exception e){
				Servidor.log.append("Falló al intentar enviar paqueteDeMovimientos \n");
				Servidor.log.append("Falló al intentar enviar paqueteNpcs \n");
			}
		}
	}
}
