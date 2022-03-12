/*
 * Desenvolvido por: Grupo 4 "Os Grandes"
 * Versão: 1.0
 * Nome da versão: Caramel
 * */
import java.io.File;
import java.util.Formatter;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;

public class Main {
  public static void main(String[] args) {

    //Constantes
    final int TAMANHO = 50;

    //Variaveis
    int nElems = 0;
    int option;

    boolean exit = false;

    //Vetores
    int[] numeros = new int[TAMANHO];
    int[] algNotas = new int[TAMANHO];
    int[] javaNotas = new int[TAMANHO];
    int[] vbNotas = new int[TAMANHO];

    String[] turmas = new String[TAMANHO];
    String[] nomes = new String[TAMANHO];

    //Programa Principal

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("ToggleButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

      // ways to remove it from other controls...
      UIManager.put("CheckBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("TabbedPane.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("RadioButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
      UIManager.put("Slider.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

      // figure out combobox
      UIManager.put("ComboBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

    } catch (Exception e) {
      e.printStackTrace();
    }

    do {

      option = menu();

      switch (option){
        case 1:
          if(nElems < TAMANHO){
            nElems = inserirAluno(turmas, nomes, numeros, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Numero máximo de alunos atingido!", "Falha ao inserir Aluno", JOptionPane.ERROR_MESSAGE);
          }
          break;

        case 2:
          if (nElems != 0){
            editarAluno(turmas, nomes, numeros, algNotas, javaNotas, vbNotas, nElems);
          }else{
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case 3:
          if (nElems > 0){
            verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);
          }else {
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case 4:
          if (nElems > 0){
            if (apagarAluno(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems) == 1) {
              JOptionPane.showMessageDialog(null, "Aluno eliminado");
              nElems--;
            }
          } else {
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem Alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case 5:
          if (nElems != 0){
            nElems = atualizarInfo(turmas, nomes, numeros, algNotas, javaNotas, vbNotas, nElems);
          }else{
            JOptionPane.showMessageDialog(null, "Não existem alunos inseridos!", "Sem alunos!", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case 6:
          if(nElems < numeros.length){
            nElems = importarDados(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          }else {
            JOptionPane.showMessageDialog(null, "Não existem dados");
          }
          break;

        case 7:
          exportarDados(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
          break;

        default:
          exit = exitMenu();
      }
    } while (!exit);
  }

  /*Funcionalidades*/

  private static int inserirAluno(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    //Variáveis
    String nome;
    String turma = "";
    String aux;

    int numero = 0;
    int algNota = 0;
    int javaNota = 0;
    int vbNota = 0;

    boolean isValid = false;
    boolean isCanceled = false;

    //Guarda o nome do aluno se não for cancelado(JOptionPane() quando cancelado retorna null).
    do {
      nome = JOptionPane.showInputDialog(null, "Insira o nome do(a) aluno(a):", "Inserir Aluno",  JOptionPane.PLAIN_MESSAGE);

      if (nome == null){
        isCanceled = true;
      }else if (nome.trim().equals("")){
        JOptionPane.showMessageDialog(null, "Por favor preencha o campo do nome!", "Nome inválido!", JOptionPane.WARNING_MESSAGE);
      }else{
        nome = nome.trim();
      }

    }while (!isCanceled && nome.equals(""));

    //Guarda a turma do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
    do {

      if(!isCanceled){
        turma = JOptionPane.showInputDialog(null, "Insira a turma do aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

        if (turma == null) {
          isCanceled = true;
        } else if (turma.trim().equals("")) {
          JOptionPane.showMessageDialog(null, "Por favor preencha o campo da turma!", "Turma inválida!", JOptionPane.WARNING_MESSAGE);
        } else {
          turma = turma.toUpperCase();
        }
      }

    } while (!isCanceled && turma.equals(""));

    //Guarda o numero do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
    do {

      if (!isCanceled){
        aux = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

        if (aux == null){
          isCanceled = true;
        }else {
          numero = checkNumber(aux, nome);

          if (numero != -2){
            if(pesquisar(numeros, nElems, numero) != -1){
              JOptionPane.showMessageDialog(null, "O aluno que está a tentar inserir já se econtra guardado!", "Numero inválido!", JOptionPane.WARNING_MESSAGE);
            }else {
              isValid = true;
            }

            if (checkDigits(numero) != 7){
              JOptionPane.showMessageDialog(null, "Por favor introduza um numero válido!", "Numero inválido!", JOptionPane.WARNING_MESSAGE);
            }
          }else {
            isCanceled = true;
          }
        }
      }

    }while (!isCanceled && (checkDigits(numero) != 7 || !isValid));

    //Guarda a nota de algoritmia do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
    if (!isCanceled){

      aux = JOptionPane.showInputDialog(null, "Insira a nota de Algoritmia do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

      if (aux == null){
        isCanceled = true;
      }else {
        algNota = checkNota(aux, nome, "Algoritmia");

        if (algNota == -1){
          isCanceled = true;
        }
      }
    }

    //Guarda a nota de java do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
    if (!isCanceled){

      aux = JOptionPane.showInputDialog(null, "Insira a nota de Java do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

      if (aux == null){
        isCanceled = true;
      }else {
        javaNota = checkNota(aux, nome, "Java");

        if (javaNota == -1){
          isCanceled = true;
        }
      }
    }

    //Guarda a nota de visual basic do aluno se não for cancelado ou se não tiver sido cancelado anteriormente
    if (!isCanceled){

      aux = JOptionPane.showInputDialog(null, "Insira a nota de Visual Basic do(a) aluno(a) " + nome + ":", "Inserir Aluno", JOptionPane.PLAIN_MESSAGE);

      if (aux == null){
        isCanceled = true;
      }else {
        vbNota = checkNota(aux, nome, "Visual Basic");

        if (vbNota == -1){
          isCanceled = true;
        }
      }
    }

    //Se não tiver sido cancelado até agora insere o aluno no vetor e incremeta o nElems
    if (!isCanceled){
      //Inserir os alunos no vetor
      nomes[nElems] = nome;
      turmas[nElems] = turma;
      numeros[nElems] = numero;
      algNotas[nElems] = algNota;
      javaNotas[nElems] = javaNota;
      vbNotas[nElems] = vbNota;

      //Incrementa o nElems
      nElems++;

      //Mostra ao utilizador que o aluno foi inserido
      JOptionPane.showMessageDialog(null, "Aluno(a) inserido(a) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }

    //Retorna o nElems para poder ser atualizado no programa principal
    return nElems;

  }

  private static void editarAluno(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    //Variáveis
    int numPos;
    int option;
    int auxInt;
    int cofirmChange;

    boolean isCanceled = false;
    boolean isValid = false;

    String msg;
    String auxString;

    //Vetores
    Object[] msgContent;
    Object[] newContent;

    //Componentes Java Swing
    JCheckBox[] camposEditar = {new JCheckBox("Nome"), new JCheckBox("Turma"), new JCheckBox("Numero"), new JCheckBox("Nota de Algoritmia"), new JCheckBox("Nota de Java"), new JCheckBox("Nota de Visual Basic")};

    //Recebe o numero do aluno que se pretende editar
    numPos = checkNumberUpdate(JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) que pretende editar:", "Editar",JOptionPane.PLAIN_MESSAGE), numeros, nElems);

    //Se o aluno existir edita-o
    if(numPos != -1){

      //Cria a mensagem com as checkboxes para editar apenas os campos que são pretendidos
      msg = "Que campos pretende editar:";
      msgContent = new Object[] {msg, camposEditar};
      option = JOptionPane.showConfirmDialog ( null,  msgContent,  "Editar", JOptionPane.OK_CANCEL_OPTION);

      //Se o utilizador nao tiver cancelado cria um vetor com o conteudo antigo que vai ser alterado para dar a opção ao utilizador para cancelar.
      if(option == JOptionPane.OK_OPTION){

        newContent = new Object[] {nomes[numPos], turmas[numPos], numeros[numPos], algNotas[numPos], javaNotas[numPos], vbNotas[numPos]};

        //Se o utilizador tiver escolhido alterar o nome pede o novo valor
        if (camposEditar[0].isSelected()){
          do{
            auxString = JOptionPane.showInputDialog(null, "Insira o novo nome para o(a) aluno(a) '"+ newContent[0] +"':", "Editar Nome", JOptionPane.PLAIN_MESSAGE);

            if(auxString != null){
              if (!auxString.trim().equals("")){
                newContent[0] = auxString.trim();
              }else {
                JOptionPane.showMessageDialog(null, "Por favor preencha o campo do nome!", "Nome inválido!", JOptionPane.WARNING_MESSAGE);
              }
            }else {
              isCanceled = true;
            }
          }while (Objects.equals(auxString, ""));
        }

        if (camposEditar[1].isSelected()){
          if (!isCanceled){
            do{
              auxString = JOptionPane.showInputDialog("Insira a turma  do(a) aluno(a) '"+ newContent[0] +"':");

              if(auxString != null){
                if (!auxString.trim().equals("")){
                  newContent[1] = auxString.trim();
                }else {
                  JOptionPane.showMessageDialog(null, "Por favor preencha o campo da turma!", "Turma inválida!", JOptionPane.WARNING_MESSAGE);
                }
              }else {
                isCanceled = true;
              }
            }while (Objects.equals(auxString, ""));
          }
        }

        //Se o utilizador tiver escolhido alterar o numero pede o novo valor
        if (camposEditar[2].isSelected()){
          if(!isCanceled){
            do {
              auxString = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) '"+ newContent[0] +"':", "", JOptionPane.PLAIN_MESSAGE);

              if(auxString == null){
                isCanceled = true;
              }else {

                auxInt = checkNumber(auxString, newContent[0].toString());

                if (auxInt != -2){
                  if(auxInt != Integer.parseInt(newContent[2].toString())){
                    if(pesquisar(numeros, nElems, auxInt) != -1){
                      JOptionPane.showMessageDialog(null, "O aluno que está a tentar inserir já se econtra guardado!", "Numero inválido!", JOptionPane.WARNING_MESSAGE);
                    }

                    if (checkDigits(auxInt) != 7){
                      JOptionPane.showMessageDialog(null, "Por favor introduza um numero válido!", "Numero inválido!", JOptionPane.WARNING_MESSAGE);
                    }else {
                      newContent[2] = auxInt;
                      isValid = true;
                    }
                  }else {
                    cofirmChange = JOptionPane.showConfirmDialog(null, "O numero introduzido é o mesmo! Pretende continuar?", "Numero inalterado!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (cofirmChange == JOptionPane.YES_OPTION){
                      isValid = true;
                    }

                    if (cofirmChange == JOptionPane.CANCEL_OPTION){
                      isCanceled = true;
                    }

                  }
                }else {
                  isCanceled = true;
                }
              }
            }while (!isValid && !isCanceled);
          }
        }

        //Se o utilizador tiver escolhido alterar a nota de algoritmia pede o novo valor
        if (camposEditar[3].isSelected()){

          isValid = false;

          if (!isCanceled){
            do {
              auxString = JOptionPane.showInputDialog(null, "Insira a nota de Algoritmia do(a) aluno(a) '"+ newContent[0] +"':", "", JOptionPane.PLAIN_MESSAGE);

              if(auxString == null){
                isCanceled = true;
              }else {

                auxInt = checkNota(auxString, newContent[0].toString(), "Algoritmia");

                if (auxInt != -1){
                  newContent[3] = auxInt;
                  isValid = true;
                }else {
                  isCanceled = true;
                }
              }
            }while (!isValid && !isCanceled);
          }
        }

        //Se o utilizador tiver escolhido alterar a nota de java pede o novo valor
        if (camposEditar[4].isSelected()){
          isValid = false;

          if (!isCanceled){
            do {
              auxString = JOptionPane.showInputDialog(null, "Insira a nota de Java do(a) aluno(a) '"+ newContent[0] +"':", "", JOptionPane.PLAIN_MESSAGE);

              if(auxString == null){
                isCanceled = true;
              }else {

                auxInt = checkNota(auxString, newContent[0].toString(), "Java");

                if (auxInt != -1){
                  newContent[4] = auxString;
                  isValid = true;
                }else {
                  isCanceled = true;
                }
              }
            }while (!isValid);
          }
        }

        //Se o utilizador tiver escolhido alterar a nota de visual basic pede o novo valor
        if (camposEditar[5].isSelected()){
          isValid = false;

          if (!isCanceled){
            do {
              auxString = JOptionPane.showInputDialog(null, "Insira a nota de Visual Basic do(a) aluno(a) '"+ newContent[0] +"':", "", JOptionPane.PLAIN_MESSAGE);

              if(auxString == null){
                isCanceled = true;
              }else {

                auxInt = checkNota(auxString, newContent[0].toString(), "Visual Basic");

                if (auxInt != -1){
                  newContent[5] = auxString;
                  isValid = true;
                }else {
                  isCanceled = true;
                }
              }
            }while (!isValid);
          }
        }

        //Quando o utilizador tivre acabado de inserir os novos valores insere-os nos vetores originais e mostra uma mensagem ao utilizador.
        if(!isCanceled){
          nomes[numPos] = newContent[0].toString();
          turmas[numPos] = newContent[1].toString().toUpperCase();
          numeros[numPos] = Integer.parseInt(newContent[2].toString());
          algNotas[numPos] = Integer.parseInt(newContent[3].toString());
          javaNotas[numPos] = Integer.parseInt(newContent[4].toString());
          vbNotas[numPos] = Integer.parseInt(newContent[5].toString());

          JOptionPane.showMessageDialog(null, "Aluno editado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
        }
      }
    }
  }

  private static void verAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {

    //Variáveis
    int option;

    //Vetores
    String[] options;

    //Os botões que são mostrados no fundo da tabela são inseridos num vetor de opções que será utilizado depois no JOptionPane da tabela
    options = new String[] {"Página Anterior", "Página Seguinte","Ordenar Alunos", "Voltar"};
    option = JOptionPane.showOptionDialog(null, tabelaAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina), "Ver Alunos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

    //As opções retornam uma Integer que corresponde ao indice do vetor das opções.
    //Caso sejam escolhidas as opções de "Página Anterior", "Página Seguinte" ou "Ordenar Alunos" chama a respetiva função.
    if (option == 0){
      paginaAnterior(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

    if (option == 1){
      paginaSeguinte(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

    if(option == 2){
      ordenarClassificacao(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }

  }

  private static int atualizarInfo(String[] turmas, String[] nomes, int[] numeros, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    // Variáveis
    int cop = nElems;
    int canceled;
    int numero;
    int numNew = 0;
    int numUpdated = 0;
    int optionConfirm;

    boolean isCanceled = false;

    //Componentes Java Swing
    JFileChooser file = new JFileChooser();

    try {

      //Importa os alunos e atualiza os dados nos vetores
      do {
        file.setDialogTitle("Importar Alunos");
        file.setDialogType(JFileChooser.OPEN_DIALOG);
        file.setAcceptAllFileFilterUsed(false);
        file.setPreferredSize(new Dimension(700, 500));

        file.addChoosableFileFilter(new FileNameExtensionFilter("Ficheiros de Texto (.txt)", "txt"));

        canceled = file.showOpenDialog(null);

        if (canceled == JFileChooser.CANCEL_OPTION){
          isCanceled = true;
        }

      }while (file.getSelectedFile() == null && !isCanceled);

      if (!isCanceled){
        Scanner fichFunc = new Scanner(file.getSelectedFile());

        while(fichFunc.hasNextLine() && nElems < numeros.length){

          String linha = fichFunc.nextLine();
          String[] vetLinha = linha.split(":");

          numero = pesquisar(numeros, nElems, Integer.parseInt(vetLinha[1]));

          if(numero != -1){
            if(!Objects.equals(turmas[numero], vetLinha[0]) || numeros[numero] != Integer.parseInt(vetLinha[1].trim()) || !Objects.equals(nomes[numero], vetLinha[2]) || algNotas[numero] != Integer.parseInt(vetLinha[3].trim()) || javaNotas[numero] != Integer.parseInt(vetLinha[4].trim()) || vbNotas[numero] != Integer.parseInt(vetLinha[5].trim())){

              turmas[numero] = vetLinha[0];
              numeros[numero] = Integer.parseInt(vetLinha[1].trim());
              nomes[numero] = vetLinha[2];

              algNotas[numero] = Integer.parseInt(vetLinha[3].trim());
              javaNotas[numero] = Integer.parseInt(vetLinha[4].trim());
              vbNotas[numero] = Integer.parseInt(vetLinha[5].trim());

              numUpdated ++;
            }
          }else {
            numNew++;
          }
        }
        fichFunc.close();

        //Se existerem alunos novos pergunta ao utilizador se os quer adicionar. Se sim adiciona senão mostra só uma mensagem de sucesso
        if(numNew != 0){
          optionConfirm = JOptionPane.showConfirmDialog(null, "Foram atualizados " + numUpdated + " ficheiros.\nExistem " + numNew + " fichieros que ainda não estão guardados\n\nPretende guardá-los?", "Ficheiros atualizados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

          if (optionConfirm == JOptionPane.YES_OPTION){

            Scanner fichNew = new Scanner(file.getSelectedFile());

            while(fichNew.hasNextLine() && nElems < numeros.length){

              String linha = fichNew.nextLine();
              String[] vetLinha = linha.split(":");

              if(pesquisar(numeros, nElems, Integer.parseInt(vetLinha[1])) == -1){

                turmas[nElems] = vetLinha[0];
                numeros[nElems] = Integer.parseInt(vetLinha[1].trim());
                nomes[nElems] = vetLinha[2];

                algNotas[nElems] = Integer.parseInt(vetLinha[3].trim());
                javaNotas[nElems] = Integer.parseInt(vetLinha[4].trim());
                vbNotas[nElems] = Integer.parseInt(vetLinha[5].trim());

                nElems++;

              }
            }

            JOptionPane.showMessageDialog(null, numUpdated + " aluno(s) atualizado(s) e "+ numNew +" adicionado(s) com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            fichFunc.close();
          }else {
            JOptionPane.showMessageDialog(null, numUpdated + " alunos atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
          }

        }else{
          if(numUpdated == 0){
            JOptionPane.showMessageDialog(null, "Não foram atualizados quaisquer dados!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
          }else {
            JOptionPane.showMessageDialog(null, numUpdated + " dado(s) atualizado(s)!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
          }
        }
      }
    }catch (FileNotFoundException e){
      JOptionPane.showMessageDialog(null, "Ficheiro não encontrado!", "Ficheiro inválido!", JOptionPane.ERROR_MESSAGE);
    }

    //Retorna o novo nElems
    return nElems;
  }

  private static int apagarAluno(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {
    String numero;
    int pos;
    boolean isDeleted = false;

    do {
      numero = JOptionPane.showInputDialog(null, "Qual o número do aluno que pretende eliminar", "Apagar Aluno", JOptionPane.PLAIN_MESSAGE);

      if (numero != null){
        pos = pesquisar(numeros, nElems, Integer.parseInt(numero));

        if (pos != -1) {
          for (int x = pos; x < nElems - 1; x++) {
            numeros[x] = numeros[x + 1];
            nomes[x] = nomes[x + 1];
            turmas[x] = turmas[x + 1];
            algNotas[x] = algNotas[x + 1];
            javaNotas[x] = javaNotas[x + 1];
            vbNotas[x] = vbNotas[x + 1];
          }
          isDeleted = true;
        }else {
          JOptionPane.showMessageDialog(null, "Não há qualquer aluno com o número introduzido!", "Sem Alunos!", JOptionPane.WARNING_MESSAGE);
        }
      }
    }while (!isDeleted && numero != null);

    if (numero != null){
      return 1;
    }else {
      return 0;
    }

  }

  private static void ordenarClassificacao(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {


    //Vetores
    String[] options;

    options = new String[] {"Turma", "Nota de Algoritmia", "Nota de Java", "Nota de VB", "Nota final", "Voltar"};
    int x = JOptionPane.showOptionDialog(null, "Escolha o método de ordenação", "Ordenar", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

    if (x == 0){
      ordenarTurma(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }
    if (x == 1){
      algNotas(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }
    if (x == 2){
      Javanotas(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }
    if (x == 3){
      NotasVB(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }
    if (x == 4){
      MediaFinal(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems);
    }
    if (x == 5){
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);
    }
  }


  private static void MediaFinal(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas,
                                 int[] vbNotas, int nElems) {
    int notas1;
    int numeros2;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    for (int x = 0; x < nElems - 1; x++) {
      for (int y = x + 1; y < nElems; y++) {
        if(notaFinal(algNotas[y], javaNotas[y], vbNotas[y]) > notaFinal(algNotas[x], javaNotas[x], vbNotas[x])) {
          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;
        }

        if (numeros[x] > numeros[y] && notaFinal(algNotas[y], javaNotas[y], vbNotas[y]) == notaFinal(algNotas[x], javaNotas[x], vbNotas[x])){

          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

        }
      }
    }
    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);

  }

  private static void NotasVB(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas,
                              int[] vbNotas, int nElems) {
    int notas1;
    int numeros2;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    for (int x = 0; x < nElems - 1; x++) {
      for (int y = x + 1; y < nElems; y++) {
        if(vbNotas[y] > vbNotas[x]) {
          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;
        }

        if (numeros[x] > numeros[y] && algNotas[y] == algNotas[x]){

          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

        }
      }
    }
    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);


  }

  private static void Javanotas(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas,
                                int[] vbNotas, int nElems) {
    int notas1;
    int numeros2;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    for (int x = 0; x < nElems - 1; x++) {
      for (int y = x + 1; y < nElems; y++) {
        if(javaNotas[y] > javaNotas[x]) {
          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;
        }

        if (numeros[x] > numeros[y] && algNotas[y] == algNotas[x]){

          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

        }
      }
    }
    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);

  }


  private static void algNotas(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas,
                               int[] vbNotas, int nElems) {
    int notas1;
    int numeros2;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    for (int x = 0; x < nElems - 1; x++) {
      for (int y = x + 1; y < nElems; y++) {
        if(algNotas[y] > algNotas[x]) {
          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;
        }

        if (numeros[x] > numeros[y] && algNotas[y] == algNotas[x]){

          numeros2 = numeros[x];
          numeros[x] = numeros[y];
          numeros[y]= numeros2;

          notas1 = algNotas[x];
          algNotas[x] = algNotas[y];
          algNotas[y]= notas1;

          auxTurmas = turmas[x];
          turmas[x] = turmas[y];
          turmas[y] = auxTurmas;

          auxNomes = nomes[x];
          nomes[x] = nomes[y];
          nomes[y] = auxNomes;

          auxJavaNota = javaNotas[x];
          javaNotas[x] = javaNotas[y];
          javaNotas[y] = auxJavaNota;

          auxVbNota = vbNotas[x];
          vbNotas[x] = vbNotas[y];
          vbNotas[y] = auxVbNota;

        }
      }
    }
    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);
  }

  private static void ordenarTurma(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas,
                                   int[] vbNotas, int nElems) {
    //Variáveis
    int auxNumero;
    int auxAlgNota;
    int auxJavaNota;
    int auxVbNota;

    String auxNomes;
    String auxTurmas;

    //Um conjunto de loops for é utilizado para comparar e trocar quando necessário as entradas dos u«alunos nos vetores
    for (int i = 0; i < nElems; i++) {
      for (int j = i + 1; j < nElems; j++) {
        //Ordena os alunos por ordem crescente de turma
        if(turmas[j].compareToIgnoreCase(turmas[i]) < 0){

          //Troca os nomes dos alunos
          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          //Troca as turmas dos alunos
          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          //Troca o numero dos alunos
          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          //Troca a nota de Algoritmia dos alunos
          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          //Troca a nota de Java dos alunos
          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          //Troca a nota de Visual Basic dos alunos
          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;
        }

        //Se as turmas forem iguais oredena por ordem crescente de numero
        if (numeros[i] > numeros[j] && turmas[j].compareToIgnoreCase(turmas[i]) == 0){

          //Troca os nomes dos alunos
          auxNomes = nomes[i];
          nomes[i] = nomes[j];
          nomes[j] = auxNomes;

          //Troca as turmas dos alunos
          auxTurmas = turmas[i];
          turmas[i] = turmas[j];
          turmas[j] = auxTurmas;

          //Troca o numero dos alunos
          auxNumero = numeros[i];
          numeros[i] = numeros[j];
          numeros[j] = auxNumero;

          //Troca a nota de Algoritmia dos alunos
          auxAlgNota = algNotas[i];
          algNotas[i] = algNotas[j];
          algNotas[j] = auxAlgNota;

          //Troca a nota de Java dos alunos
          auxJavaNota = javaNotas[i];
          javaNotas[i] = javaNotas[j];
          javaNotas[j] = auxJavaNota;

          //Troca a nota de Visual Basic dos alunos
          auxVbNota = vbNotas[i];
          vbNotas[i] = vbNotas[j];
          vbNotas[j] = auxVbNota;

        }
      }
    }

    //Chama a função verAlunos() para mostrar a tabela ordenada
    verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, 1);
  }

  private static int importarDados(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems) {

    int cop = nElems;
    int canceled;

    boolean isCanceled = false;

    JFileChooser file = new JFileChooser();

    try {

      do {
        file.setDialogTitle("Importar Alunos");
        file.setDialogType(JFileChooser.OPEN_DIALOG);
        file.setAcceptAllFileFilterUsed(false);
        file.setPreferredSize(new Dimension(700, 500));

        file.addChoosableFileFilter(new FileNameExtensionFilter("Ficheiros de Texto (.txt)", "txt"));

        canceled = file.showOpenDialog(null);

        if (canceled == JFileChooser.CANCEL_OPTION){
          isCanceled = true;
        }

      }while (file.getSelectedFile() == null && !isCanceled);

      if (!isCanceled){
        Scanner fichFunc = new Scanner(file.getSelectedFile());

        while(fichFunc.hasNextLine() && nElems < numeros.length){

          String linha = fichFunc.nextLine();
          String[] vetLinha = linha.split(":");

          if(pesquisar(numeros, nElems, Integer.parseInt(vetLinha[1])) == -1){

            turmas[nElems] = vetLinha[0];
            numeros[nElems] = Integer.parseInt(vetLinha[1].trim());
            nomes[nElems] = vetLinha[2];

            algNotas[nElems] = Integer.parseInt(vetLinha[3].trim());
            javaNotas[nElems] = Integer.parseInt(vetLinha[4].trim());
            vbNotas[nElems] = Integer.parseInt(vetLinha[5].trim());

            nElems++;

          }
        }
        fichFunc.close();

        if (nElems - cop != 0){
          JOptionPane.showMessageDialog(null, (nElems-cop) +  " Dado(s) carregados(s)");
        }else {
          JOptionPane.showMessageDialog(null, "Não foram carregados dados!");
        }
      }
    }catch (FileNotFoundException e){
      JOptionPane.showMessageDialog(null, "Ficheiro não encontrado!", "Ficheiro inválido!", JOptionPane.ERROR_MESSAGE);
    }

    return nElems;

  }

  private static void exportarDados(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems){

    int canceled;

    boolean isCanceled = false;
    boolean isSet = false;

    File file = null;

    JFileChooser fileChooser = new JFileChooser();

    do {
      fileChooser.setDialogTitle("Exportar Alunos");
      fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.setPreferredSize(new Dimension(700, 500));
      fileChooser.setSelectedFile(new File("alunos.txt"));

      fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Ficheiros de Texto (.txt)", "txt"));

      canceled = fileChooser.showSaveDialog(null);

      if (canceled == JFileChooser.CANCEL_OPTION){
        isCanceled = true;
      }

      if(checkValidFile(fileChooser.getSelectedFile().toString()) == 2 && fileChooser.getSelectedFile() != null){
        file = new File(fileChooser.getSelectedFile().toString() + ".txt");
        isSet = true;
      }

    }while (fileChooser.getSelectedFile() == null && !isCanceled && checkValidFile(fileChooser.getSelectedFile().toString()) == -1);

    if (!isSet && !isCanceled){
      file = fileChooser.getSelectedFile();
    }

    if(!isCanceled){
      try {
        Formatter fichAlunos = new Formatter(file);

        for (int x = 0; x < nElems; x++) {
          if(x == 0) {
            fichAlunos.format("%s:%d:%s:%d:%d:%d", turmas[x], numeros[x], nomes[x], algNotas[x], javaNotas[x], vbNotas[x]);
          }else{
            fichAlunos.format("\n%s:%d:%s:%d:%d:%d", turmas[x], numeros[x], nomes[x], algNotas[x], javaNotas[x], vbNotas[x]);
          }
        }

        fichAlunos.close();
        JOptionPane.showMessageDialog(null, nElems +  " Dado(s) exportado(s)");
      }catch (FileNotFoundException e){
        JOptionPane.showMessageDialog(null, "Não foi possivel criar o ficheiro!", "Ficheiro inválido!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /* Menus e outras necessidades*/
  public static int menu() {

    //Variáveis
    int selectedOption = -1;
    int result;

    //Vetores
    String[] options = {"Inserir Aluno", "Editar Aluno", "Ver Alunos", "Apagar Aluno", "Atualizar Dados", "Carregar Alunos", "Exportar Alunos", "Sair"};

    Object[] msg;

    //Componentes Java Swing
    JComboBox optionBox = new JComboBox(options);

    //Criar a mensagem do JOptionPane com as opções
    msg = new Object[]{"Selecione o que pretende fazer:", optionBox};

    //Mostra o drop-down do menu com as opções inseridas no vetor acima. Retorna uma String com o valor da opção que foi escolhida
    result = JOptionPane.showConfirmDialog(null, msg, "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

    //Se o utilizador não tiver fechado a janela verifica que opção foi escolhida
    if(result != JOptionPane.CLOSED_OPTION){
      selectedOption = optionBox.getSelectedIndex();
    }

    //Se o utilizador carregar no cancelar, a função Menu() envia a opção "Sair" para mostrar o menu de saída.
    if (selectedOption == options.length-1 || selectedOption == -1){
      return 0;
    }

    //Se o utilizador não tiver cancelado retorna a opção que foi selecionada.
    return selectedOption + 1;


  }

  private static boolean exitMenu() {

    //Vetores
    String[] options = {"Sim", "Não"};

    //Componentes Java Swing
    JLabel lbl = new JLabel("Quer mesmo sair?");

    JPanel panel = new JPanel();
    panel.add(lbl);

    //Mostra um JOptionPane para confirmar que o utilizador quer mesmo sair que retorna o indice do vetor das opções ou -1 se for cancelado
    int selectedOption = JOptionPane.showOptionDialog(null, panel, "Sair", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[1]);

    //Se o utilizador tiver cancelado ou selecionado "Não" retorna false senão retorna true e sai do programa
    return selectedOption != 1 && selectedOption != -1;

  }

  private static String tabelaAlunos(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina){

    //Variáveis
    int numIn;
    int numFin;

    StringBuilder tabela;

    //Define primeiro indice da página
    numIn = (pagina * 10) - 10;

    //Define o ultimo indice da página
    numFin = Math.min(numIn + 10, nElems);

    //Adiciona o inicio de uma tabela HTML com estilização e cabeçalho
    tabela = new StringBuilder("<html> <head> <style> table { font-family: arial, sans-serif; border-collapse: collapse; width: 500px; margin-right: 0,} td, th { border: 2px solid #dddddd; text-align: left; margin-right: 0;} tr:nth-child(even) { background-color: #000000; font-weight: bold} </style> </head> <body> <table> <tr> <th>Turma</th> <th>Número</th> <th>Nome</th> <th>Alg</th> <th>Java</th> <th>VB</th> <th>Final</th> </tr>");

    //Adiciona á tabela uma nova fila com os dados do aluno corespondente ao indice do loop for
    for (int i = numIn; i < numFin; i++) {
      tabela.append("<tr> <td>").append(turmas[i].trim()).append("</td> <td>").append(numeros[i]).append("</td> <td>").append(nomes[i].trim()).append("</td> <td>").append(algNotas[i]).append("</td> <td>").append(javaNotas[i]).append("</td> <td>").append(vbNotas[i]).append("</td> <td>").append(notaFinal(algNotas[i], javaNotas[i], vbNotas[i])).append("</td> </tr>");
    }

    //Adiciona o fim de uma tabela HTML e o numero da página no centro
    tabela.append("</table> <br><p style='width:100%; text-align: center'> Página - ").append(pagina).append("</p><br> </body> </html>");

    //Retorna a tabela construida
    return tabela.toString();

  }

  private static int notaFinal(int algNota, int javaNota, int vbNota) {

    //Variáveis
    final double pAlg = 0.30;
    final double pJava = 0.40;
    final double pVb = 0.30;

    //Calcula a nota final do aluno e retorna-a
    return (int) ((algNota * pAlg) + (javaNota * pJava) + (vbNota * pVb));

  }

  private static void paginaAnterior(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {

    //Verifica se está na primeira página. Caso não esteja anda uma página para trás, senão continua na primeira. No final mostra a tabela na página escolhida.
    if (pagina != 1){
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, (pagina - 1));
    }else{
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

  }

  private static void paginaSeguinte(String[] turmas, int[] numeros, String[] nomes, int[] algNotas, int[] javaNotas, int[] vbNotas, int nElems, int pagina) {

    //Verifica se está na ultima página. Caso não esteja anda uma página para a frente, senão continua na ultima. No final mostra a tabela na página escolhida.
    if(pagina != Math.ceil((double) nElems/10 )){
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, (pagina + 1));
    }else {
      verAlunos(turmas, numeros, nomes, algNotas, javaNotas, vbNotas, nElems, pagina);
    }

  }

  private static int checkNumber(String input, String nome) {

    //Variáveis
    int output = -1;

    //Recebe uma string da inserção do numero. Se conseguir converter para Integer retorna o numero, senão pede o numero outra vez.
    while (output == -1){
      try {
        output = Integer.parseInt(input);
      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Numero inválido!", "Falha ao inserir numero", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -2
        if (input == null){
          return -2;
        }

      }

      //Se o numero for negativo pede o numero outra vez
      if(output <= -1){
        output = -1;

        JOptionPane.showMessageDialog(null, "Numero inválido!", "Falha ao inserir numero", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -2
        if (input == null){
          return -2;
        }
      }
    }

    //Quando o utilizador introduzir um numero válido retorna-o
    return output;

  }

  private static int checkNumberUpdate(String input, int[] numeros, int nElems) {

    //Variáveis
    int output = -1;

    //Recebe uma string da inserção do numero. Se conseguir converter para Integer retorna o numero, senão pede o numero outra vez.
    while (output == -1) {
      if (input != null){
        try {
          output = Integer.parseInt(input);
        } catch (NumberFormatException e) {

          output = -1;

          JOptionPane.showMessageDialog(null, "Numero inválido!", "O numero que inseriu não é válido!", JOptionPane.WARNING_MESSAGE);

          input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) que pretende atualizar:", "Atualizar", JOptionPane.PLAIN_MESSAGE);

        }

        //Quando a output é um numero, verifica se o numero tem 7 digitos caso tenha verifica se ja esta inserido. Se não estivre inserido retorna -1
        if (output != -1 && checkDigits(output) != 7) {
          output = -1;

          JOptionPane.showMessageDialog(null, "Numero inválido!", "O numero que inseriu não é válido!", JOptionPane.WARNING_MESSAGE);

          input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) que pretende atualizar:", "Atualizar", JOptionPane.PLAIN_MESSAGE);

        } else if (output != -1 && pesquisar(numeros, nElems, output) == -1) {
          output = -1;

          JOptionPane.showMessageDialog(null, "Aluno não encontrado!", "O numero que inseriu não corresponde a nenhum aluno!", JOptionPane.WARNING_MESSAGE);

          input = JOptionPane.showInputDialog(null, "Insira o numero do(a) aluno(a) que pretende atualizar:", "Atualizar", JOptionPane.PLAIN_MESSAGE);
        }
      }else {
        return -1;
      }
    }

    //Quando o utilizador introduzir um numero válido retorna-o
    return pesquisar(numeros, nElems, output);

  }

  private static int checkNota(String input, String nome, String notaNome) {

    //Variáveis
    int output = -1;

    boolean isNumber;

    //Recebe uma string da inserção da nota. Se conseguir converter para Integer retorna a nota, senão pede a nota outra vez.
    while (output == -1){

      //Põe a flag a falso para refazer a verificação
      isNumber = false;

      //Se o utilizador cancelar retorna -1
      if (input == null){
        return -1;
      }

      try {
        output = Integer.parseInt(input);

        isNumber = true;

      }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);

        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -1
        if (input == null){
          return -1;
        }
      }

      //Se for um numero e não for uma nota válida (0-20) pede a nota outra vez
      if(isNumber && (output < 0 || output > 20)){
        output = -1;

        JOptionPane.showMessageDialog(null, "Nota inválida!", "Falha ao inserir nota", JOptionPane.WARNING_MESSAGE);
        input = JOptionPane.showInputDialog(null, "Insira a nota de "+ notaNome +" do(a) aluno(a) " + nome + ":", "", JOptionPane.PLAIN_MESSAGE);

        //Se o utilizador cancelar retorna -1
        if (input == null){
          return -1;
        }
      }

    }

    //Quando for introduzida uma nota válida retorna-a
    return output;

  }

  private static int checkDigits(int num){

    //Variáveis
    int count = 0;

    //Conta o numero de digitos de um numero
    while (num != 0) {
      num /= 10;
      count++;
    }

    //Retorna o numero de digitos contado
    return count;

  }

  private static int checkValidFile(String saveFilePath) {

    //Se o ficheiro não acabar com ".txt" retorna 2 se for uma pasta retorna -1 para o valor poder ser tratado na funçaõ exportarDados()
    if (!saveFilePath.endsWith(".txt")){
      if (saveFilePath.endsWith("/")){
        return -1;
      }else {
        return 2;
      }
    }
    return 1;
  }

  private static int pesquisar(int[] numeros, int nElems, int numero) {
    int pos = 0;
    while (pos < nElems && numero != numeros[pos]) {
      pos++;
    }
    if (pos < nElems) {
      return pos;
    } else {
      return -1;
    }
  }
}
