package comandos;

import mensajeria.PaqueteNpc;
import servidor.Servidor;

public class ActualizarNpc extends ComandosServer {

	@Override
	public void ejecutar() {
		Servidor.log.append("Llegamos aca??? ActualizarNpc");
		escuchaCliente.setPaqueteNpc((PaqueteNpc) gson.fromJson(cadenaLeida, PaqueteNpc.class));
		Servidor.log.append("Se asesino al pobre mob" + escuchaCliente.getPaqueteNpc().getIdPersonaje() + "Requiescat in pace");
		Servidor.getConector().deleteNpc(escuchaCliente.getPaqueteNpc());
	}

}