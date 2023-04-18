package br.edu.ifce.tjw.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		ServerSocket server = null;

		try {

			server = new ServerSocket(1234);
			server.setReuseAddress(true);

			while (true) {

				Socket client = server.accept();

				System.out.println("Novo cliente conectado " + client.getInetAddress().getHostAddress());

				ClientHandler clientSock = new ClientHandler(client);

				new Thread(clientSock).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			PrintWriter out = null;
			BufferedReader in = null;
			try {

				out = new PrintWriter(clientSocket.getOutputStream(), true);

				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String line;

				while ((line = in.readLine()) != null) {
					System.out.printf("String enviada: " + line + "\n");
					System.out.println("Resultado da operação: \n");
					System.out.println(multiplicacao(line));
					out.println(multiplicacao(line));
					line = "";
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						clientSocket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String multiplicacao(String vetor) {

		String aux = vetor.substring(0, vetor.length() - 1);

		String[] subArray = aux.split(" ");
		int[] linha = new int[subArray.length];
		for (int i = 0; i < subArray.length; i++) {
			linha[i] = Integer.parseInt(subArray[i]);
		}

		int[][] a = new int[2][2];
		int[][] b = new int[2][2];
		int[][] c = new int[2][2];
		int x = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				a[i][j] = linha[x];
				x++;
			}
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				b[i][j] = linha[x];
				x++;
			}
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					c[i][j] = c[i][j] + a[i][k] * b[k][j];
				}
			}
		}
		aux = "[ ";
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
//				System.out.println(c[i][j]);
				aux += c[i][j] + " ][ ";
			}
		}
		aux = aux.substring(0, aux.length() - 2);
		
		return aux;
	}
}
