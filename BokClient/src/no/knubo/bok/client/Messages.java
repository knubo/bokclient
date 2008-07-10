package no.knubo.bok.client;


/**
 * Interface to represent the messages contained in resource  bundle:
 * 	/Users/knuterikborgen/Documents/workspacebok/BokClient/src/no/knubo/bok/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Søket ga Ingen treff".
   * 
   * @return translated "Søket ga Ingen treff"
   * @gwt.key no_result
   */
  String no_result();

  /**
   * Translated "Fikk ikke svar fra server. Program- eller databasefeil.".
   * 
   * @return translated "Fikk ikke svar fra server. Program- eller databasefeil."
   * @gwt.key no_server_response
   */
  String no_server_response();

  /**
   * Translated "...ingen data oppdatert.".
   * 
   * @return translated "...ingen data oppdatert."
   * @gwt.key save_failed
   */
  String save_failed();

  /**
   * Translated "Fikk ikke forventet svar fra server. Operasjonen er trolig ikke gjennomført.".
   * 
   * @return translated "Fikk ikke forventet svar fra server. Operasjonen er trolig ikke gjennomført."
   * @gwt.key bad_server_response
   */
  String bad_server_response();

  /**
   * Translated "Slett bruker?".
   * 
   * @return translated "Slett bruker?"
   * @gwt.key delete_user_question
   */
  String delete_user_question();

  /**
   * Translated "For mange treff. Viser kun {0}.".
   * 
   * @return translated "For mange treff. Viser kun {0}."
   * @gwt.key too_many_hits
   */
  String too_many_hits(String arg0);

  /**
   * Translated "Søket mislyktes - programfeil eller databasefeil".
   * 
   * @return translated "Søket mislyktes - programfeil eller databasefeil"
   * @gwt.key search_failed
   */
  String search_failed();

  /**
   * Translated "Feil ved lagring av data".
   * 
   * @return translated "Feil ved lagring av data"
   * @gwt.key save_failed_badly
   */
  String save_failed_badly();

  /**
   * Translated "Du har ikke tilgang til operasjonen".
   * 
   * @return translated "Du har ikke tilgang til operasjonen"
   * @gwt.key no_access
   */
  String no_access();

  /**
   * Translated "...lagret".
   * 
   * @return translated "...lagret"
   * @gwt.key save_ok
   */
  String save_ok();

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
   * Translated "Du er ikke innlogget - åpner innloggingsvindu".
   * 
   * @return translated "Du er ikke innlogget - åpner innloggingsvindu"
   * @gwt.key not_logged_in
   */
  String not_logged_in();
}
