package serviceDomain;

import com.google.gson.Gson;

import Entity.Todos;
import Interface.IWorkerConvertObject;

public class WorkerConvertObject implements Runnable {

	private IWorkerConvertObject accessPlaceHolderUseCase;
	Gson gson = null;

	public WorkerConvertObject(IWorkerConvertObject accessPlaceHolderUseCase) {
		this.accessPlaceHolderUseCase = accessPlaceHolderUseCase;
		gson = new Gson();

	}

	public void run() {

		while (this.accessPlaceHolderUseCase.getContinua()) {
			String todostJson = this.accessPlaceHolderUseCase.getElementListPlaceHolderTodos();
//			System.out.println(todostJson);
			if (todostJson != null) {
				Todos todos = gson.fromJson(todostJson, Todos.class);
			}

		}
	}

}
