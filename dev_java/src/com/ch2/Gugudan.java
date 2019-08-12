package com.ch2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Gugudan {
	
	public int getProcessID(int port) throws IOException {
        Process ps = new ProcessBuilder("cmd", "/c", "netstat -a -o").start();
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(":" + port)) {
                while (line.contains("  ")) {
                    line = line.replaceAll("  ", " ");
                }
                int pid = Integer.valueOf(line.split(" ")[5]);
                ps.destroy();
                return pid;
            }
        }
        return -1;
    }

	public static void main(String[] args) {
		Gugudan a = new Gugudan();
		try {
			System.out.println(a.getProcessID(10530));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
