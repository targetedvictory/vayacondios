package com.infochimps.vayacondios;

import java.util.Collection;

/**
 * An organization is the last commmon class in the Vayacondios
 * hierarchy between the itemset and stash interface.
 */
public class Organization<LinkType extends LinkToVCD>
  extends VayacondiosClient {
  public Organization(PathBuilder delegate) { super(delegate); }

  public Organization(VayacondiosClient server, String organization) {
    super(server);
    _server = server;
    _organization = organization;
  }

  //----------------------------------------------------------------------------
  // next in path hierarchy
  //----------------------------------------------------------------------------

  /**
   * @return new ItemSets path builder for this organization
   */
  public ItemSets<LinkType> itemsets() {
    return itemsets(new StandardVCDLink());
  }


  /**
   * @param linkType link type to use to 
   * 
   * @return new ItemSets path builder for this organization
   */
  public ItemSets<LinkType> itemsets(LinkToVCD linkToVCD) {
    return new ItemSets(this, linkToVCD);
  }

  //----------------------------------------------------------------------------
  // API HTTP path components
  //----------------------------------------------------------------------------

  protected String urlString(String type, String topic, String id) {
    return urlString(getOrganization(), type, topic, id);
  }

  protected int getPort() {
    return ((VayacondiosClient)getDelegate()).getPort();
  }
  protected String getServerName() {
    return ((VayacondiosClient)getDelegate()).getServerName();
  }
  String getOrganization() { return _organization; }

  //----------------------------------------------------------------------------
  // fields
  //----------------------------------------------------------------------------

  private VayacondiosClient _server;
  private String _organization;
}