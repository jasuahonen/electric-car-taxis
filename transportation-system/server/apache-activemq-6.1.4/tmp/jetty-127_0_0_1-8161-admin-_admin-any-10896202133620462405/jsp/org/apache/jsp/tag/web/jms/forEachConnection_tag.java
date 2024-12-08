/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/11.0.24
 * Generated at: 2024-12-02 20:03:06 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.tag.web.jms;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import java.util.Iterator;
import org.apache.activemq.broker.jmx.ConnectionViewMBean;

public final class forEachConnection_tag
    extends jakarta.servlet.jsp.tagext.SimpleTagSupport
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Iterator");
    _jspx_imports_classes.add("org.apache.activemq.broker.jmx.ConnectionViewMBean");
  }

  private jakarta.servlet.jsp.JspContext jspContext;
  private java.io.Writer _jspx_sout;
  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public void setJspContext(jakarta.servlet.jsp.JspContext ctx) {
    super.setJspContext(ctx);
    java.util.ArrayList _jspx_nested = null;
    java.util.ArrayList _jspx_at_begin = null;
    java.util.ArrayList _jspx_at_end = null;
    this.jspContext = new org.apache.jasper.runtime.JspContextWrapper(this, ctx, _jspx_nested, _jspx_at_begin, _jspx_at_end, null);
  }

  public jakarta.servlet.jsp.JspContext getJspContext() {
    return this.jspContext;
  }
  private java.lang.String connection;
  private java.lang.String connectionName;
  private org.apache.activemq.web.BrokerFacade broker;
  private java.lang.String connectorName;

  public java.lang.String getConnection() {
    return this.connection;
  }

  public void setConnection(java.lang.String connection) {
    this.connection = connection;
    jspContext.setAttribute("connection", connection);
  }

  public java.lang.String getConnectionName() {
    return this.connectionName;
  }

  public void setConnectionName(java.lang.String connectionName) {
    this.connectionName = connectionName;
    jspContext.setAttribute("connectionName", connectionName);
  }

  public org.apache.activemq.web.BrokerFacade getBroker() {
    return this.broker;
  }

  public void setBroker(org.apache.activemq.web.BrokerFacade broker) {
    this.broker = broker;
    jspContext.setAttribute("broker", broker);
  }

  public java.lang.String getConnectorName() {
    return this.connectorName;
  }

  public void setConnectorName(java.lang.String connectorName) {
    this.connectorName = connectorName;
    jspContext.setAttribute("connectorName", connectorName);
  }

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    return _jsp_instancemanager;
  }

  private void _jspInit(jakarta.servlet.ServletConfig config) {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(config.getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(config);
  }

  public void _jspDestroy() {
  }

  public void doTag() throws jakarta.servlet.jsp.JspException, java.io.IOException {
    jakarta.servlet.jsp.PageContext _jspx_page_context = (jakarta.servlet.jsp.PageContext)jspContext;
    jakarta.servlet.http.HttpServletRequest request = (jakarta.servlet.http.HttpServletRequest) _jspx_page_context.getRequest();
    jakarta.servlet.http.HttpServletResponse response = (jakarta.servlet.http.HttpServletResponse) _jspx_page_context.getResponse();
    jakarta.servlet.http.HttpSession session = _jspx_page_context.getSession();
    jakarta.servlet.ServletContext application = _jspx_page_context.getServletContext();
    jakarta.servlet.ServletConfig config = _jspx_page_context.getServletConfig();
    jakarta.servlet.jsp.JspWriter out = jspContext.getOut();
    _jspInit(config);
    jspContext.getELContext().putContext(jakarta.servlet.jsp.JspContext.class,jspContext);
    if( getConnection() != null ) 
      _jspx_page_context.setAttribute("connection", getConnection());
    if( getConnectionName() != null ) 
      _jspx_page_context.setAttribute("connectionName", getConnectionName());
    if( getBroker() != null ) 
      _jspx_page_context.setAttribute("broker", getBroker());
    if( getConnectorName() != null ) 
      _jspx_page_context.setAttribute("connectorName", getConnectorName());

    try {
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

	Iterator it = broker.getConnections(connectorName).iterator();
	while (it.hasNext()) {
		String conName = (String) it.next();
		ConnectionViewMBean con = broker.getConnection(conName);
		request.setAttribute(connectionName, conName);
		request.setAttribute(connection, con);

      out.write('\n');
      ((org.apache.jasper.runtime.JspContextWrapper) this.jspContext).syncBeforeInvoke();
      _jspx_sout = null;
      if (getJspBody() != null)
        getJspBody().invoke(_jspx_sout);
      jspContext.getELContext().putContext(jakarta.servlet.jsp.JspContext.class,getJspContext());
      out.write('\n');

	}

      out.write("       \n");
      out.write("    \n");
    } catch( java.lang.Throwable t ) {
      if( t instanceof jakarta.servlet.jsp.SkipPageException )
          throw (jakarta.servlet.jsp.SkipPageException) t;
      if( t instanceof java.io.IOException )
          throw (java.io.IOException) t;
      if( t instanceof java.lang.IllegalStateException )
          throw (java.lang.IllegalStateException) t;
      if( t instanceof jakarta.servlet.jsp.JspException )
          throw (jakarta.servlet.jsp.JspException) t;
      throw new jakarta.servlet.jsp.JspException(t);
    } finally {
      jspContext.getELContext().putContext(jakarta.servlet.jsp.JspContext.class,super.getJspContext());
      ((org.apache.jasper.runtime.JspContextWrapper) jspContext).syncEndTagFile();
    }
  }
}
