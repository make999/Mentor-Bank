package ru.mentorbank.backoffice.services.moneytransfer;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


import ru.mentorbank.backoffice.model.Operation;
import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.model.transfer.AccountInfo;
import ru.mentorbank.backoffice.model.transfer.JuridicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.PhysicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.TransferRequest;
import ru.mentorbank.backoffice.services.moneytransfer.exceptions.TransferException;
import ru.mentorbank.backoffice.services.stoplist.StopListService;
import ru.mentorbank.backoffice.services.stoplist.StopListServiceStub;
import ru.mentorbank.backoffice.test.AbstractSpringTest;
import ru.mentorbank.backoffice.services.accounts.AccountService;
import ru.mentorbank.backoffice.services.accounts.AccountServiceBean;
import ru.mentorbank.backoffice.dao.OperationDao;
import ru.mentorbank.backoffice.dao.exception.OperationDaoException;


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

		moneyTransferService.setAccountService(mockedAcService);
		moneyTransferService.setOperationDao(mockedOpDao);
		moneyTransferService.setStopListService(mockedStService);
				
                when(mockedAcService.verifyBalance(srcac)).thenReturn(true);

		StlistInfo = new StopListInfo();
		StlistInfo.setStatus(StopListStatus.OK);
	}

	@Test
	public void transfer() throws TransferException, OperationDaoException {
		//fail("not implemented yet");
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

                
		when(mockedStService.getJuridicalStopListInfo(any(JuridicalStopListRequest.class))).thenReturn(StlistInfo);
		when(mockedStService.getPhysicalStopListInfo(any(PhysicalStopListRequest.class))).thenReturn(StlistInfo);
		
		
	        moneyTransferService.transfer(transreq);
		verify(mockedStService).getJuridicalStopListInfo(any(JuridicalStopListRequest.class));
		verify(mockedAcService).verifyBalance(srcac);
		verify(mockedOpDao).saveOperation(any(Operation.class));
	}
}
