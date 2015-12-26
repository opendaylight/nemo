/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Update</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.FlowUpdate#getFlowId <em>Flow Id</em>}</li>
 * </ul>
 *
 * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getFlowUpdate()
 * @model
 * @generated
 */
public interface FlowUpdate extends Sentence
{
  /**
   * Returns the value of the '<em><b>Flow Id</b></em>' reference list.
   * The list contents are of type {@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Flow}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Flow Id</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Flow Id</em>' reference list.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorPackage#getFlowUpdate_FlowId()
   * @model
   * @generated
   */
  EList<Flow> getFlowId();

} // FlowUpdate
