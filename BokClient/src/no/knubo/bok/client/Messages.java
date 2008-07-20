package no.knubo.bok.client;


/**
 * Interface to represent the messages contained in resource  bundle:
 * 	/Users/knuterikborgen/Documents/workspacebok/BokClient/src/no/knubo/bok/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Det finnes allerede en person med gitt fornavn og etternavn - ikke lagret.".
   * 
   * @return translated "Det finnes allerede en person med gitt fornavn og etternavn - ikke lagret."
   * @gwt.key person_duplicate
   */
  String person_duplicate();

  /**
   * Translated "Ulovlig måned".
   * 
   * @return translated "Ulovlig måned"
   * @gwt.key illegal_month
   */
  String illegal_month();

  /**
   * Translated "Søket ga Ingen treff".
   * 
   * @return translated "Søket ga Ingen treff"
   * @gwt.key no_result
   */
  String no_result();

  /**
   * Translated "...ingen data oppdatert.".
   * 
   * @return translated "...ingen data oppdatert."
   * @gwt.key save_failed
   */
  String save_failed();

  /**
   * Translated "Fikk ikke svar fra server. Program- eller databasefeil.".
   * 
   * @return translated "Fikk ikke svar fra server. Program- eller databasefeil."
   * @gwt.key no_server_response
   */
  String no_server_response();

  /**
   * Translated "Ulovlig år".
   * 
   * @return translated "Ulovlig år"
   * @gwt.key illegal_year
   */
  String illegal_year();

  /**
   * Translated "Penger inngis på format 10.45 og må være større ".
   * 
   * @return translated "Penger inngis på format 10.45 og må være større "
   * @gwt.key field_money
   */
  String field_money();

  /**
   * Translated "Fikk ikke forventet svar fra server. Operasjonen er trolig ikke gjennomført.".
   * 
   * @return translated "Fikk ikke forventet svar fra server. Operasjonen er trolig ikke gjennomført."
   * @gwt.key bad_server_response
   */
  String bad_server_response();

  /**
   * Translated "For mange treff. Viser kun {0}.".
   * 
   * @return translated "For mange treff. Viser kun {0}."
   * @gwt.key too_many_hits
   */
  String too_many_hits(String arg0);

  /**
   * Translated "Slett bruker?".
   * 
   * @return translated "Slett bruker?"
   * @gwt.key delete_user_question
   */
  String delete_user_question();

  /**
   * Translated "lik 0".
   * 
   * @return translated "lik 0"
   * @gwt.key eller
   */
  String eller();

  /**
   * Translated "Feltet må ha større verdi enn 0".
   * 
   * @return translated "Feltet må ha større verdi enn 0"
   * @gwt.key field_to_low_zero
   */
  String field_to_low_zero();

  /**
   * Translated "Søket mislyktes - programfeil eller databasefeil".
   * 
   * @return translated "Søket mislyktes - programfeil eller databasefeil"
   * @gwt.key search_failed
   */
  String search_failed();

  /**
   * Translated "Feltet må være større eller lik 0".
   * 
   * @return translated "Feltet må være større eller lik 0"
   * @gwt.key field_positive
   */
  String field_positive();

  /**
   * Translated "Feil ved lagring av data".
   * 
   * @return translated "Feil ved lagring av data"
   * @gwt.key save_failed_badly
   */
  String save_failed_badly();

  /**
   * Translated "...lagret".
   * 
   * @return translated "...lagret"
   * @gwt.key save_ok
   */
  String save_ok();

  /**
   * Translated "Du har ikke tilgang til operasjonen".
   * 
   * @return translated "Du har ikke tilgang til operasjonen"
   * @gwt.key no_access
   */
  String no_access();

  /**
   * Translated "Din cachet(?) versjon av klienten er ikke på samme versjon som serverversjonen. Klientversjonen er {0} og serverversjonen er {1}. Prøv en shift reload av siden. Dette kan gjøre at deler av applikasjonen ikke fungerer som forventet.".
   * 
   * @return translated "Din cachet(?) versjon av klienten er ikke på samme versjon som serverversjonen. Klientversjonen er {0} og serverversjonen er {1}. Prøv en shift reload av siden. Dette kan gjøre at deler av applikasjonen ikke fungerer som forventet."
   * @gwt.key version_mismatch
   */
  String version_mismatch(String arg0,  String arg1);

  /**
   * Translated "Feltet må fylles ut".
   * 
   * @return translated "Feltet må fylles ut"
   * @gwt.key required_field
   */
  String required_field();

  /**
   * Translated "Følgende felter er ikke validert ok: {0}.".
   * 
   * @return translated "Følgende felter er ikke validert ok: {0}."
   * @gwt.key field_validation_fail
   */
  String field_validation_fail(String arg0);

  /**
   * Translated "Ulovlig dato".
   * 
   * @return translated "Ulovlig dato"
   * @gwt.key illegal_day
   */
  String illegal_day();

  /**
   * Translated "Ulovlig epostadresse".
   * 
   * @return translated "Ulovlig epostadresse"
   * @gwt.key invalid_email
   */
  String invalid_email();

  /**
   * Translated "Gitt navn finnes fra før - ikke lagret.".
   * 
   * @return translated "Gitt navn finnes fra før - ikke lagret."
   * @gwt.key duplicate
   */
  String duplicate();

  /**
   * Translated "Du er ikke innlogget - åpner innloggingsvindu".
   * 
   * @return translated "Du er ikke innlogget - åpner innloggingsvindu"
   * @gwt.key not_logged_in
   */
  String not_logged_in();
}
