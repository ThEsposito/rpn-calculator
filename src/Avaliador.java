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
import exceptions.OperacaoInvalidaException;
import exceptions.VariavelNaoDefinidaException;

public class Avaliador {
    private Variaveis vars;

    public Avaliador(){
        this.vars = new Variaveis();
    }

    public Avaliador(Variaveis vars){
        this.vars = vars;
    }

    //AVALIAR ENTRADA INFIXA
    // Irmão, tu acaba de criar o código mais indecifrável do planeta Terra
    public Boolean validarExpressao(String expressao){
        boolean temOperando = false;
        boolean ultimoFoiOperador = true; //permite negativo no começo da expressão

        if (expressao == null || expressao.isBlank()) { //se a expressão está vazia
            return false;
        }

        //valida se os parenteses estão balanceados:
        if (!isBalanceado(expressao)) {
            return false;
        }

        for(int i = 0; i < expressao.length(); i++){
            char c = expressao.charAt(i);
            if (Character.isWhitespace(c)) continue; // ignora os espaços
            
            if(Character.isAlphabetic(c)){
                temOperando = true;
                ultimoFoiOperador = false;
            }
            else if (isOperador(c)) {
                // dois operadores seguidos
                if(ultimoFoiOperador && c != '-'){ // permitir sinal negativo no início
                    return false;
                }
                ultimoFoiOperador = true;
            } else if (c == '(') {
                ultimoFoiOperador = true; // depois de '(' pode vir operador ou operando
            } else if (c == ')') {
                if (ultimoFoiOperador) return false; // não pode fechar depois de operador
                ultimoFoiOperador = false;
            } else {
                return false; // caractere inválido
            }
            
            // se o ultimo digitado foi operador, retorna falso
            if (ultimoFoiOperador) return false;

            // precisa ter pelo menos um operando
            return temOperando;
        }

    }


public String infixToPosfix(String infix){
    String result = "";
    Pilha<Character> p1 = new Pilha<>(infix.length());

    for (int i = 0; i < infix.length(); i++) {
        char atual = Character.toUpperCase(infix.charAt(i));

        if (Character.isWhitespace(atual)) continue; // ignora os espaços

        if (Character.isAlphabetic(atual)) {
            result += atual; // vai direto para saída
        }
        else if (isOperador(atual)){
            while (!p1.isEmpty() && isOperador(p1.top()) &&
                    getPrioridadeDoOperador(p1.top()) >= getPrioridadeDoOperador(atual)) {
                result += p1.pop();
            }
            p1.push(atual);
        }
        else if (atual == '('){
            p1.push(atual);
        }
        else if (atual == ')'){
            while (!p1.isEmpty() && p1.top() != '(') {
                result += p1.pop();
            }
            if (!p1.isEmpty() && p1.top() == '(') {
                p1.pop(); // descarta o '('
            } else {
                throw new IllegalArgumentException("Parêntese desbalanceado!");
            }
        }
        else {
            throw new IllegalArgumentException("Caractere inválido: " + atual);
        }
    }

    // Desempilha o que restou
    while (!p1.isEmpty()){
        if (p1.top() == '(') throw new IllegalArgumentException("Parêntese desbalanceado!");
        result += p1.pop();
    }
    return result;
}


    // Se não conseguirmos avaliar se todas as variáveis foram definidas antes da expressão,
    // podemos lançar essa exceção aqui (ou na infixToPosfix).
    // Pensei em meter o louco e, se a pilha estiver vazia onde não deveris estar, lançar a excecao
    // falando que a expressao ta invalida ksksksksksksk
    public Double avaliarPosfix(String posfix) throws VariavelNaoDefinidaException {
        // Character? Vou precisar empilhar Double tbem????
        Pilha<Double> p = new Pilha<>(posfix.length());

        for (int i = 0; i < posfix.length(); i++) {
            Character c = posfix.charAt(i);
            if (Character.isAlphabetic(c)) {
                p.push(vars.getValor(c));
            } else if (isOperador(c)) {
                double a = p.pop();
                double b = p.pop();

                double result;
                // Dá pra reduzir com lambda expressions, mas n lembro a sintaxe
                switch (c) {
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;

                    // TODO: lembrar de NÃO FAZER DIVISÃO POR ZERO. Validar isso em algum lugar
                    case '/':
                        result = a / b;
                        break;
                    case '^':
                        result = Math.pow(a, b);
                        break;
                    default:
                        throw new OperacaoInvalidaException("Não é possível realizar a operação '" + c + "'!");
                }

                p.push(result);
            }
        }
        if(p.size() == 1) return p.pop();
        else throw new RuntimeException("Erro inesperado!! Era pro último elemento da pilha ser o resultado! \nA operação pode ser inválida!: "+posfix);
    }

    public static boolean isBalanceado(String expressao) {
        Pilha<Character> p = new Pilha<>(expressao.length());

        for(int i=0; i<expressao.length(); i++){
            char c = expressao.charAt(i);

            if(c == '(') p.push(c);
            else if(c == ')'){
                if(p.isEmpty()) return false;
                else p.pop();
            } else {
                return false;
            }
        }
        return p.isEmpty();
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
