/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Operating</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating#getNodename <em>Nodename</em>}</li>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating#getNodes <em>Nodes</em>}</li>
 * </ul>
 *
 * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getNodeOperating()
 * @model
 * @generated
 */
public interface NodeOperating extends Sentence
{
  /**
   * Returns the value of the '<em><b>Nodename</b></em>' reference list.
   * The list contents are of type {@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Node}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nodename</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nodename</em>' reference list.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getNodeOperating_Nodename()
   * @model
   * @generated
   */
  EList<Node> getNodename();

  /**
   * Returns the value of the '<em><b>Nodes</b></em>' reference list.
   * The list contents are of type {@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Node}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nodes</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nodes</em>' reference list.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getNodeOperating_Nodes()
   * @model
   * @generated
   */
  EList<Node> getNodes();

} // NodeOperating
