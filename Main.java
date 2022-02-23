import javax.swing.*;
import java.awt.*;

public class Main {
  public static void main(String[] args) {

    String option;

    boolean exit = false;

    do {

      option = menu();

      switch (option){
        case "Inserir Aluno":
          System.out.println("INSERIR");
          break;

        case "Atualizar Informação":
          System.out.println("ATUALIZAR");
          break;

        default:
          exit = exitMenu();
      }
    } while (!exit);
  }

  public static String menu() {

    String[] options = {"Inserir Aluno", "Atualizar Informação", "Sair"};

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
}