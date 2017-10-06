package comandos;

import java.io.IOException;

import mensajeria.PaqueteNpcs;
import servidor.Servidor;

public class ActualizarNpcs extends ComandosServer {

	@Override
	public void ejecutar() {
		escuchaCliente.setPaqueteNpcs((PaqueteNpcs) (gson.fromJson((String) cadenaLeida, PaqueteNpcs.class)));
		Servidor.getConector().getNpcs(escuchaCliente.getPaqueteNpcs().getNpcs());
	
		try {
			escuchaCliente.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteNpcs()));
		} catch (IOException e) {
			Servidor.log.append("Choteadisimo, murio con ActualizarNpcs!");
			e.printStackTrace();
		}

		synchronized(Servidor.atencionMovimientos){
			Servidor.atencionMovimientos.notify();
		}

	}

}
