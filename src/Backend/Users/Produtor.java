package Backend.Users;

import java.util.Set;
import java.util.TreeSet;

public class Produtor extends User {
    private Set<Backend.Albums.AlbumEditado> projetos = new TreeSet<>();

    public Produtor(String name, String email, String username, String password, Backend.Users.Repos users,
            Backend.Instruments.Repos instruments, Backend.Albums.Repos albums, Backend.Sessions.Repos sessions) {
        super(name, email, username, password, users, instruments, albums, sessions);
    }

    public void addProjeto(Backend.Albums.AlbumEditado projeto) {
        if (projeto.getProducer() != null)
            return;
        projetos.add(projeto);
    }

    public void removeProjeto(Backend.Albums.AlbumEditado projeto) {
        projetos.remove(projeto);
    }

    public Set<Backend.Albums.AlbumEditado> getProjetos() {
        return projetos;
    }

    public Backend.Albums.AlbumEditado getProjeto(String titulo) {
        for (Backend.Albums.AlbumEditado projeto : projetos) {
            if (projeto.getTitulo().equals(titulo)) {
                return projeto;
            }
        }
        return null;
    }

}
