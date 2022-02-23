import javax.swing.*;

public class Main {
  public static void main(String[] args) {

    //Constantes
    final int TAMANHO = 1;

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
          break;

        case "Ver Alunos":
          verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          break;

        default:
          exit = exitMenu();
      }
    } while (!exit);
  }

  /*Funcionalidades*/

  private static int inserirAluno(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    nomes[nElems] = JOptionPane.showInputDialog("Insira o nome do(a) aluno(a):");

    do{

      turmas[nElems] = JOptionPane.showInputDialog("Insira a turma do aluno(a) "+ nomes[nElems] +":");

      if (turmas[nElems].length() != 1){
        JOptionPane.showMessageDialog(null, "A turma só pode ser constituida por uma letra!", "Turma inválida!", JOptionPane.WARNING_MESSAGE);
      }

    }while (turmas[nElems].length() != 1);

    numeros[nElems] = Integer.parseInt(JOptionPane.showInputDialog("Insira o numero do(a) aluno(a) " + nomes[nElems] + ":"));
    algNotas[nElems] = Integer.parseInt(JOptionPane.showInputDialog("Insira a nota de Algoritmia do(a) aluno(a) " + nomes[nElems] + ":"));
    javaNotas[nElems] = Integer.parseInt(JOptionPane.showInputDialog("Insira a nota de Java do(a) aluno(a) " + nomes[nElems] + ":"));
    vbNotas[nElems] = Integer.parseInt(JOptionPane.showInputDialog("Insira a nota de Visual Basic do(a) aluno(a) " + nomes[nElems] + ":"));

    nElems++;

    JOptionPane.showMessageDialog(null, "Aluno(a) inserido(a) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);

    return nElems;

  }

  private static void verAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    String[] options;

    int option;

    options = new String[] {"Ordenar Alunos", "Voltar"};
    option = JOptionPane.showOptionDialog(null, tabelaAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems), "Ver Alunos", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

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

    String[] options = {"Inserir Aluno", "Atualizar Informação", "Ver Alunos", "Sair"};

    String selectedOption;

    selectedOption = (String) JOptionPane.showInputDialog(null, "Selecione o que pretende fazer:\n\n", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

    return selectedOption;

  }

  private static boolean exitMenu() {

    String[] options = {"Sim", "Não"};

    JLabel lbl = new JLabel("Quer mesmo sair?");

    JPanel panel = new JPanel();
    panel.add(lbl);

    int selectedOption = JOptionPane.showOptionDialog(null, panel, "The Title", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[1]);

    if (selectedOption == 1){
      return false;
    }

    return true;

  }

  private static String tabelaAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems){

    String tabela = "";

    tabela = "<html> <head> <style> table { font-family: arial, sans-serif; border-collapse: collapse; width: 750px; } td, th { border: 1px solid #dddddd; text-align: left; padding: 5px; } tr:nth-child(even) { background-color: #dddddd; font-weight: bold} </style> </head> <body> <table> <tr> <th>Turma</th> <th>Número</th> <th>Nome</th> <th>Alg</th> <th>Java</th> <th>VB</th> <th>Final</th> </tr>";

    for (int i = 0; i < nElems; i++) {
      tabela += "<tr> <td>"+ turmas[i].trim() +"</td> <td>"+ numeros[i] +"</td> <td>"+ nomes[i].trim() +"</td> <td>"+ algNotas[i] +"</td> <td>"+ javaNotas[i] +"</td> <td>"+ vbNotas[i] +"</td> <td>"+ notaFinal(algNotas[i], javaNotas[i], vbNotas[i], 0.30, 0.40, 0.30) +"</td> </tr>";
    }

    tabela += "</table> </body> </html>";

    return tabela;

  }

  private static int notaFinal(int algNota, int javaNota, int vbNota, double pAlg, double pJava, double pVb) {

    int mediaPond = (int) ((algNota * pAlg) + (javaNota * pJava) + (vbNota * pVb));

    return  mediaPond;

  }

}