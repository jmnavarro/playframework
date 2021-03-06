/*
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package play.filters.csrf

import play.api.mvc._
import play.filters.csrf.CSRF.{ TokenProvider, ErrorHandler }
import play.api.Play

/**
 * A filter that provides CSRF protection.
 *
 * These must be by name parameters because the typical use case for instantiating the filter is in Global, which
 * happens before the application is started.  Since the default values for the parameters are loaded from config
 * and hence depend on a started application, they must be by name.
 *
 * @param tokenName The key used to store the token in the Play session.  Defaults to csrfToken.
 * @param cookieName If defined, causes the filter to store the token in a Cookie with this name instead of the session.
 * @param secureCookie If storing the token in a cookie, whether this Cookie should set the secure flag.  Defaults to
 *                     whether the session cookie is configured to be secure.
 * @param createIfNotFound Whether a new CSRF token should be created if it's not found.  Default creates one if it's
 *                         a GET request that accepts HTML.
 * @param tokenProvider A token provider to use.
 * @param errorHandler handling failed token error.
 */
class CSRFFilter(tokenName: => String = CSRFConf.TokenName,
    cookieName: => Option[String] = CSRFConf.CookieName,
    secureCookie: => Boolean = CSRFConf.SecureCookie,
    createIfNotFound: (RequestHeader) => Boolean = CSRFConf.defaultCreateIfNotFound,
    tokenProvider: => TokenProvider = CSRFConf.defaultTokenProvider,
    errorHandler: => ErrorHandler = CSRFConf.defaultErrorHandler) extends EssentialFilter {

  /**
   * Default constructor, useful from Java
   */
  def this() = {
    this(CSRFConf.TokenName, CSRFConf.CookieName, CSRFConf.SecureCookie, CSRFConf.defaultCreateIfNotFound,
      CSRFConf.defaultTokenProvider, CSRFConf.defaultJavaErrorHandler)
  }

  def apply(next: EssentialAction): EssentialAction = new CSRFAction(next, tokenName, cookieName, secureCookie,
    createIfNotFound, tokenProvider, errorHandler)
}

object CSRFFilter {
  def apply(tokenName: => String = CSRFConf.TokenName,
    cookieName: => Option[String] = CSRFConf.CookieName,
    secureCookie: => Boolean = CSRFConf.SecureCookie,
    createIfNotFound: (RequestHeader) => Boolean = CSRFConf.defaultCreateIfNotFound,
    tokenProvider: => TokenProvider = CSRFConf.defaultTokenProvider,
    errorHandler: => ErrorHandler = CSRFConf.defaultErrorHandler) = {
    new CSRFFilter(tokenName, cookieName, secureCookie, createIfNotFound, tokenProvider, errorHandler)
  }

}
