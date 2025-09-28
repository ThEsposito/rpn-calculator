public class Variaveis {
    private Double[] values;

    public Variaveis(){
        values = new Double[26];
    }

    public void atribuir(char var, double valor) throws IllegalArgumentException {
        int idx = this.getIndex(var);

        this.values[idx] = valor;
    }
    
    public double getValor(char var) throws IllegalArgumentException {
        int idx = this.getIndex(var);

        return this.values[idx];
    }

    public boolean existe(char var) throws IllegalArgumentException{
        int idx = this.getIndex(var);

        // Como é um vetor de Double (um wrapper) os valores são inicializados como null
        return values[idx] != null;
    }

    public void reset(){
        values = new Double[26];
    }

    // Instrução VARS da nossa calculadora
    public String listar(){
        if(this.isEmpty()) return "Nenhuma variável foi atribuída.";

        int lista;
        for(Double value: values){
            if(value != null) {
                System.out.println();
            }
        }
    }

    private int getIndex(char var) throws IllegalArgumentException {
        var = Character.toUpperCase(var);

        // Usamos a tabela ASCII para pegar o índice pro nosso vetor:
        // A = 65 e Z = 90
        if(var < 'A' || var > 'Z') {
            throw new IllegalArgumentException("A entrada deve ser uma letra entre A e Z!!");
        }

        return var - 'A';
    }
    private boolean isEmpty(){
        for(Double v : values){
            if (v != null) return false;
        }
        return true;
    }
}
