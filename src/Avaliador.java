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

public class Avaliador {
    private Variaveis vars;

    public Avaliador(){
        this.vars = new Variaveis();
    }

    public Avaliador(Variaveis vars){
        this.vars = vars;
    }

    public String infixToPosfix(String infix){
        String result = "";
        for(int i = 0; i < infix.length(); i++){

        }

        return result;
    }

    public Double avaliarPosfix(){


        return result2 = 0;
    }

    public Boolean validarExpressao(){ //avalia a entrada infixa
        boolean result3;


        return result3;
    }
}
