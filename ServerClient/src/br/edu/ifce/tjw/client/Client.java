package br.edu.ifce.tjw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		
		String vetor = "";
		
		try (Socket socket = new Socket("localhost", 1234)) {

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			Scanner sc = new Scanner(System.in);
			String line = null;

			while (!"exit".equalsIgnoreCase(line)) {
				
				System.out.println("Preencha a matriz A");
				for (int i = 0; i < 4; i++) {
					vetor = vetor + sc.nextLine() + " ";
				}
				
				System.out.println("Preencha a matriz B");
				for (int i = 4; i < 8; i++) {
					vetor = vetor + sc.nextLine() + " ";
				}

				out.println(vetor);
				out.flush();

				System.out.println("Mensagem enviada: " + vetor);
				System.out.println("Server replied " + in.readLine());
			}

			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
