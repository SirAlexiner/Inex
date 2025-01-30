/**
 * This is the module info for the Inex Application.
 *
 * @author Torgrim Thorsen
 * @since April 28. 2023
 */
module Inex {

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires com.sun.jna;
  requires com.sun.jna.platform;
  requires atlantafx.base;
  requires com.jthemedetector;
  requires java.xml;
  requires org.kordamp.ikonli.feather;
  requires javafx.web;
  requires javafx.fxml;
  requires com.google.gson;
  requires kernel;
  requires layout;
  requires java.desktop;
  requires lombok;
  requires org.jetbrains.annotations;
  requires java.logging;

  exports no.ntnu.idatg1002.grp8.inex.addressapi;
  opens no.ntnu.idatg1002.grp8.inex.addressapi to javafx.fxml;
  exports no.ntnu.idatg1002.grp8.inex.addressapi.json;
  opens no.ntnu.idatg1002.grp8.inex.addressapi.json to javafx.fxml, com.google.gson;
  exports no.ntnu.idatg1002.grp8.inex.gui.controllers;
  opens no.ntnu.idatg1002.grp8.inex.gui.controllers to javafx.fxml;
  exports no.ntnu.idatg1002.grp8.inex.customers;
  opens no.ntnu.idatg1002.grp8.inex.customers to javafx.fxml, com.google.gson;
  exports no.ntnu.idatg1002.grp8.inex.invoice;
  opens no.ntnu.idatg1002.grp8.inex.invoice to javafx.fxml;
  exports no.ntnu.idatg1002.grp8.inex.gui.stages;
  opens no.ntnu.idatg1002.grp8.inex.gui.stages to javafx.fxml;
  exports no.ntnu.idatg1002.grp8.inex.utilities;
  opens no.ntnu.idatg1002.grp8.inex.utilities to javafx.fxml;
  exports no.ntnu.idatg1002.grp8.inex.customers.dao;
  opens no.ntnu.idatg1002.grp8.inex.financialdata;
  exports no.ntnu.idatg1002.grp8.inex.financialdata to com.google.gson;
  exports no.ntnu.idatg1002.grp8.inex.gui.controllers.util;
  opens no.ntnu.idatg1002.grp8.inex.gui.controllers.util to javafx.fxml;
}