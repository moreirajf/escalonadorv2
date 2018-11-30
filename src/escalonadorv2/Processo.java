package escalonadorv2;


public class Processo {
	private boolean IObound;
	private int tempo,tempoRestante,prioridade,nivelOriginal;
	private String label;
	private int tempoBloqueio;
	
	public int getTempoBloqueio() {
		return tempoBloqueio;
	}

	public void setTempoBloqueio(int tempoBloqueio) {
		this.tempoBloqueio = tempoBloqueio;
	}

	public Processo(boolean iObound, int tempo, int prioridade, String label) {
		super();
		IObound = iObound;
		this.tempo = tempo;
		this.tempoRestante=tempo;
		this.prioridade = prioridade;
		this.nivelOriginal = prioridade;
		this.label = label;
		
	}

	public boolean isIObound() {
		return IObound;
	}

	public void setIObound(boolean iObound) {
		IObound = iObound;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getTempoRestante() {
		return tempoRestante;
	}

	public void setTempoRestante(int tempoRestatnte) {
		this.tempoRestante = tempoRestatnte;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public int getNivelOriginal() {
		return nivelOriginal;
	}

	public void setNivelOriginal(int nivelOriginal) {
		this.nivelOriginal = nivelOriginal;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	public void decremTempo() {
		this.tempoRestante--;
	}

	public void decremTempo(int qtd) {
		this.tempoRestante-=qtd;
	}

	public boolean isFinished() {
		return this.tempoRestante<=0;
	}
	public void incremNivel() {
		this.prioridade++;
	}
	public void resetNivel() {
		this.prioridade = this.nivelOriginal;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return label+" - "+prioridade+" - "+tempoRestante;
	}
	
	
}
