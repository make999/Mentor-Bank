package ru.mentorbank.backoffice.services.moneytransfer;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mentorbank.backoffice.services.moneytransfer.exceptions.TransferException;
import ru.mentorbank.backoffice.test.AbstractSpringTest;

public class MoneyTransferServiceTest extends AbstractSpringTest {

	@Autowired
	private MoneyTransferServiceBean moneyTransferService;

	@Before
	public void setUp() {
                srcac = new PhysicalAccountInfo();
		dstac = new JuridicalAccountInfo();
		transreq = new TransferRequest();
		
		mockedAcService = mock(AccountServiceBean.class);
		mockedStService = mock(StopListServiceStub.class);
		mockedOpDao = mock(OperationDao.class);
		
		srcac.setAccountNumber("11111111111");
		srcac.setDocumentNumber(StopListServiceStub.DOC_NUM_FOR_OK_STATUS);
		srcac.setDocumentSeries(StopListServiceStub.DOC_SER_FOR_OK_STATUS);
		
		dstac.setInn(StopListServiceStub.INN_FOR_OK_STATUS);
		dstac.setAccountNumber("66666666666");
		
		transreq.setSrcAccount(srcac);
		transreq.setDstAccount(dstac);
		
		StlistInfo = new StopListInfo();
		StlistInfo.setStatus(StopListStatus.OK);
	}

	@Test
	public void transfer() throws TransferException, OperationDaoException {
		fail("not implemented yet");
		// TODO: ���������� ��������������, ��� ��� �������� �������� ���
		// �������� � ���������� ��� ����������� ������ ��������
		// ����� ������� ����������������� ���������
		// StopListService mockedStopListService =
		// mock(StopListServiceStub.class);
		// AccountService mockedAccountService = mock(AccountServiceBean.class);
		//
		// moneyTransferService.transfer(null);
		//
		// verify(mockedStopListService).getJuridicalStopListInfo(null);
		// verify(mockedAccountService).verifyBalance(null);

                when(mockedAcService.verifyBalance(srcac)).thenReturn(true);
		when(mockedStService.getJuridicalStopListInfo(any(JuridicalStopListRequest.class))).thenReturn(StlistInfo);
		when(mockedStService.getPhysicalStopListInfo(any(PhysicalStopListRequest.class))).thenReturn(StlistInfo);
		
		
	        moneyTransferService.transfer(transreq);
		verify(mockedStService).getJuridicalStopListInfo(any(JuridicalStopListRequest.class));
		verify(mockedAcService).verifyBalance(srcac);
		verify(mockedOpDao).saveOperation(any(Operation.class));
	}
}
