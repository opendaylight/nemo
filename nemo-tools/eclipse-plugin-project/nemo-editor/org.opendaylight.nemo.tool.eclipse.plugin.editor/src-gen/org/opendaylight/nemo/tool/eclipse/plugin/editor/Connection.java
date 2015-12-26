/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection#getName <em>Name</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection#getEndnode <em>Endnode</em>}</li>
 * </ul>
 *
 * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getConnection()
 * @model
 * @generated
 */
public interface Connection extends Sentence
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
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getConnection_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Endnode</b></em>' reference list.
   * The list contents are of type {@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Node}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Endnode</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Endnode</em>' reference list.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getConnection_Endnode()
   * @model
   * @generated
   */
  EList<Node> getEndnode();

} // Connection
