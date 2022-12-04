package Backend.Sessions;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class Session implements Serializable {
    private static long serialVersionUID = 4L;

    private LocalDate date;
    private UUID id = UUID.randomUUID();
    private boolean completed = false;

    public Session(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Session other = (Session) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // used for exceptions
    public void setId(UUID id2) {
        this.id = id2;
    }

    public Object getAccepted() {
        return null;
    }

    public void setAccepted(boolean b) {
    }

}
