package escalonadorv2;

import java.util.ArrayList;

public interface Metodos {
	
	public Step execucao();
	public ArrayList<Processo> getProntos();
	public ArrayList<Processo> getBloqueados();
	public Object getFilas();


	enum Estado{
		nada,
		executando,
		trocaDeContexto,
		oscioso,
		finalizado
	}
}



