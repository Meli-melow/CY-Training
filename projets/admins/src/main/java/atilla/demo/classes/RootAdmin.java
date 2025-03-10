package atilla.demo.classes;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@Entity
@Data


public class RootAdmin extends Admin {

    private int code ;

    public RootAdmin(int id, String nom, String prenom, String mail, String mdp, Filiere filiere) {
        super(id, nom, prenom, mail, mdp, filiere);
    }

    public RootAdmin() {
    }




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RootAdmin(int code) {
        this.code = code;
    }



    public void certifierAdmin(Admin admin, Matiere matiere) {

        admin.addCertif(matiere);

    }

    public void removeCertification ( Admin admin , Matiere matiere){
        admin.getCertifs().remove(matiere);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootAdmin rootAdmin)) return false;
        if (!super.equals(o)) return false;
        return getCode() == rootAdmin.getCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCode());
    }



    }

