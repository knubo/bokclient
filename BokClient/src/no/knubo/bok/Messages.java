package no.knubo.bok;


/**
 * Interface to represent the messages contained in resource  bundle:
 * 	/Users/knuterikborgen/Documents/workspacebok/BokClient/src/no/knubo/bok/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Du har ikke tilgang til operasjonen".
   * 
   * @return translated "Du har ikke tilgang til operasjonen"
   * @gwt.key no_access
   */
  String no_access();

  /**
   * Translated "Din cachet(?) versjon av klienten er ikke på samme versjon".
   * 
   * @return translated "Din cachet(?) versjon av klienten er ikke på samme versjon"
   * @gwt.key version_mismatch
   */
  String version_mismatch();

  /**
   * Translated "som forventet.".
   * 
   * @return translated "som forventet."
   * @gwt.key fungerer
   */
  String fungerer();

  /**
   * Translated "serverversjonen. Klientversjonen er {0} og serverversjonen er {1}. Prø".
   * 
   * @return translated "serverversjonen. Klientversjonen er {0} og serverversjonen er {1}. Prø"
   * @gwt.key som
   */
  String som(String arg0,  String arg1);

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
   * Translated "For mange treff. Viser kun {0}.".
   * 
   * @return translated "For mange treff. Viser kun {0}."
   * @gwt.key too_many_hits
   */
  String too_many_hits(String arg0);

  /**
   * Translated "Du er ikke innlogget - åpner innloggingsvindu".
   * 
   * @return translated "Du er ikke innlogget - åpner innloggingsvindu"
   * @gwt.key not_logged_in
   */
  String not_logged_in();

  /**
   * Translated "en shift reload av siden. Dette kan gjøre at deler av applikasjonen ikke ".
   * 
   * @return translated "en shift reload av siden. Dette kan gjøre at deler av applikasjonen ikke "
   * @gwt.key v
   */
  String v();
}
