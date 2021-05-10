package useCase;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import Interface.IWorkerConvertObject;
import Interface.IWorkerFetchData;
import serviceDomain.WorkerConvertObject;
import serviceDomain.WorkerFetchData;

public class PlaceHolderUseCase implements IWorkerFetchData, IWorkerConvertObject {
	private ArrayList<String> listPlaceHolderTodos = new ArrayList<String>();
	private boolean continua = true;
	private int totalIteracoes = 0;

	public void execute(String url) throws InterruptedException {
		Instant inicioA = Instant.now();
		int max = 200;
		String urlTodos = url + "todos/";
		Thread threadA = this.initializesFetchData(urlTodos, max);
		Thread threadA2 = this.initializesFetchData(urlTodos, max);
		Thread threadB = this.initializesFetchData(urlTodos, max);

		max = 10;
		String urlUsers = url + "users/";
		Thread threadUsers = this.initializesFetchData(urlUsers, max);
		
		
		
		Thread threadC = this.initializesConvertObject();

		threadA.join();
		threadA2.join();
		threadB.join();
		threadC.join();
		Instant fimA = Instant.now();
		Duration duracaoA = Duration.between(inicioA, fimA);
		System.out.println("Duração:" + duracaoA.toMillis() + " milisegundos. Média:"
				+ duracaoA.toMillis() / this.totalIteracoes + " milisegundos por iteração");

	}

	private Thread initializesConvertObject() {
		Thread thread = new Thread(new WorkerConvertObject(this));
		thread.start();
		return thread;

	}

	private Thread initializesFetchData(String url, int max) {
		Thread thread = new Thread(new WorkerFetchData(url, max, this));
		thread.start();
		return thread;
	}

	public synchronized void setListPlaceHolderTodos(String todosJSon) {
		this.listPlaceHolderTodos.add(todosJSon);
		this.maximumIterations();
		this.totalIteracoes++;
		System.out.println(this.listPlaceHolderTodos.size());
	}

	public synchronized boolean getContinua() {
		return this.continua;
	}

	public synchronized String getElementListPlaceHolderTodos() {
		if (this.listPlaceHolderTodos.size() > 0) {
			return this.listPlaceHolderTodos.remove(0);
		}
		return null;
	}

	public void maximumIterations() {
		if (this.listPlaceHolderTodos.size() >= 100 || this.totalIteracoes == 100) {
			this.continua = false;
		}
	}

}
