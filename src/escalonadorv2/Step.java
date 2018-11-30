package escalonadorv2;
public class Step {
    private Metodos.Estado state;
    private int duracao;
    private Processo px;


    public Processo getPx() {
        return px;
    }

    public Step(Metodos.Estado state, int duracao, Processo px) {
        this.state = state;
        this.duracao = duracao;
        this.px=px;
    }


    public Metodos.Estado getState() {
        return state;
    }

    public int getDuracao() {
        return duracao;
    }

    @Override
    public String toString() {

        if(state.equals(Metodos.Estado.executando)) return "EXECUTANDO "+px.getLabel()+" POR "+duracao+" UQ";
        return state.toString().toUpperCase();
    }
}
