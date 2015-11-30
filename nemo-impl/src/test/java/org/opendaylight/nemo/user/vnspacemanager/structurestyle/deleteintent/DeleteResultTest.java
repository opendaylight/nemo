package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.*;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.structure.style.nemo.delete.input.Results;
public class DeleteResultTest{
	private UserId userId;
    private	Results results;
	private DeleteResult deleteresult;
	@org.junit.Before
	public void setUp()throws Exception{
		userId=mock(UserId.class);
		results=mock(Results.class);
		deleteresult=mock(DeleteResult.class);
	}
	public void DeleteResultHandlingTest() throws Exception{
		Assert.assertNull(deleteresult.DeleteResultHandling(userId,results));
	}
}