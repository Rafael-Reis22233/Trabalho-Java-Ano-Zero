/*
* Desenvolvido por: Grupo 4 "Os Grandes"
* Versão: 1.0
* Ultima Modificação: 23-02-2022 22:14
* */
import javax.swing.*;

public class main {
  public static void main(String[] args) {

    //Constantes
    final int TAMANHO = 50;

    //Variaveis
    int nElems = 0;

    String option;

    boolean exit = false;

    //Vetores
    int[] numeros = new int[TAMANHO];
    int[] algNotas = new int[TAMANHO];
    int[] javaNotas = new int[TAMANHO];
    int[] vbNotas = new int[TAMANHO];

    String[] turmas = new String[TAMANHO];
    String[] nomes = new String[TAMANHO];

    do {

      option = menu();

      switch (option){
        case "Inserir Aluno":
          if(nElems < TAMANHO){
            nElems = inserirAluno(turmas, nomes, numeros, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Numero máximo de alunos atingido!", "Falha ao inserir Aluno", JOptionPane.ERROR_MESSAGE);
          }
          break;

        case "Atualizar Informação":
          System.out.println("ATUALIZAR");
          //TODO: atualizarAluno()
          break;

        case "Ver Alunos":
          if (nElems > 0){
            verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case "Carregar Alunos":
          System.out.println("CARREHAR ALUNOS");
          //TODO: carregarAlunos()
          break;

        case "Exportar Alunos":
          System.out.println("EXPORTAR ALUNOS");
          //TODO: exportarAlunos()
          break;

        default:
          exit = exitMenu();
      }
    } while (!exit);
  }

  /*Funcionalidades*/

  private static int inserirAluno(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    do {
      nomes[nElems] = JOptionPane.showInputDialog(null, "Insira o nome do(a) aluno(a):", "Inserir Aluno",  JOptionPane.PLAIN_MESSAGE);

      if (nomes[nElems].length() == 0){
        JOptionPane.showMessageDialog(null, "Por favor preencha o campo do nome!", "Nome inválido!", JOptionPane.WARNING_MESSAGE);
      }

    }while (nomes[nElems] == null || nomes[nElems].length() == 0);

    do{

      turmas[nElems] = JOptionPane.showInputDialog(null, "Insira a turma do aluno(a) "+ nomes[nElems] +":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE).toUpperCase();

      if (turmas[nElems].length() != 1){
        JOptionPane.showMessageDialog(null, "A turma só pode ser constituida por uma letra!", "Turma inválida!", JOptionPane.WARNING_MESSAGE);
      }

    }while (turmas[nElems].length() != 1);

    numeros[nElems] = checkNumber(JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nomes[nElems] + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE), nomes[nElems]);
    algNotas[nElems] = checkNota(JOptionPane.showInputDialog(null, "Insira a nota de Algoritmia do(a) aluno(a) " + nomes[nElems] + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE), nomes[nElems], "Algoritmia");
    javaNotas[nElems] = checkNota(JOptionPane.showInputDialog(null, "Insira a nota de Java do(a) aluno(a) " + nomes[nElems] + ":", "Inserir Aluno",JOptionPane.PLAIN_MESSAGE), nomes[nElems], "Java");
    vbNotas[nElems] = checkNota(JOptionPane.showInputDialog(null, "Insira a nota de Visual Basic do(a) aluno(a) " + nomes[nElems] + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE), nomes[nElems], "Visual Basic");

    nElems++;

    JOptionPane.showMessageDialog(null, "Aluno(a) inserido(a) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);

    return nElems;

  }

  private static void verAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    String[] options;

    int option;

    options = new String[] {"Ordenar Alunos", "Voltar"};
    option = JOptionPane.showOptionDialog(null, tabelaAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems), "Ver Alunos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

    if(option == 0){
      ordenarClassificacao(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }

  }

  private static void ordenarClassificacao(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    int auxNumero;
    int auxAlgNota;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    for (int i = 0; i < nElems; i++) {
      for (int j = i + 1; j < nElems; j++) {
        if(turmas[j].compareToIgnoreCase(turmas[i]) < 0){

          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;
        }

        if (numeros[i] > numeros[j] && turmas[j].compareToIgnoreCase(turmas[i]) == 0){

          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;

        }
      }
    }

    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);

  }

  /* Menus e outras necessidades*/
  public static String menu() {

    String[] options = {"Inserir Aluno", "Atualizar Informação", "Ver Alunos", "Carregar Alunos", "Exportar Alunos", "Sair"};

    String selectedOption;

    selectedOption = (String) JOptionPane.showInputDialog(null, "Selecione o que pretende fazer:\n\n", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

    return selectedOption;


  }

  private static boolean exitMenu() {

    String[] options = {"Sim", "Não"};

    JLabel lbl = new JLabel("Quer mesmo sair?");

    JPanel panel = new JPanel();
    panel.add(lbl);

    int selectedOption = JOptionPane.showOptionDialog(null, panel, "The Title", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[1]);

    return selectedOption != 1;

  }

  private static String tabelaAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems){

    String tabela;

    tabela = "<html> <head> <style> table { font-family: arial, sans-serif; border-collapse: collapse; width: 500px; margin-right: 0,} td, th { border: 2px solid #dddddd; text-align: left; margin-right: 0;} tr:nth-child(even) { background-color: #000000; font-weight: bold} </style> </head> <body> <table> <tr> <th>Turma</th> <th>Número</th> <th>Nome</th> <th>Alg</th> <th>Java</th> <th>VB</th> <th>Final</th> </tr>";

    for (int i = 0; i < nElems; i++) {
      tabela += "<tr> <td>"+ turmas[i].trim() +"</td> <td>"+ numeros[i] +"</td> <td>"+ nomes[i].trim() +"</td> <td>"+ algNotas[i] +"</td> <td>"+ javaNotas[i] +"</td> <td>"+ vbNotas[i] +"</td> <td>"+ notaFinal(algNotas[i], javaNotas[i], vbNotas[i], 0.30, 0.40, 0.30) +"</td> </tr>";
    }

    tabela += "</table> </body> </html>";

    return tabela;

  }

  private static int notaFinal(int algNota, int javaNota, int vbNota, double pAlg, double pJava, double pVb) {

    return (int) ((algNota * pAlg) + (javaNota * pJava) + (vbNota * pVb));

  }

  private static int checkNumber(String input, String nome) {

    int output = -1;

    while (output == -1){
      try {
        output = Integer.parseInt(input);
      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Numero inválido!", "Falha ao inserir numero", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);
      }
    }

    return output;

  }

  private static int checkNota(String input, String nome, String notaNome) {

    int output = -1;

    while (output == -1){
      try {
        output = Integer.parseInt(input);
      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);
      }

      if(output < 0 || output > 20){
        output = -1;

        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);
        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);
      }

    }

    return output;

  }
}
