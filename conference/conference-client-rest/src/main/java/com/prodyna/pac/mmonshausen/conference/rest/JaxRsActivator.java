package com.prodyna.pac.mmonshausen.conference.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * in order to activate JAX-RS (and avoid using XMLs) one (empty) class has to
 * inherit from class Application and has to set ApplicationPath
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
}