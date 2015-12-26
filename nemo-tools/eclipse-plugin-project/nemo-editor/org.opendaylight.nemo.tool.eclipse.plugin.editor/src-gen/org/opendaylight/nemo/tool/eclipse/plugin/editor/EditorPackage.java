/**
 */
package org.opendaylight.nemo.tool.eclipse.plugin.editor;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.EditorFactory
 * @model kind="package"
 * @generated
 */
public interface EditorPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "editor";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.huawei.com/nemo/Editor";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "editor";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  EditorPackage eINSTANCE = org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl.init();

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ModelImpl <em>Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ModelImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getModel()
   * @generated
   */
  int MODEL = 0;

  /**
   * The feature id for the '<em><b>Sentences</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__SENTENCES = 0;

  /**
   * The number of structural features of the '<em>Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.SentenceImpl <em>Sentence</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.SentenceImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getSentence()
   * @generated
   */
  int SENTENCE = 1;

  /**
   * The number of structural features of the '<em>Sentence</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SENTENCE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeImpl <em>Node</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getNode()
   * @generated
   */
  int NODE = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE__NAME = SENTENCE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Nodes</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE__NODES = SENTENCE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Node</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE_FEATURE_COUNT = SENTENCE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeModelImpl <em>Node Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeModelImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getNodeModel()
   * @generated
   */
  int NODE_MODEL = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE_MODEL__NAME = 0;

  /**
   * The number of structural features of the '<em>Node Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE_MODEL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeOperatingImpl <em>Node Operating</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeOperatingImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getNodeOperating()
   * @generated
   */
  int NODE_OPERATING = 4;

  /**
   * The feature id for the '<em><b>Nodename</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE_OPERATING__NODENAME = SENTENCE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Nodes</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE_OPERATING__NODES = SENTENCE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Node Operating</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NODE_OPERATING_FEATURE_COUNT = SENTENCE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionImpl <em>Connection</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getConnection()
   * @generated
   */
  int CONNECTION = 5;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION__NAME = SENTENCE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Endnode</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION__ENDNODE = SENTENCE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Connection</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION_FEATURE_COUNT = SENTENCE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionUpdateImpl <em>Connection Update</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionUpdateImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getConnectionUpdate()
   * @generated
   */
  int CONNECTION_UPDATE = 6;

  /**
   * The feature id for the '<em><b>Connectionname</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION_UPDATE__CONNECTIONNAME = SENTENCE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Endnode</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION_UPDATE__ENDNODE = SENTENCE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Connection Update</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTION_UPDATE_FEATURE_COUNT = SENTENCE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowImpl <em>Flow</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getFlow()
   * @generated
   */
  int FLOW = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FLOW__NAME = SENTENCE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Flow</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FLOW_FEATURE_COUNT = SENTENCE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowUpdateImpl <em>Flow Update</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowUpdateImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getFlowUpdate()
   * @generated
   */
  int FLOW_UPDATE = 8;

  /**
   * The feature id for the '<em><b>Flow Id</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FLOW_UPDATE__FLOW_ID = SENTENCE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Flow Update</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FLOW_UPDATE_FEATURE_COUNT = SENTENCE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl <em>Operation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getOperation()
   * @generated
   */
  int OPERATION = 9;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERATION__NAME = SENTENCE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERATION__VALUE = SENTENCE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Target Id</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERATION__TARGET_ID = SENTENCE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Target Node</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERATION__TARGET_NODE = SENTENCE_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Operation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERATION_FEATURE_COUNT = SENTENCE_FEATURE_COUNT + 4;


  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Model
   * @generated
   */
  EClass getModel();

  /**
   * Returns the meta object for the containment reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Model#getSentences <em>Sentences</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Sentences</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Model#getSentences()
   * @see #getModel()
   * @generated
   */
  EReference getModel_Sentences();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Sentence <em>Sentence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Sentence</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Sentence
   * @generated
   */
  EClass getSentence();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Node <em>Node</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Node</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Node
   * @generated
   */
  EClass getNode();

  /**
   * Returns the meta object for the attribute '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Node#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Node#getName()
   * @see #getNode()
   * @generated
   */
  EAttribute getNode_Name();

  /**
   * Returns the meta object for the reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Node#getNodes <em>Nodes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Nodes</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Node#getNodes()
   * @see #getNode()
   * @generated
   */
  EReference getNode_Nodes();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeModel <em>Node Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Node Model</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeModel
   * @generated
   */
  EClass getNodeModel();

  /**
   * Returns the meta object for the attribute '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeModel#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeModel#getName()
   * @see #getNodeModel()
   * @generated
   */
  EAttribute getNodeModel_Name();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating <em>Node Operating</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Node Operating</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating
   * @generated
   */
  EClass getNodeOperating();

  /**
   * Returns the meta object for the reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating#getNodename <em>Nodename</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Nodename</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating#getNodename()
   * @see #getNodeOperating()
   * @generated
   */
  EReference getNodeOperating_Nodename();

  /**
   * Returns the meta object for the reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating#getNodes <em>Nodes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Nodes</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.NodeOperating#getNodes()
   * @see #getNodeOperating()
   * @generated
   */
  EReference getNodeOperating_Nodes();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection <em>Connection</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Connection</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection
   * @generated
   */
  EClass getConnection();

  /**
   * Returns the meta object for the attribute '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection#getName()
   * @see #getConnection()
   * @generated
   */
  EAttribute getConnection_Name();

  /**
   * Returns the meta object for the reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection#getEndnode <em>Endnode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Endnode</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Connection#getEndnode()
   * @see #getConnection()
   * @generated
   */
  EReference getConnection_Endnode();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate <em>Connection Update</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Connection Update</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate
   * @generated
   */
  EClass getConnectionUpdate();

  /**
   * Returns the meta object for the reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate#getConnectionname <em>Connectionname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Connectionname</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate#getConnectionname()
   * @see #getConnectionUpdate()
   * @generated
   */
  EReference getConnectionUpdate_Connectionname();

  /**
   * Returns the meta object for the reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate#getEndnode <em>Endnode</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Endnode</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.ConnectionUpdate#getEndnode()
   * @see #getConnectionUpdate()
   * @generated
   */
  EReference getConnectionUpdate_Endnode();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Flow <em>Flow</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Flow</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Flow
   * @generated
   */
  EClass getFlow();

  /**
   * Returns the meta object for the attribute '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Flow#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Flow#getName()
   * @see #getFlow()
   * @generated
   */
  EAttribute getFlow_Name();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.FlowUpdate <em>Flow Update</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Flow Update</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.FlowUpdate
   * @generated
   */
  EClass getFlowUpdate();

  /**
   * Returns the meta object for the reference list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.FlowUpdate#getFlowId <em>Flow Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Flow Id</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.FlowUpdate#getFlowId()
   * @see #getFlowUpdate()
   * @generated
   */
  EReference getFlowUpdate_FlowId();

  /**
   * Returns the meta object for class '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation <em>Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Operation</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation
   * @generated
   */
  EClass getOperation();

  /**
   * Returns the meta object for the attribute '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getName()
   * @see #getOperation()
   * @generated
   */
  EAttribute getOperation_Name();

  /**
   * Returns the meta object for the attribute list '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Value</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getValue()
   * @see #getOperation()
   * @generated
   */
  EAttribute getOperation_Value();

  /**
   * Returns the meta object for the reference '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetId <em>Target Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target Id</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetId()
   * @see #getOperation()
   * @generated
   */
  EReference getOperation_TargetId();

  /**
   * Returns the meta object for the reference '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetNode <em>Target Node</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target Node</em>'.
   * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.Operation#getTargetNode()
   * @see #getOperation()
   * @generated
   */
  EReference getOperation_TargetNode();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  EditorFactory getEditorFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ModelImpl <em>Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ModelImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getModel()
     * @generated
     */
    EClass MODEL = eINSTANCE.getModel();

    /**
     * The meta object literal for the '<em><b>Sentences</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__SENTENCES = eINSTANCE.getModel_Sentences();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.SentenceImpl <em>Sentence</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.SentenceImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getSentence()
     * @generated
     */
    EClass SENTENCE = eINSTANCE.getSentence();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeImpl <em>Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getNode()
     * @generated
     */
    EClass NODE = eINSTANCE.getNode();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NODE__NAME = eINSTANCE.getNode_Name();

    /**
     * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NODE__NODES = eINSTANCE.getNode_Nodes();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeModelImpl <em>Node Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeModelImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getNodeModel()
     * @generated
     */
    EClass NODE_MODEL = eINSTANCE.getNodeModel();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NODE_MODEL__NAME = eINSTANCE.getNodeModel_Name();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeOperatingImpl <em>Node Operating</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.NodeOperatingImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getNodeOperating()
     * @generated
     */
    EClass NODE_OPERATING = eINSTANCE.getNodeOperating();

    /**
     * The meta object literal for the '<em><b>Nodename</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NODE_OPERATING__NODENAME = eINSTANCE.getNodeOperating_Nodename();

    /**
     * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference NODE_OPERATING__NODES = eINSTANCE.getNodeOperating_Nodes();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionImpl <em>Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getConnection()
     * @generated
     */
    EClass CONNECTION = eINSTANCE.getConnection();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONNECTION__NAME = eINSTANCE.getConnection_Name();

    /**
     * The meta object literal for the '<em><b>Endnode</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTION__ENDNODE = eINSTANCE.getConnection_Endnode();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionUpdateImpl <em>Connection Update</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.ConnectionUpdateImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getConnectionUpdate()
     * @generated
     */
    EClass CONNECTION_UPDATE = eINSTANCE.getConnectionUpdate();

    /**
     * The meta object literal for the '<em><b>Connectionname</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTION_UPDATE__CONNECTIONNAME = eINSTANCE.getConnectionUpdate_Connectionname();

    /**
     * The meta object literal for the '<em><b>Endnode</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTION_UPDATE__ENDNODE = eINSTANCE.getConnectionUpdate_Endnode();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowImpl <em>Flow</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getFlow()
     * @generated
     */
    EClass FLOW = eINSTANCE.getFlow();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FLOW__NAME = eINSTANCE.getFlow_Name();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowUpdateImpl <em>Flow Update</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.FlowUpdateImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getFlowUpdate()
     * @generated
     */
    EClass FLOW_UPDATE = eINSTANCE.getFlowUpdate();

    /**
     * The meta object literal for the '<em><b>Flow Id</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FLOW_UPDATE__FLOW_ID = eINSTANCE.getFlowUpdate_FlowId();

    /**
     * The meta object literal for the '{@link org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl <em>Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.OperationImpl
     * @see org.opendaylight.nemo.tool.eclipse.plugin.editor.impl.EditorPackageImpl#getOperation()
     * @generated
     */
    EClass OPERATION = eINSTANCE.getOperation();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute OPERATION__NAME = eINSTANCE.getOperation_Name();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute OPERATION__VALUE = eINSTANCE.getOperation_Value();

    /**
     * The meta object literal for the '<em><b>Target Id</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OPERATION__TARGET_ID = eINSTANCE.getOperation_TargetId();

    /**
     * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OPERATION__TARGET_NODE = eINSTANCE.getOperation_TargetNode();

  }

} //EditorPackage
