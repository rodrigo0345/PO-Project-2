package Frontend.Menus;

public class Mprodutor implements Menu {
    private int option;

    @Override
    public void mostrarMenu() {

    }

    @Override
    public void executeOption() {
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
