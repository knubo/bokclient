package no.knubo.bok.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/Users/knuterikborgen/kode/workspace/BokClient/src/no/knubo/bok/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Fikk ikke forventet svar fra server. Operasjonen er trolig ikke gjennomført.".
   * 
   * @return translated "Fikk ikke forventet svar fra server. Operasjonen er trolig ikke gjennomført."
   */
  @DefaultMessage("Fikk ikke forventet svar fra server. Operasjonen er trolig ikke gjennomført.")
  @Key("bad_server_response")
  String bad_server_response();

  /**
   * Translated "Slett bok?".
   * 
   * @return translated "Slett bok?"
   */
  @DefaultMessage("Slett bok?")
  @Key("delete_book_question")
  String delete_book_question();

  /**
   * Translated "Slett bruker?".
   * 
   * @return translated "Slett bruker?"
   */
  @DefaultMessage("Slett bruker?")
  @Key("delete_user_question")
  String delete_user_question();

  /**
   * Translated "Gitt navn finnes fra før - ikke lagret.".
   * 
   * @return translated "Gitt navn finnes fra før - ikke lagret."
   */
  @DefaultMessage("Gitt navn finnes fra før - ikke lagret.")
  @Key("duplicate")
  String duplicate();

  /**
   * Translated "Boken med gitt ISBN nummer finnes fra før.<br>Trykk på varseltrekant for å se på boken.".
   * 
   * @return translated "Boken med gitt ISBN nummer finnes fra før.<br>Trykk på varseltrekant for å se på boken."
   */
  @DefaultMessage("Boken med gitt ISBN nummer finnes fra før.<br>Trykk på varseltrekant for å se på boken.")
  @Key("duplicate_book")
  String duplicate_book();

  /**
   * Translated "Oppgitt boknummer er allerede i bruk - ikke lagret.".
   * 
   * @return translated "Oppgitt boknummer er allerede i bruk - ikke lagret."
   */
  @DefaultMessage("Oppgitt boknummer er allerede i bruk - ikke lagret.")
  @Key("duplicate_user_number")
  String duplicate_user_number();

  /**
   * Translated "Penger inngis på format 10.45 og må være større eller lik 0".
   * 
   * @return translated "Penger inngis på format 10.45 og må være større eller lik 0"
   */
  @DefaultMessage("Penger inngis på format 10.45 og må være større eller lik 0")
  @Key("field_money")
  String field_money();

  /**
   * Translated "Feltet må være større eller lik 0".
   * 
   * @return translated "Feltet må være større eller lik 0"
   */
  @DefaultMessage("Feltet må være større eller lik 0")
  @Key("field_positive")
  String field_positive();

  /**
   * Translated "Feltet må ha større verdi enn 0".
   * 
   * @return translated "Feltet må ha større verdi enn 0"
   */
  @DefaultMessage("Feltet må ha større verdi enn 0")
  @Key("field_to_low_zero")
  String field_to_low_zero();

  /**
   * Translated "Følgende felter er ikke validert ok: {0}.".
   * 
   * @return translated "Følgende felter er ikke validert ok: {0}."
   */
  @DefaultMessage("Følgende felter er ikke validert ok: {0}.")
  @Key("field_validation_fail")
  String field_validation_fail(String arg0);

  /**
   * Translated "Ulovlig dato".
   * 
   * @return translated "Ulovlig dato"
   */
  @DefaultMessage("Ulovlig dato")
  @Key("illegal_day")
  String illegal_day();

  /**
   * Translated "Ulovlig måned".
   * 
   * @return translated "Ulovlig måned"
   */
  @DefaultMessage("Ulovlig måned")
  @Key("illegal_month")
  String illegal_month();

  /**
   * Translated "Ulovlig år".
   * 
   * @return translated "Ulovlig år"
   */
  @DefaultMessage("Ulovlig år")
  @Key("illegal_year")
  String illegal_year();

  /**
   * Translated "Ulovlig epostadresse".
   * 
   * @return translated "Ulovlig epostadresse"
   */
  @DefaultMessage("Ulovlig epostadresse")
  @Key("invalid_email")
  String invalid_email();

  /**
   * Translated "Du har ikke tilgang til operasjonen".
   * 
   * @return translated "Du har ikke tilgang til operasjonen"
   */
  @DefaultMessage("Du har ikke tilgang til operasjonen")
  @Key("no_access")
  String no_access();

  /**
   * Translated "Søket ga Ingen treff".
   * 
   * @return translated "Søket ga Ingen treff"
   */
  @DefaultMessage("Søket ga Ingen treff")
  @Key("no_result")
  String no_result();

  /**
   * Translated "Fikk ikke svar fra server. Program- eller databasefeil.".
   * 
   * @return translated "Fikk ikke svar fra server. Program- eller databasefeil."
   */
  @DefaultMessage("Fikk ikke svar fra server. Program- eller databasefeil.")
  @Key("no_server_response")
  String no_server_response();

  /**
   * Translated "Du er ikke in\u0009nlogget - åpner innloggingsvindu".
   * 
   * @return translated "Du er ikke in\u0009nlogget - åpner innloggingsvindu"
   */
  @DefaultMessage("Du er ikke in\u0009nlogget - åpner innloggingsvindu")
  @Key("not_logged_in")
  String not_logged_in();

  /**
   * Translated "Det finnes allerede en person med gitt fornavn og etternavn - ikke lagret.".
   * 
   * @return translated "Det finnes allerede en person med gitt fornavn og etternavn - ikke lagret."
   */
  @DefaultMessage("Det finnes allerede en person med gitt fornavn og etternavn - ikke lagret.")
  @Key("person_duplicate")
  String person_duplicate();

  /**
   * Translated "Feltet må fylles ut".
   * 
   * @return translated "Feltet må fylles ut"
   */
  @DefaultMessage("Feltet må fylles ut")
  @Key("required_field")
  String required_field();

  /**
   * Translated "...ingen data oppdatert.".
   * 
   * @return translated "...ingen data oppdatert."
   */
  @DefaultMessage("...ingen data oppdatert.")
  @Key("save_failed")
  String save_failed();

  /**
   * Translated "Feil ved lagring av data".
   * 
   * @return translated "Feil ved lagring av data"
   */
  @DefaultMessage("Feil ved lagring av data")
  @Key("save_failed_badly")
  String save_failed_badly();

  /**
   * Translated "...lagret".
   * 
   * @return translated "...lagret"
   */
  @DefaultMessage("...lagret")
  @Key("save_ok")
  String save_ok();

  /**
   * Translated "Søket mislyktes - programfeil eller databasefeil".
   * 
   * @return translated "Søket mislyktes - programfeil eller databasefeil"
   */
  @DefaultMessage("Søket mislyktes - programfeil eller databasefeil")
  @Key("search_failed")
  String search_failed();

  /**
   * Translated "For mange treff. Viser kun {0}.".
   * 
   * @return translated "For mange treff. Viser kun {0}."
   */
  @DefaultMessage("For mange treff. Viser kun {0}.")
  @Key("too_many_hits")
  String too_many_hits(String arg0);

  /**
   * Translated "Inngitt boknummer er ikke i bruk".
   * 
   * @return translated "Inngitt boknummer er ikke i bruk"
   */
  @DefaultMessage("Inngitt boknummer er ikke i bruk")
  @Key("unknown_book_number")
  String unknown_book_number();

  /**
   * Translated "Din cachet(?) versjon av klienten er ikke på samme versjon som serverversjonen. Klientversjonen er {0} og serverversjonen er {1}. Prøv en shift reload av siden. Dette kan gjøre at deler av applikasjonen ikke fungerer som forventet.".
   * 
   * @return translated "Din cachet(?) versjon av klienten er ikke på samme versjon som serverversjonen. Klientversjonen er {0} og serverversjonen er {1}. Prøv en shift reload av siden. Dette kan gjøre at deler av applikasjonen ikke fungerer som forventet."
   */
  @DefaultMessage("Din cachet(?) versjon av klienten er ikke på samme versjon som serverversjonen. Klientversjonen er {0} og serverversjonen er {1}. Prøv en shift reload av siden. Dette kan gjøre at deler av applikasjonen ikke fungerer som forventet.")
  @Key("version_mismatch")
  String version_mismatch(String arg0,  String arg1);
}
