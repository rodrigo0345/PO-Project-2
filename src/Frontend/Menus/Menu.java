package Frontend.Menus;

public interface Menu {

    void mostrarMenu();

    void executeOption(Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Users.Repos users, Backend.Sessions.Repos sessions);
}
