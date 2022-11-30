package Frontend.Menus;

import java.util.Scanner;

import Backend.Users.Produtor;

public class Mprodutor implements Menu {
    private int option;
    private Produtor user;

    public Mprodutor(Produtor user) {
        this.user = user;
    }

    @Override
    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);

        // escrever aqui os menus
        System.out.println("Menu de Produtor - Logged as " + user.getUsername());

        try {
            option = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid option");
        }
    }

    public Produtor getUser() {
        return user;
    }

    @Override
    public void executeOption(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums,
            Backend.Users.Repos users) {
        switch (option) {
            case 1:
                System.out.println("Cadastrar produtor");
                break;
            case 2:
                System.out.println("Listar produtores");
                break;
            case 3:
                System.out.println("Editar produtor");
                break;
            case 4:
                System.out.println("Excluir produtor");
                break;
            case 5:
                System.out.println("Voltar");
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }

    }

}
