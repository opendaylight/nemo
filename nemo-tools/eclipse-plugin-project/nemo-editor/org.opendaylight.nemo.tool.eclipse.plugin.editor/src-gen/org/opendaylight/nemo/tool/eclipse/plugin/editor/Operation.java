/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getName <em>Name</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getValue <em>Value</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetId <em>Target Id</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetNode <em>Target Node</em>}</li>
 * </ul>
 *
 * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getOperation()
 * @model
 * @generated
 */
public interface Operation extends Sentence
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getOperation_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute list.
   * The list contents are of type {@link java.lang.Integer}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute list.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getOperation_Value()
   * @model unique="false"
   * @generated
   */
  EList<Integer> getValue();

  /**
   * Returns the value of the '<em><b>Target Id</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target Id</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target Id</em>' reference.
   * @see #setTargetId(Flow)
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getOperation_TargetId()
   * @model
   * @generated
   */
  Flow getTargetId();

  /**
   * Sets the value of the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetId <em>Target Id</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target Id</em>' reference.
   * @see #getTargetId()
   * @generated
   */
  void setTargetId(Flow value);

  /**
   * Returns the value of the '<em><b>Target Node</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target Node</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target Node</em>' reference.
   * @see #setTargetNode(Node)
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getOperation_TargetNode()
   * @model
   * @generated
   */
  Node getTargetNode();

  /**
   * Sets the value of the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetNode <em>Target Node</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target Node</em>' reference.
   * @see #getTargetNode()
   * @generated
   */
  void setTargetNode(Node value);

} // Operation
