/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.Node;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Update</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionUpdateImpl#getConnectionname <em>Connectionname</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionUpdateImpl#getEndnode <em>Endnode</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectionUpdateImpl extends SentenceImpl implements ConnectionUpdate
{
  /**
   * The cached value of the '{@link #getConnectionname() <em>Connectionname</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConnectionname()
   * @generated
   * @ordered
   */
  protected EList<Connection> connectionname;

  /**
   * The cached value of the '{@link #getEndnode() <em>Endnode</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndnode()
   * @generated
   * @ordered
   */
  protected EList<Node> endnode;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConnectionUpdateImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return EditorPackage.Literals.CONNECTION_UPDATE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Connection> getConnectionname()
  {
    if (connectionname == null)
    {
      connectionname = new EObjectResolvingEList<Connection>(Connection.class, this, EditorPackage.CONNECTION_UPDATE__CONNECTIONNAME);
    }
    return connectionname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Node> getEndnode()
  {
    if (endnode == null)
    {
      endnode = new EObjectResolvingEList<Node>(Node.class, this, EditorPackage.CONNECTION_UPDATE__ENDNODE);
    }
    return endnode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case EditorPackage.CONNECTION_UPDATE__CONNECTIONNAME:
        return getConnectionname();
      case EditorPackage.CONNECTION_UPDATE__ENDNODE:
        return getEndnode();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EditorPackage.CONNECTION_UPDATE__CONNECTIONNAME:
        getConnectionname().clear();
        getConnectionname().addAll((Collection<? extends Connection>)newValue);
        return;
      case EditorPackage.CONNECTION_UPDATE__ENDNODE:
        getEndnode().clear();
        getEndnode().addAll((Collection<? extends Node>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case EditorPackage.CONNECTION_UPDATE__CONNECTIONNAME:
        getConnectionname().clear();
        return;
      case EditorPackage.CONNECTION_UPDATE__ENDNODE:
        getEndnode().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case EditorPackage.CONNECTION_UPDATE__CONNECTIONNAME:
        return connectionname != null && !connectionname.isEmpty();
      case EditorPackage.CONNECTION_UPDATE__ENDNODE:
        return endnode != null && !endnode.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ConnectionUpdateImpl
