/**
 * 
 */
package com.example.asteroides;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Para consultar puntuaciones el cliente se conectará al servidor y le mandará
 * los caracteres PUNTUACIONES (solo se permite en mayúsculas) seguido de un
 * salto de línea. El servidor mandará todo el listado de puntuaciones,
 * separadas por caracteres de salto de línea. A continuación, se cerrará la
 * conexión.<br>
 * 
 * <code>
 * Cliente: PUNTUACIONES
 * <br> 
 * Servidor: 19000 Pedro Perez 17500 María Suarez 13000 Juan García </code>
 * <p>
 * Para almacenar una nueva puntuación el cliente se conectará al servidor y
 * mandará un texto con la puntuación obtenida, seguido de un salto de línea. El
 * servidor lo reconocerá como una nueva puntuación siempre que este texto no
 * sea PUNTUACIONES. En tal caso almacenará la puntuación y mandará los
 * caracteres OK seguidos de un salto de línea. A continuación se cerrará la
 * conexión. <br>
 * <code>
 * Cliente: 32000 Eva Gutierrez
 * <br>
 * Servidor: OK
 * </code>
 * 
 * @author robertome
 * 
 */
public class ServidorPuntuaciones {
	public static void main(String[] args) {
		List<String> puntuaciones = new ArrayList<String>();
		try {
			ServerSocket s = new ServerSocket(1234);
			System.out.println("Esperando conexiones...");
			while (true) {
				Socket cliente = s.accept();
				BufferedReader entrada = new BufferedReader(
						new InputStreamReader(cliente.getInputStream()));
				PrintWriter salida = new PrintWriter(new OutputStreamWriter(
						cliente.getOutputStream()), true);
				String datos = entrada.readLine();
				if (datos.equals("PUNTUACIONES")) {
					for (int n = 0; n < puntuaciones.size(); n++) {
						salida.println(puntuaciones.get(n));
					}
				} else {
					puntuaciones.add(0, datos);
					salida.println("OK");
				}
				cliente.close();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
