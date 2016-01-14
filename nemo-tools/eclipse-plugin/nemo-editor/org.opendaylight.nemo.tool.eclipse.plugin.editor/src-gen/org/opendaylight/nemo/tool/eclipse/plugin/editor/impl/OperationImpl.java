/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.Flow;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.Node;
import org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl#getTargetId <em>Target Id</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl#getTargetNode <em>Target Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationImpl extends SentenceImpl implements Operation
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected EList<Integer> value;

  /**
   * The cached value of the '{@link #getTargetId() <em>Target Id</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetId()
   * @generated
   * @ordered
   */
  protected Flow targetId;

  /**
   * The cached value of the '{@link #getTargetNode() <em>Target Node</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetNode()
   * @generated
   * @ordered
   */
  protected Node targetNode;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OperationImpl()
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
    return EditorPackage.Literals.OPERATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.OPERATION__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Integer> getValue()
  {
    if (value == null)
    {
      value = new EDataTypeEList<Integer>(Integer.class, this, EditorPackage.OPERATION__VALUE);
    }
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Flow getTargetId()
  {
    if (targetId != null && targetId.eIsProxy())
    {
      InternalEObject oldTargetId = (InternalEObject)targetId;
      targetId = (Flow)eResolveProxy(oldTargetId);
      if (targetId != oldTargetId)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, EditorPackage.OPERATION__TARGET_ID, oldTargetId, targetId));
      }
    }
    return targetId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Flow basicGetTargetId()
  {
    return targetId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTargetId(Flow newTargetId)
  {
    Flow oldTargetId = targetId;
    targetId = newTargetId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.OPERATION__TARGET_ID, oldTargetId, targetId));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Node getTargetNode()
  {
    if (targetNode != null && targetNode.eIsProxy())
    {
      InternalEObject oldTargetNode = (InternalEObject)targetNode;
      targetNode = (Node)eResolveProxy(oldTargetNode);
      if (targetNode != oldTargetNode)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, EditorPackage.OPERATION__TARGET_NODE, oldTargetNode, targetNode));
      }
    }
    return targetNode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Node basicGetTargetNode()
  {
    return targetNode;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTargetNode(Node newTargetNode)
  {
    Node oldTargetNode = targetNode;
    targetNode = newTargetNode;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.OPERATION__TARGET_NODE, oldTargetNode, targetNode));
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
      case EditorPackage.OPERATION__NAME:
        return getName();
      case EditorPackage.OPERATION__VALUE:
        return getValue();
      case EditorPackage.OPERATION__TARGET_ID:
        if (resolve) return getTargetId();
        return basicGetTargetId();
      case EditorPackage.OPERATION__TARGET_NODE:
        if (resolve) return getTargetNode();
        return basicGetTargetNode();
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
      case EditorPackage.OPERATION__NAME:
        setName((String)newValue);
        return;
      case EditorPackage.OPERATION__VALUE:
        getValue().clear();
        getValue().addAll((Collection<? extends Integer>)newValue);
        return;
      case EditorPackage.OPERATION__TARGET_ID:
        setTargetId((Flow)newValue);
        return;
      case EditorPackage.OPERATION__TARGET_NODE:
        setTargetNode((Node)newValue);
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
      case EditorPackage.OPERATION__NAME:
        setName(NAME_EDEFAULT);
        return;
      case EditorPackage.OPERATION__VALUE:
        getValue().clear();
        return;
      case EditorPackage.OPERATION__TARGET_ID:
        setTargetId((Flow)null);
        return;
      case EditorPackage.OPERATION__TARGET_NODE:
        setTargetNode((Node)null);
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
      case EditorPackage.OPERATION__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case EditorPackage.OPERATION__VALUE:
        return value != null && !value.isEmpty();
      case EditorPackage.OPERATION__TARGET_ID:
        return targetId != null;
      case EditorPackage.OPERATION__TARGET_NODE:
        return targetNode != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", value: ");
    result.append(value);
    result.append(')');
    return result.toString();
  }

} //OperationImpl
