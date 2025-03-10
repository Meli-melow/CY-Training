package v3bis.team.service.Classes;

import org.springframework.stereotype.Service;
import v3bis.team.enumerations.EtatValidation;
import v3bis.team.model.Question;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Permet de modifier les dates associées aux classes selon les actions des utilisateurs.
 *
 * Utilisé en particulier pour les Questions (dates de demande d'ajout et de validation)
 * et les Admins (date de certification)
 */
@Service
public class DateEventService {

    static final SimpleDateFormat frenchDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    //TODO : param Object -> date validation question / certification admin
    public static String dateEvent(String changerDate)
    {
        /*System.out.println("Current date : "+changerDate);*/
        Date now = new Date();
        changerDate = frenchDate.format(now);
        /*System.out.println("New date : "+changerDate);*/
        return changerDate;
    }

    //TODO : param Object -> date validation question / certification admin
    public static void dateNouvDemande(Object nouvDate)
    {
        if ( nouvDate instanceof Question ) {
            Question dateDemande = (Question) nouvDate;
            if ( dateDemande.getEtatValidation().equals( EtatValidation.VALIDEE.getValeurEtat() ) ) {
                dateDemande.setTempsAttente("À l'instant");
                dateDemande.setDateDemandeAjout( dateEvent( dateDemande.getDateDemandeAjout() ) );
            }
            else if ( dateDemande.getEtatValidation().equals( EtatValidation.EN_ATTENTE.getValeurEtat() ) ) {
                dateDemande.setDateDemandeAjout( dateEvent( dateDemande.getDateDemandeAjout() ) );
            }
        }
    }

    //TODO : use for question date validation / admin certification
    /**
     * Supprime une date
     */
    public static String resetDate() { return " - "; }

    /**
     * Permet de voir le temps entre une demande de validation
     * (création de question ou renouvellement de demande) et une action
     * par un admin (validation ou refus).
     */
    //TODO : Appel à chaque fois qu'on accède aux informations d'une telle question.
    public static void voirTempsAttente(Object changerDate) {

        if ( changerDate instanceof Question) {
            Question majTpsAtt = (Question) changerDate;
            if ( !majTpsAtt.getEtatValidation().equals( EtatValidation.EN_ATTENTE.getValeurEtat() ) ) {
                System.out.println("La question n'est pas en attente!\n");

            } else {
                //Équivalents en ms
                long second = 1000;
                long minute = 60 * second;
                long hour = 60 * minute;
                long day = 24 * hour;
                long year = 365 * day;

                //Calculer la différence en ms entre le moment de la mise en attente et le moment de l'action
                long demandeMilliSec = majTpsAtt.demandeMillisecondes();
                Date now = new Date();
                long nowMilliseconds = now.getTime();
                long difference = nowMilliseconds - demandeMilliSec;
                //Conversion des ms en d'autres unités de temps
                var years = (int) (difference / year);
                var days = (int) ((difference % year) / day);
                var hours = (int) ((difference % day) / hour);
                var minutes = (int) ((difference % hour) / minute);

                majTpsAtt.setTempsAttente("");
                if (years > 0) {
                    String annees = years > 1 ? years + "ans " : years + "an ";
                    majTpsAtt.setTempsAttente( majTpsAtt.getTempsAttente() + annees );
                }
                if (days > 0) {
                    String jours = days > 1 ? days + "jours " : days + "jour ";
                    majTpsAtt.setTempsAttente( majTpsAtt.getTempsAttente() + jours );
                }
                if (hours > 0) { majTpsAtt.setTempsAttente( majTpsAtt.getTempsAttente() + hours + "h " ); }

                if (minutes > 0) { majTpsAtt.setTempsAttente( majTpsAtt.getTempsAttente() + minutes + "min" ); }

                else { majTpsAtt.setTempsAttente("Moins d'une minute"); }
            }
        }

    }

    public static long dateEventMillisecondes(Object changerDate)
    {
        Date momentValidation = null;
        if ( changerDate instanceof Question ) {
            Question q = (Question) changerDate;
            if ( !q.getDateValidee().equals(" - ") ) {
                momentValidation = new Date(q.getDateValidee());
            }
        }
        return momentValidation.getTime();
    }


}
