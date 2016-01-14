/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.Flow;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.FlowUpdate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flow Update</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowUpdateImpl#getFlowId <em>Flow Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FlowUpdateImpl extends SentenceImpl implements FlowUpdate
{
  /**
   * The cached value of the '{@link #getFlowId() <em>Flow Id</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFlowId()
   * @generated
   * @ordered
   */
  protected EList<Flow> flowId;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FlowUpdateImpl()
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
    return EditorPackage.Literals.FLOW_UPDATE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Flow> getFlowId()
  {
    if (flowId == null)
    {
      flowId = new EObjectResolvingEList<Flow>(Flow.class, this, EditorPackage.FLOW_UPDATE__FLOW_ID);
    }
    return flowId;
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
      case EditorPackage.FLOW_UPDATE__FLOW_ID:
        return getFlowId();
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
      case EditorPackage.FLOW_UPDATE__FLOW_ID:
        getFlowId().clear();
        getFlowId().addAll((Collection<? extends Flow>)newValue);
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
      case EditorPackage.FLOW_UPDATE__FLOW_ID:
        getFlowId().clear();
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
      case EditorPackage.FLOW_UPDATE__FLOW_ID:
        return flowId != null && !flowId.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //FlowUpdateImpl
