package v4.team.classes;

import v4.team.enumerations.EtatValidation;
import v4.team.model.Question;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cette classe s'occupe de gérer les événements temporels liés aux entités.
 */
public class DateEvent {

    /**
     * Format français de date.
     */
    private static final SimpleDateFormat frenchDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    /**
     * Renseigner une date associée à une instance d'entité.
     * La date est renseignée au format français à la seconde près.
     *
     * Cette méthode peut être utilisée comme argument d'un setter.
     *
     * @param changerDate la date à renseigner. C'est une donnée d'une instance d'entité.
     * @return la nouvelle date qui est celle du moment de l'appel à cette méthode.
     */
    public static String dateEvent(String changerDate)
    {
        Date now = new Date();
        //Convertir l'objet date en String au format fr
        changerDate = frenchDate.format(now);
        return changerDate;
    }

    //TODO : param Object -> date validation question / certification admin
    /**
     * Renseigner de nouveau une date.
     * Cette version est utilisable sur des questions.
     *
     * @param nouvDate une instance d'entité dont une des données est une date.
     */
    public static void dateNouvDemande(Object nouvDate)
    {
        if ( nouvDate instanceof Question) {
            Question dateDemande = (Question) nouvDate;
            if ( dateDemande.getEtatValidation().equals( EtatValidation.VALIDEE.getValeurEtat() ) ) {
                //Les questions creees par les admins sont validees immediatement dans cette version
                dateDemande.setTempsAttente("À l'instant");
                dateDemande.setDateDemandeAjout( dateEvent( dateDemande.getDateDemandeAjout() ) );
            }
            else if ( dateDemande.getEtatValidation().equals( EtatValidation.EN_ATTENTE.getValeurEtat() ) ) {
                //Mettre a jour a nouveau la date du moment de la proposition
                dateDemande.setDateDemandeAjout( dateEvent( dateDemande.getDateDemandeAjout() ) );
            }
        }
    }


    /**
     * 'Supprime' une date.
     *
     * Cette méthode peut être utilisée comme argument d'un setter.
     *
     * @return une date équivalente à null.
     */
    public static String resetDate() { return " - "; }


    /**
     * Permet de voir le temps entre une demande de validation
     * (création de question ou renouvellement de demande) et une action
     * par un admin (validation ou refus).
     *
     * Cette méthode réalise le calcule du temps d'attente et vérifie qu'elle
     * est utilisée sur une question. Il est préférable de l'appeler à chaque
     * fois qu'il est nécéssaire de mettre à jour le temps d'attente.
     *
     * @param o instance d'entité dont on veut connaître le temps d'attente.
     */
    public static void voirTempsAttente(Object o) throws RuntimeException, IllegalArgumentException
    {
        if ( o instanceof Question) {
            Question majTpsAtt = (Question) o;
            //Grandeurs temporelles converties en ms
            long second = 1000;
            long minute = 60 * second;
            long hour = 60 * minute;
            long day = 24 * hour;
            long year = 365 * day;

            //Conversion en ms de la date de création de la question
            long demandeMilliSec = conversionMillisecondesDemande(majTpsAtt);
            Date now = new Date();
            long nowMilliseconds = now.getTime();
            long difference = nowMilliseconds - demandeMilliSec;
            //Equivalents de la difference dans plusieurs unites de temps
            var years = (int) (difference / year);
            var days = (int) ((difference % year) / day);
            var hours = (int) ((difference % day) / hour);
            var minutes = (int) ((difference % hour) / minute);

            //Mettre a jour le temps d'attente
            majTpsAtt.setTempsAttente("");
            if (years > 0) {
                String annees = years > 1 ? years + "ans " : years + "an ";
                majTpsAtt.setTempsAttente(majTpsAtt.getTempsAttente() + annees);
            }
            if (days > 0) {
                String jours = days > 1 ? days + "jours " : days + "jour ";
                majTpsAtt.setTempsAttente(majTpsAtt.getTempsAttente() + jours);
            }
            if (hours > 0) {
                majTpsAtt.setTempsAttente(majTpsAtt.getTempsAttente() + hours + "h ");
            }

            if (minutes > 0) {
                majTpsAtt.setTempsAttente(majTpsAtt.getTempsAttente() + minutes + "min");
            } else {
                majTpsAtt.setTempsAttente("Moins d'une minute");
            }
        } else {
            throw new IllegalArgumentException("Le calcul du temps d'attente est compatible avec des questions uniquement."
            + "\nOpération impossible avec l'objet "+ o.toString());
        }
    }


    /**
     * Convertit en ms la date de demande de validation d'une question.
     *
     * @param o une instance d'entité possédant une date liée à une demande.
     * @return l'équivalent en ms de cette date.
     */
    public static long conversionMillisecondesDemande(Object o) throws org.springframework.expression.ParseException,
    IllegalArgumentException
    {
        Date demandeEng = null;
        if (o instanceof Question) {
            Question qMajDateDemande = (Question) o;
            if ( !qMajDateDemande.getDateDemandeAjout().equals(null) ) {
                try {
                    //Mettre la date d'ajout en attente dans le format de date de la JVM
                    demandeEng = frenchDate.parse( qMajDateDemande.getDateDemandeAjout() );
                } catch (ParseException e) {
                    throw new RuntimeException("Erreur lors de la conversion des formats de date pour la question d'id "+ qMajDateDemande.getId());
                }
            }
        } else {
            throw new IllegalArgumentException("La conversion en ms de la date d'ajout d'un execice en attente est compatible avec des questions uniquement."
            + "\nOpération impossible avec l'objet "+ o.toString());
        }
        return demandeEng.getTime();
    }

}
