/*
Um algoritmo para conversão de uma expressão infixa qualquer para posfixa seria:
• Inicie com uma pilha vazia;
• Realize uma varredura na expressão infixa, copiando todos os identificadores encontrados
diretamente para a expressão de saída.
a) Ao encontrar um operador:
1. Enquanto a pilha não estiver vazia e houver no seu topo um operador com prioridade maior
ou igual ao encontrado, desempilhe o operador e copie-o na saída;
2. Empilhe o operador encontrado;
b) Ao encontrar um parêntese de abertura, empilhe-o;
c) Ao encontrar um parêntese de fechamento, remova um símbolo da pilha e copie-o na saída,
até que seja desempilhado o parêntese de abertura correspondente.
• Ao final da varredura, esvazie a pilha, movendo os símbolos desempilhados para a saída.
*/

import data.structures.Pilha;

public class Avaliador {
    private Variaveis vars;

    public Avaliador(){
        this.vars = new Variaveis();
    }

    public Avaliador(Variaveis vars){
        this.vars = vars;
    }
/*
1. Enquanto a pilha não estiver vazia e houver no seu topo um operador com prioridade maior
ou igual ao encontrado, desempilhe o operador e copie-o na saída;
2. Empilhe o operador encontrado;
 */
    public String infixToPosfix(String infix){
        String result = "";
        Pilha<Character> p1 = new Pilha(infix.length());

        for(int i = 0; i < infix.length(); i++){
            char atual = Character.toUpperCase(infix.charAt(i));

            if(Character.isAlphabetic(atual)){ //se achar um identificador
                result += atual; //concatenar em resultado
            }
            else if(this.isOperador(atual)){
                while(!p1.isEmpty() && isOperador(p1.top()) && getPrioridadeDoOperador(p1.top()) >= getPrioridadeDoOperador(atual)){
                        result += p1.pop(); // desempilha operadores de maior ou igual prioridade
                }
                p1.push(atual); // empilha o operador atual ATENÇÃO A TESTES DE PRIORIDADES IGUAIS
            }
        }
        // Desempilha o que restou
        while (!p1.isEmpty()) {
            result += p1.pop();
        }

        return result;
    }

    // TODO: implementar esse método
    public Double avaliarPosfix(){
        return 0.0;
    }

    public Boolean validarExpressao(){ //avalia a entrada infixa
        boolean result = true;
        return result;
    }

    private boolean isOperador(char c){
        char[] operadores = {'+','-', '*','/', '^'};

        for(char operador : operadores) {
            if(c == operador) return true;
        }
        return false;
    }

    private int getPrioridadeDoOperador(char operador) throws IllegalArgumentException {
        if(!isOperador(operador)) throw new IllegalArgumentException("O parâmetro deve ser um operador!");
        switch(operador){
            case '+', '-':
                return 1;

            case '/', '*':
                return 2;

            case '^':
                return 3;
        }
        throw new IllegalArgumentException("Operador inválido!");
    }
}
