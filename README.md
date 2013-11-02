PRODYNA PAC Conference Portal - Martin Monshausen Edition
==========================================================

This git repository contains all projects belonging to the PRODYNA PAC Conference Portal.

There are:
* conference_backend
  EJB backend providing services for CRUD operations
* conference_client_common
  common classes for client projects
* conference_client_jsf
  JSF client for UI
* conference_rest
  REST client for integration into e.q. mobile apps

Prerequisites for this project are:
* IDE with maven support
* JBOSS >= 7 (or equivalent)
  * running in standalone_full-mode
  * MySQL module and driver
  * Datasource for MySQL named conference_ds
  * Datasource for Queue named queue/log
