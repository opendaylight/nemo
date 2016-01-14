/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.Node;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Operating</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeOperatingImpl#getNodename <em>Nodename</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeOperatingImpl#getNodes <em>Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeOperatingImpl extends SentenceImpl implements NodeOperating
{
  /**
   * The cached value of the '{@link #getNodename() <em>Nodename</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNodename()
   * @generated
   * @ordered
   */
  protected EList<Node> nodename;

  /**
   * The cached value of the '{@link #getNodes() <em>Nodes</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNodes()
   * @generated
   * @ordered
   */
  protected EList<Node> nodes;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NodeOperatingImpl()
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
    return EditorPackage.Literals.NODE_OPERATING;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Node> getNodename()
  {
    if (nodename == null)
    {
      nodename = new EObjectResolvingEList<Node>(Node.class, this, EditorPackage.NODE_OPERATING__NODENAME);
    }
    return nodename;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Node> getNodes()
  {
    if (nodes == null)
    {
      nodes = new EObjectResolvingEList<Node>(Node.class, this, EditorPackage.NODE_OPERATING__NODES);
    }
    return nodes;
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
      case EditorPackage.NODE_OPERATING__NODENAME:
        return getNodename();
      case EditorPackage.NODE_OPERATING__NODES:
        return getNodes();
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
      case EditorPackage.NODE_OPERATING__NODENAME:
        getNodename().clear();
        getNodename().addAll((Collection<? extends Node>)newValue);
        return;
      case EditorPackage.NODE_OPERATING__NODES:
        getNodes().clear();
        getNodes().addAll((Collection<? extends Node>)newValue);
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
      case EditorPackage.NODE_OPERATING__NODENAME:
        getNodename().clear();
        return;
      case EditorPackage.NODE_OPERATING__NODES:
        getNodes().clear();
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
      case EditorPackage.NODE_OPERATING__NODENAME:
        return nodename != null && !nodename.isEmpty();
      case EditorPackage.NODE_OPERATING__NODES:
        return nodes != null && !nodes.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //NodeOperatingImpl
