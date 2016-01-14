/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection Update</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate#getConnectionname <em>Connectionname</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate#getEndnode <em>Endnode</em>}</li>
 * </ul>
 *
 * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getConnectionUpdate()
 * @model
 * @generated
 */
public interface ConnectionUpdate extends Sentence
{
  /**
   * Returns the value of the '<em><b>Connectionname</b></em>' reference list.
   * The list contents are of type {@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Connectionname</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Connectionname</em>' reference list.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getConnectionUpdate_Connectionname()
   * @model
   * @generated
   */
  EList<Connection> getConnectionname();

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
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getConnectionUpdate_Endnode()
   * @model
   * @generated
   */
  EList<Node> getEndnode();

} // ConnectionUpdate
